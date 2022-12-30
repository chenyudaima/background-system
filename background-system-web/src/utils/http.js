import Axios from 'axios'
import router from '@/router/index.js'


const http = Axios.create({
  baseURL: "http://localhost:8080/", // 基础请求地址
  timeout: 10000, // 请求超时设置
  withCredentials: true, // 跨域请求是否需要携带 cookie
})

//配置
http.defaults.baseURL = '/system'

//请求拦截
http.interceptors.request.use(
  (config) => {
    config.headers["Authorization"] = `Basic ${localStorage.getItem("token")}`;
    return config;
  },
  (error) => {
    Promise.reject(error);
  })

//响应拦截
http.interceptors.response.use(
  (res) => {
    let response = res.data
    if(response.code == 403) {
      router.push("/login")
    }
    return response;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default http;