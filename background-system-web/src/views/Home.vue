<template>
  <div>index</div>
</template>

<script>
import http from '@/utils/http'
export default {
  created(){this.cc()},
  methods: {
    cc() {
      // 请求任意一个资源 后端过滤器进行鉴权
      http.get("/updateToken").then((resp) => {
        console.log(resp)
      });
    },
  },
};
</script>

<style>
</style>
