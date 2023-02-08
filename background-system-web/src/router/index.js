import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/Login.vue')
  },
  {
    path: '/',
    name: 'home',
    component: () => import('@/views/Home.vue'),

    //这里面的都是登录成功之后从服务器加载的 动态路由
    children: [
      {
        path: '/',
        name: 'index',
        component: () => import('@/views/home/Index.vue')
      },
      
    ]

  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  
  //如果未匹配到路由则跳转到首页 (也可以跳转到404页面)
	if (to.matched.length === 0) {  
	   router.push("/")
	 } else {
	   next()
	 }
})

export default router
