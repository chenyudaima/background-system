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
    children: [
      {
        path: '/',
        name: 'index',
        component: () => import('@/views/Home.vue')
      }
    ]

  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  //如果未匹配到路由则跳转到首页
	if (to.matched.length === 0) {  
	   router.push("/")
	 } else {
	   next()
	 }
})

export default router
