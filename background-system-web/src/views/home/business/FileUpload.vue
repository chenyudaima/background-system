<template>
  <div>
    <input type="file" @change="upload" />
  </div>
</template>

<script>
import http from '@/utils/http.js'
import $ from '/public/js/jquery-3.4.1.min.js'
export default {
  data() {
    return {
      file: null
    }
  },
  methods: {
    upload(event) {
      let param = new FormData()
      param.append("file", event.target.files[0])
      http.upload("/home/business/file", param).then(resp => {
        console.log(resp)
      })
    }

  }

}
</script>

<style></style>