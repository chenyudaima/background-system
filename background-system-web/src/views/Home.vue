<template>
  <el-container style="height: 100%; overflow:hidden;">

    <!--头部  -->
    <el-header>
      <div style="display: flex;align-items: center;">
        <img src="/image/home/app.png" alt="" width="50" height="50">
        <span style="margin-left: 20px;">合格证质检系统</span>
      </div>
      <div>
        <el-dropdown style="padding: 20px" trigger="click">
          <span style="color: #409EFF">
            {{ user.name }}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>个人信息</el-dropdown-item>
            <el-dropdown-item><el-button type="danger" size="mini" round
                @click="loginOut">退出</el-button></el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>

        <el-button type="danger" round @click="loginOut" size="mini">退出</el-button>
      </div>
    </el-header>


    <!-- 页面主体区  嵌套容器  包裹 Aside与Main -->
    <el-container>

      <!-- 左侧 -->
      <el-aside width="200px" style="background-color: #333744">

        <!-- 
          unique-opened 是否保持只有一个子菜单展开 如果true不生效说明菜单index相同,所以非路由菜单的index设置为id
          background-color 导航栏背景颜色
          text-color 一级菜单字体颜色
          active-text-color 二级菜单字体颜色
          router 启用该模式会在激活导航时以 index 作为 path 进行路由跳转
         -->
        <el-menu :unique-opened="true" background-color="#333744" text-color="#fff" active-text-color="#ffd04b"
          :router="true">

          <template v-for="item in menuList">

            <!--判断一级菜单里面是否有二级菜单 -->
            <template v-if="item.type == 0 && item.subMenu != null && item.subMenu.length != 0">
              <el-submenu :index="item.id" :key="item.id">

                <!--一级菜单 -->
                <template slot="title">
                  <i :class="item.icon"></i>
                  <span>{{ item.name }}</span>
                </template>

                <template v-for="subItem in item.subMenu">
                  <!--判断是否有三级菜单 -->
                  <el-submenu v-if="subItem.type == 0 && subItem.subMenu != null && subItem.subMenu.length != 0"
                    :index="subItem.id" :key="subItem.id">

                    <!-- 二级菜单 -->
                    <template slot="title">
                      <i :class="subItem.icon"></i>
                      {{ subItem.name }}
                    </template>


                    <el-menu-item v-for="threeItem in subItem.subMenu" :key="threeItem.id" :index="threeItem.routerPath">
                      <i :class="threeItem.icon"></i>
                      {{ threeItem.name }}</el-menu-item>
                  </el-submenu>


                  <el-menu-item v-else :index="subItem.routerPath" :key="subItem.id">

                    <!--二级菜单 -->
                    <template slot="title">
                      <i :class="subItem.icon"></i>
                      {{ subItem.name }}
                    </template>

                  </el-menu-item>
                </template>
              </el-submenu>
            </template>

            <!--一级菜单 -->
            <template v-else>
              <el-menu-item :index="item.routerPath" :key="item.id">
                <i :class="item.icon"></i>
                {{ item.name }}
              </el-menu-item>
            </template>

          </template>

        </el-menu>
      </el-aside>

      <!-- 主体 -->
      <el-main class="home-main">
        <router-view></router-view>
      </el-main>

    </el-container>

  </el-container>
</template>

<script >
import http from '@/utils/http.js'
import routerUtil from '@/utils/routerUtil.js'
export default {
  name: 'Home',

  data() {
    return {
      //用户信息
      user: {},

      //菜单
      menuList: []
    }
  },

  created() {
    //查询用户信息
    http.get("/home/userInfo").then(resp => {
      this.user = resp.data
    })

    //加载路由和菜单
    this.loadMenu().then(() => {
      if (this.$route.path == "/home" || this.$route.path == "/home/") {
        this.$router.push("/home/index")
      }
    })
  },

  methods: {
    //退出
    loginOut() {
      http.get("/home/logout").then(resp => {
        if (resp.code == 200) {
          sessionStorage.clear()
          localStorage.clear()
        }
      })
      this.$router.push("/login")
    },

    //加载菜单栏和对应路由
    async loadMenu() {
      await http.get("/home/sysMenu").then(resp => {
        this.menuList = resp.data
        routerUtil.routerHandler(resp.data).forEach(router => {
          if (router.path.indexOf("home") != -1) {
            this.$router.addRoute("home", router)
          } else {
            this.$router.addRoute(router)
          }

        })
      })


    },

  },
}
</script>


<style scoped>
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

.el-menu {
  border-right-width: 0;
}

.el-header {
  background-color: #373D41;
  display: flex;
  justify-content: space-between;
  padding-left: 0px;

  align-items: center;

  color: #fff;

  font-size: 20px;
}
</style>