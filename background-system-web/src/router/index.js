import Vue from 'vue'
import VueRouter from 'vue-router'
import routerUtil from '@/utils/routerUtil.js'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/Login.vue')
  },
  {
    path: '/',
    redirect: '/home'
  },
  {
    path: '/home',
    name: 'home',
    component: () => import('@/views/Home.vue')
  }
]

//修改原型对象中的push方法 避免出现冗余导航的错误
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}


const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

let count = 0;
//路由跳转前
router.beforeEach((to, from, next) => {
  //如果未匹配到路由则跳转到首页 (也可以跳转到404页面)
  if (to.matched.length === 0) {
    //说明用户刷新了路由
    if (sessionStorage.getItem("currentRoute") == to.path) {
      //先加载路由
      routerUtil.loadRouter()
      count ++
      
      if(count > 3) {
        router.push("/")
      }else {
        next()
      }
      
    } else {
      router.push("/")
    }

  } else {
    //储存当前的路由
    sessionStorage.setItem("currentRoute", to.path)
    count = 0
    next()
  }
})

//路由跳转后
router.afterEach((to, from) => {

})

export default router
