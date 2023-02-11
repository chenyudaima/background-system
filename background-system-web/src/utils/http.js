import axios from 'axios'
import router from '@/router/index.js'
import httpMethod from '/public/common/constant/httpMethod.js'
import md5 from 'js-md5'
import qs from 'qs'


const http = axios.create({
  baseURL: "http://localhost:8080/", // 请求地址
  timeout: 100000, // 请求超时
  withCredentials: true, // 跨域请求是否需要携带 cookie
})

//配置
http.defaults.baseURL = '/system'

//请求拦截
http.interceptors.request.use(config => {
  //设置权限请求头
  config.headers.Authorization = localStorage.getItem("token");

  //排序和格式化之后的参数(需要参与加密的参数)
  let params;

  //时间戳
  let timestamp = new Date().getTime()

  //参与加密的key，但不进行传参
  let accessKey = config.headers.Authorization

  //唯一标识
  let nonce = `${config.url}#${config.method}#${timestamp}#${Math.random()}`

  if (config.method == httpMethod.POST ||
    config.method == httpMethod.DELETE ||
    config.method == httpMethod.PUT ||
    config.method == httpMethod.PATCH) {
    let body = config.data

    if (body instanceof URLSearchParams) {
      config.headers['content-type'] = "application/x-www-form-urlencoded"
      body.append("timestamp", timestamp)
      body.append("accessKey", accessKey)
      body.append("nonce", nonce)

      //按key排序，a字母在前，第一个相同继续看第二个字母，不看长度
      body.sort()
      
      //转换成 aa=aa&bb=bb 格式       
      params = decodeURIComponent(body.toString())

      //删除参数中的密钥
      body.delete("accessKey")

    } else if (body instanceof FormData) {
      body.append("timestamp", timestamp)
      body.append("nonce", nonce)

      let map = new Map();
      for (var kv of body.entries()) {
        //判断是否是文件，文件不参与参数加密
        if(kv[1] instanceof File) {
          continue
        }
        map.set(kv[0], kv[1])
      }

      map.set("accessKey", accessKey)

      //map进行排序
      map = new Map([...map].sort());

      params = ""
      //排序之后拼接参数
      for (var kv of map.entries()) {
        params += kv[0] + "=" + kv[1] + "&"
      }

      params = params.substring(0, params.length - 1);

    } else if (config.data instanceof Object) {

      config.data = {
        ...config.data,
        timestamp: new Date().getTime(),
        accessKey : accessKey,
        nonce : nonce
      }

      params = ""

      Object.keys(config.data).sort().forEach(key => {
        params += `${key}=${config.data[key]}&`
      })

      params = params.substring(0, params.length - 1);

      //删除json对象指定属性 accessKey不参与传输
      delete config.data.accessKey
    }

  } else if (config.method == httpMethod.GET) {
    
    config.params = {
      ...config.params,
      timestamp: new Date().getTime(),
      accessKey : accessKey,
      nonce : nonce
    }
    params = ""

    Object.keys(config.params).sort().forEach(key => {
      params += `${key}=${config.params[key]}&`
    })
    
    params = params.substring(0, params.length - 1);

    //删除json对象指定属性 accessKey不参与传输
    delete config.params.accessKey
  }

  //设置参数签名请求头
  config.headers.signature = md5(params).toUpperCase()

  return config;

}, error => {
  Promise.reject(error);
})


//标识现在是否正在刷新token
let isRefreshing = false

//响应拦截
http.interceptors.response.use(
  (res) => {
    let response = res.data

    //403为权限不足
    if (response.code == 403) {
      router.push({path:"/login", query:{"message": response.message}})
      return response;
    }

    //每次请求都会刷新token 或者定时刷新token
    // if (!isRefreshing && window.location.pathname != "/login") {
    //   isRefreshing = true
    //   http.get("/system/updateToken").then(resp => {
    //     localStorage.setItem("token", resp.data);
    //     isRefreshing = false;
    //   })
    // }

    return response;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default http;