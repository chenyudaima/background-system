<template>
  <el-container height="100%">

    <!-- 头部 -->
    <el-header style="background: rgb(34, 160, 231);display: flex;justify-content: space-between;">
      <div style="display: flex; align-items: center;">
        <img :src="require('/public/img/1.jpg')" width="60" height="60" />
        <div style="margin-left: 10px;">后台系统</div>
      </div>

      <div>
        <span icon="el-icon-coordinate">{{ sysUser!=null ? sysUser.name :'' }}</span>
        <el-button type="text" icon="el-icon-coordinate" @click="loginOut" style="color: #fbfffd;">注销</el-button>
      </div>
    </el-header>

    <el-container>
      <!-- 左边目录 -->
      <el-aside style="background-color: rgb(55, 61, 68);" width="220px">
        

      </el-aside>

      <!-- 页面 -->
      <el-main style="background-color: rgb(234, 237, 241);">
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import http from '@/utils/http'
export default {
  data() {
    return {
      sysUser:null
    }
  },

  created() {
      //获取该用户目录
      http.get("/home/userInfo").then(resp => {
        this.sysUser = resp.sysUser
        
      })
      //获取该用户名称
  },

  methods: {
    loginOut() {
      localStorage.removeItem("token")
      this.$router.push("/login")
    },
  },
};
</script>

<style scoped>

</style>
