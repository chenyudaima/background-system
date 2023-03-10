import axios from 'axios'
import router from '@/router/index.js'
import signUtil from '@/utils/signUtil.js'

const http = axios.create({
  //使用代理，后端不需要做跨域处理，会匹配vue.config.js中配置的代理，实际上是匹配的 target/system-api
  //如果没有匹配到或者这个服务请求不了，则会改为获取前端页面的ip和端口
  baseURL: "/system_api",
  
  // baseURL: "http://localhost:8080/system_api", //全地址表示不使用vue.config.js配置的代理，后端需要做跨域处理
  timeout: 100000, // 请求超时
  withCredentials: true, // 跨域请求是否需要携带 cookie
})

//和axios的create中的baseURL一样
// http.defaults.baseURL = '/system_api'

//请求拦截
http.interceptors.request.use(config => {

  //设置权限请求头
  config.headers.Authorization = localStorage.getItem("token");
  
  //添加签名参数及参数加密请求头
  signUtil.signature(config)

  return config;

}, error => {
  Promise.reject(error);
})


//响应拦截
http.interceptors.response.use(
  (res) => {
    //判断响应的是文件还是json对象
    if(!(res.data instanceof Object)) {
      return res;
    }

    let response = res.data

    //403为权限不足
    if (response.code == 403) {
      router.push({ path: "/login", query: { "message": response.message } })
      return response;
    }

    return response;
  },

  //请求异常（网络异常）的回调函数
  (error) => {
    router.push({ path: "/login", query: { "message": error.message } })
    return Promise.reject(error);
  }
);

export default http;