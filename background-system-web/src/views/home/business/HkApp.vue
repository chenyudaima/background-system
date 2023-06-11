<template>
  <div>
    <el-button @click="control(21, 0)">上</el-button>
    <el-button @click="control(22, 0)">下</el-button>
    <el-button @click="control(23, 0)">左</el-button>
    <el-button @click="control(24, 0)">右</el-button>

    <el-button @click="control(21, 1)">停止</el-button>


    预设点:<el-input v-model="dwPresetIndex" size="small" style="width: 350px;" />


    <el-button @click="presetPointOperation()">设置预设点</el-button>


    速度:<el-input v-model="dwSpeed" />


    <video id="video"  muted="muted" style="width: 100%;height: 100%;" autoplay="autoplay"></video>

  </div>
</template>

<script>
import http from '@/utils/http.js'
import Wfs from '/public/js/wfs.js'
export default {
  data() {
    return {
      //摄像头转动速度
      dwSpeed: 1,

      //预设点
      dwPresetIndex: 1,

      
      video: null,
      wfs: null,
    }
  },

  mounted() {
    this.video = document.getElementById("video")
  },

  created() {
    window.onload = function () {
      if (Wfs.isSupported()) {
        this.wfs = new Wfs()
        this.wfs.attachMedia(this.video, 'ch1')
      }
    }
    // this.ws = new WebSocket("ws://localhost:8080/ws")

    // this.ws.onmessage = function(event) {
    //   console.log(event.data)
    //   // let blob = new Blob([event.data], { type: 'video/mp4' })
    //   // let url = URL.createObjectURL(blob)
    //   // if(this.video != null) {
    //   //   this.video.src = url
    //   // }

    // }
  },

  // beforeDestroy() {
  //   // 断开 WebSocket 连接
  //   this.ws.close()
  // },

  methods: {

    /**
     * 摄像头转动
     * @param {Number} dwPTZControlCmd 转动方向代号
     * @param {Number} dwStop 0开始 1停止
     */
    control(dwPTZControlCmd, dwStop) {
      let params = new URLSearchParams();
      params.append("dwPTZControlCmd", dwPTZControlCmd)
      params.append("dwStop", dwStop)
      params.append("dwSpeed", this.dwSpeed)
      http.post("/home/business/hkApp/control", params).then(resp => {
        if (resp.data == false) {
          this.$message.error("转动失败")
        }
      })
    },

    /**
     * 将当前点设置为预设点
     */
    presetPointOperation() {
      let params = new URLSearchParams();
      params.append("dwPresetIndex", this.dwPresetIndex)
      http.post("/home/business/hkApp/presetPointOperation", params).then(resp => {
        if (resp.data == true) {
          //调用成功
          this.$message.success("设置成功")
        }
      })
    }
  }
}

</script>

<style scope>
</style>
