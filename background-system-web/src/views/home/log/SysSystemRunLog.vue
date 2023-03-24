<template>
    <div>
      <p v-for="(item, index) in sysSystemRunLogs" :key="index">
        {{ item }}
      </p>
    </div>
</template>

<script>
import http from '@/utils/http.js'
export default {
  data() {
    return {
      sysSystemRunLogs: []
    }
  },
  created() {
    http.get("/home/log/sysSystemRunLog",{params: {filePath : "all/web-2023-03-24.0.log"}}).then(resp => {
      this.sysSystemRunLogs = resp.data
    })
  }
}
</script>

<style></style>