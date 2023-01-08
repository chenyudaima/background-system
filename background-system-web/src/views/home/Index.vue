<template>
  <div>
    <input type="file" id="upload" @change="upload" />

    <div id="message" style="size:40px">

    </div>
  </div>
</template>

<script>
import http from '@/utils/http'
export default {
  methods: {
    data() {
      return {
        message: ''
      }
    },
    upload(e) {
      let file = e.target.files[0]
      let param = new FormData()
      param.append('file', file)
      http.post("/index/ocr", param).then(resp => {
        document.getElementById("message").innerText = resp.data
      })

    }
  }
}
</script>

<style>

</style>