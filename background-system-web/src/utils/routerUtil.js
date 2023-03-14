import http from '@/utils/http.js'
import router from '@/router/index.js'

//加载路由
function loadRouter() {
  http.get("/home/menu").then(resp => {
    routerHandler(resp.data).forEach(x => {
      router.addRoute("home", x)
    })
    
  })
}

//解析菜单生成路由表
function routerHandler(menuList) {
  let array = []
  menuList.forEach(menu => {
    if (menu.routerComponent && menu.routerPath) {
      array.push({
        path: menu.routerPath,
        name: menu.name,
        component: () => import(`@/views${menu.routerComponent}`)
      })
    }

    //如果大于0说明有子菜单
    if (menu.subMenu == null) {
      menu.subMenu = []
    }

    // children
    if (menu.subMenu.length > 0) {
      array = [...array, ...routerHandler(menu.subMenu)]
    }
  })

  return array
}

export default
{
  loadRouter,routerHandler
}

