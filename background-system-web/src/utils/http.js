import axios from 'axios'
import router from '@/router/index.js'
import signUtil from '@/utils/signUtil.js'

const http = axios.create({
  baseURL: "http://localhost:8080/", // 请求地址
  timeout: 100000, // 请求超时
  withCredentials: true, // 跨域请求是否需要携带 cookie
})

//后端部署路径（配置）
http.defaults.baseURL = '/system-api'

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


//标识现在是否正在刷新token
let isRefreshing = false

//响应拦截
http.interceptors.response.use(
  (res) => {
    //如果是下载，响应的不是Object对象，直接返回res
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
  (error) => {
    return Promise.reject(error);
  }
);

export default http;