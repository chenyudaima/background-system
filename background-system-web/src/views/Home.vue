<template>
  <el-container style="height: 100%;">

    <!--头部  -->
    <el-header>
      <div style="display: flex;align-items: center;">
        <img src="../../public/img/home_icon.jpg" alt="" width="50" height="50">
        <span style="margin-left: 20px;">数据核对系统</span>
      </div>
      <div>
        <span>{{ name }}</span>
        <el-button type="info" @click="loginOut">退出</el-button>
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
          :router="true" style="text-align:center">

          <template v-for="item in menuList">

            <!--判断一级菜单里面是否有二级菜单 -->
            <template v-if="item.children.length != 0">
              <el-submenu :index="item.id.toString()" :key="item.id">

                <!--一级菜单 -->
                <template slot="title">
                  <!-- <i v-bind:class="item.iconwebcode"></i> -->
                  <span>{{ item.name }}</span>
                </template>

                <template v-for="subItem in item.children">
                  <!--判断是否有三级菜单 -->
                  <el-submenu v-if="subItem.children.length != 0" :index="subItem.id.toString()" :key="subItem.id">

                    <!-- 二级菜单 -->
                    <template slot="title">
                      <!-- <i v-bind:class="subItem.iconwebcode"></i> -->
                      {{ subItem.name }}
                    </template>


                    <el-menu-item v-for="threeItem in subItem.children" :key="threeItem.id"
                      :index="threeItem.routerPath">
                      <!-- <i v-bind:class="threeItem.iconwebcode"></i> -->
                      {{ threeItem.name }}</el-menu-item>
                  </el-submenu>

                  
                  <el-menu-item v-else :index="subItem.routerPath" :key="subItem.id">

                    <!--二级菜单 -->
                    <template slot="title">
                      <!-- <i v-bind:class="subItem.iconwebcode"></i> -->
                      {{ subItem.name }}
                    </template>

                  </el-menu-item>
                </template>
              </el-submenu>
            </template>

            <!--一级菜单 -->
            <template v-else>
              <el-menu-item :index="item.routerPath" :key="item.id">
                <!-- <i v-bind:class="item.iconwebcode"></i> -->
                {{ item.name }}
              </el-menu-item>
            </template>

          </template>

        </el-menu>
      </el-aside>

      <!-- 主体 -->
      <el-main style="background-color: #EAEDF1;">
        <router-view></router-view>
      </el-main>

    </el-container>

  </el-container>
</template>

<script >
import http from '@/utils/http.js'
export default {
  name: 'Home',

  data() {
    return {
      //当前用户名
      name: '',

      //当前
      menuList: []
    }
  },

  created() {
    //查询用户信息
    http.get("/home/userInfo").then(resp => {
      this.name = resp.data
    })

    this.loadMenu()
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
      let routers = {
        path: '/home',
        name: 'home',
        component: () => import('@/views/Home.vue'),
        children: []
      }

      await http.get("/home/menu").then(resp => {
        this.menuList = resp.data
        //过滤没有路径的
        routers.children = this.routerHandler(this.menuList)
      })

      //动态添加路由表
      this.$router.addRoute(routers)
    },

    //解析菜单数据生成路由
    routerHandler(menuList) {
      let array = []
      menuList.forEach(menu => {
        if (menu.routerComponent != null || menu.routerPath != null) {
          array.push({
            path: menu.routerPath,
            name: menu.name,
            component: () => import(`@/views${menu.routerComponent}`)
          })
        }

        //如果大于0说明有子菜单
        if(menu.children == null) {
          menu.children = []
        }
        if (menu.children.length > 0) {
          array = [...array, ...this.routerHandler(menu.children)]
        }
      })

      return array
    }

  },
}
</script>


<style scoped>
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