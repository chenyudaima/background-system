<template>
  <el-container style="height: 100%;">

    <el-header style="text-align: left;height: @rowheight*10 !important;">
      <el-form :inline="true" @submit.native.prevent>
        <el-form-item label="日志级别">
          <el-select size="small" v-model="type" placeholder="请选择">
            <el-option v-for="(type) in typeList" :label="type.name" :value="type.value" :key="type.id" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button @click="query" type="primary" size="small">查询</el-button>
        </el-form-item>
      </el-form>
    </el-header>

    <el-aside width="200px">
      <span v-for="(file, index) in fileList" :key="index">
        <el-button @click="query1(file)">{{ file }}</el-button> <br />
      </span>
    </el-aside>

    <el-main class="home-main">
        <p v-for="(log, index) in logList" :key="index">{{ log }}</p>
    </el-main>

  </el-container>
</template>

<script>
import http from '@/utils/http.js'
import dictUtil from '@/utils/dictUtil.js'
export default {
  data() {
    return {
      logList: [],
      typeList: [],
      type: "all",
      fileList: [],
      filePath: null
    }
  },

  created() {
    dictUtil.getDictData("837727191054680065").then(resp => {
      this.typeList = resp
    })
    this.query()
  },

  methods: {
    query() {
      http.get("/home/log/sysSystemRunLog", { params: { type: this.type, filePath: this.filePath } }).then(resp => {
        if (resp.code != 200) {
          return;
        }

        if (resp.data.type == 1) {
          this.fileList = resp.data.fileList
          return;
        }

        if (resp.data.type == 2) {
          this.filePath = null
          this.logList = resp.data.logList
          return;
        }
      })
    },
    
    query1(name) {
      this.filePath = name
      this.query()
    }
  }


}
</script>

<style>
.home-main:hover {
  overflow-y: auto;
  overflow-y: overlay;
}

.home-main {
  background-color: #EAEDF1;
  padding-right: 17px;
  padding-left: 3px;
  height: 93vh;
  width: 100%;
  overflow-y: hidden;
  overflow-x: hidden;
}
</style>