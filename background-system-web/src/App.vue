<template>
  <div id="app">
    <router-view />
  </div>
</template>
<script>
import http from '@/utils/http.js'
export default {
  name: "App",

  created() {
    
    //定时刷新token
    setInterval(() => {
      http.get("/system/updateToken").then(resp => {
        localStorage.setItem("token", resp.data);
      })
    }, 60000)
  }

};
</script>
<style>

</style>
