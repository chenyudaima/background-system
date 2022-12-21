<template>
  
</template>

<script>
import axios from 'axios'
export default {
  methods:{
    cc() {
      console.log("发起请求")
      //请求任意一个资源 后端过滤器进行鉴权
      axios.get("http://localhost:8081/login").then(resp => {

      })
    }
    
  }
};
</script>

<style>
      
</style>