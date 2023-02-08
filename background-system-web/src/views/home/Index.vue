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
      let reader = new FileReader()
      reader.onload = e => {
        param.append('image', e.target.result)
        http.post("/index/ocr",param).then(resp => {
          document.getElementById("message").innerText = resp.data
        })
      }
      reader.readAsDataURL(file)
    }
  }
}
</script>

<style>

</style>