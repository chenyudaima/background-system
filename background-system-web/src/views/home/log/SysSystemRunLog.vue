<template>
  <div>
    <div v-for="(log, index) in logs" :key="index">
      <div v-html="log[1]" style="margin-bottom: 5px; font-size: 16px"></div>
    </div>
  </div>
</template>

<script>
import http from '@/utils/http.js'
export default {
  data() {
    return {
      logs: []
    }
  },
  created() {
    http.get("/home/log/sysSystemRunLog", { params: { filePath: "all/web-2023-03-24.0.log" } }).then(resp => {
      this.logs = resp.data
    })
  }
}
</script>

<style></style>