import Vue from 'vue'
import App from './App.vue'
import router from '@/router/index.js'

//注册全局样式 直接生效
import '@/assets/style/index.css'

//注册全局js方法
// import common from '@/assets/style/common.js'   // 引入全局文件common.js
// Vue.prototype.$common = common; 
//使用 this.$common.formatDate(date);

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(ElementUI)

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
