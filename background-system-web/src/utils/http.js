import Axios from 'axios'
import router from '@/router/index.js'


const http = Axios.create({
  baseURL: "http://localhost:8080/", // 请求地址
  timeout: 10000, // 请求超时
  withCredentials: true, // 跨域请求是否需要携带 cookie
})

//配置
http.defaults.baseURL = '/system'

//标识现在是否正在刷新token
let isRefreshing = false

//请求拦截
http.interceptors.request.use(
  (config) => {
    config.headers.Authorization = localStorage.getItem("token");
    return config;
  },
  (error) => {
    Promise.reject(error);
  })

//响应拦截
http.interceptors.response.use(
  (res) => {
    let response = res.data

    //403为权限不足
    if (response.code == 403) {
      router.push("/login")
      return response;
    }
    
    //每次请求都会刷新token
    if(!isRefreshing && window.location.pathname != "/login") {
      isRefreshing = true
      http.get("/system/updateToken").then(resp => {
        localStorage.setItem("token", resp.data);
        isRefreshing = false;
      })
    }

    return response;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default http;