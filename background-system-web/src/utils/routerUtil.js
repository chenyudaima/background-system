import http from '@/utils/http.js'
import router from '@/router/index.js'

//加载路由
async function loadRouter() {
  let resp = await http.get("/home/sysMenu")
  routerHandler(resp.data).forEach(x => {
    if(x.path.indexOf("home") != -1) {
      //包含home的页面
      router.addRoute("home", x)
    }else {
      //单页面
      router.addRoute(x)
    }
  })
}

//解析菜单生成路由表
function routerHandler(sysMenuList) {
  let array = []
  sysMenuList.forEach(sysMenu => {
    if (sysMenu.type == 1) {
      array.push({
        path: sysMenu.routerPath,
        name: sysMenu.name,
        component: () => import(`@/views${sysMenu.routerComponent}`)
      })
    }

    if (sysMenu.type == 0 && sysMenu.subMenu != null && sysMenu.subMenu.length > 0) {
      array = [...array, ...routerHandler(sysMenu.subMenu)]
    }
  })

  return array
}

export default
  {
    loadRouter, routerHandler
  }

