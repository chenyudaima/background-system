<template>
  <el-container height="100%">

    <!-- 头部 -->
    <el-header height="60px" style="background: rgb(34, 160, 231); display: flex; justify-content: space-between;">
      <div style="display: flex; align-items: center;">
        <img :src="require('/public/img/1.jpg')" width="60" height="60" />
        <div style="margin-left: 10px;">后台系统</div>
      </div>

      <div>
        <span icon="el-icon-coordinate">{{ name }}</span>
        <el-button type="text" icon="el-icon-coordinate" @click="loginOut" style="color: #fbfffd;">注销</el-button>
      </div>
    </el-header>

    <el-container height="800px">
      <!-- 左边目录 -->
      <el-aside style="background-color: rgb(55, 61, 68);">

        <el-menu default-active="2" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose"
          background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>导航一</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="1-1">选项1</el-menu-item>
              <el-menu-item index="1-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="1-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="1-4">
              <template slot="title">选项4</template>
              <el-menu-item index="1-4-1">选项1</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-menu-item index="2">
            <i class="el-icon-menu"></i>
            <span slot="title">导航二</span>
          </el-menu-item>
          <el-menu-item index="3" disabled>
            <i class="el-icon-document"></i>
            <span slot="title">导航三</span>
          </el-menu-item>
          <el-menu-item index="4">
            <i class="el-icon-setting"></i>
            <span slot="title">导航四</span>
          </el-menu-item>
        </el-menu>

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
      name: null,
      catalogue: null
    }
  },

  created() {
    http.get("/home/userInfo").then(resp => {
      this.name = resp.data.sysUser.name
      this.catalogue = resp.data.catalogue

    })
  },

  methods: {
    loginOut() {
      http.get("/home/logout").then(resp => {if(resp.code == 200) {localStorage.removeItem("token")}})
      this.$router.push("/login")
    }
  },
};
</script>

<style scoped>

</style>
