<template>
  <el-container style="height: 100%;">

    <!-- 按钮区域 -->
    <el-header style="height: @rowheight*10 !important">
      <el-button>添加</el-button>
      <el-button @click="remove">删除</el-button>
    </el-header>

    <el-container>

      <!-- 菜单权限Tree区域 -->
      <el-aside width="500px">

        <el-tree style="3Z" ref="tree" draggable @node-drop="nodeDrop" @allow-drop="allowDrop" :data="sysMenuList"
          show-checkbox node-key="id" :props="{ children: 'subMenu' }" @node-click="sysMenuClick">
          <span class="custom-tree-node" slot-scope=" { data }">
            <i :class="data.icon"></i>
            &nbsp;
            <span>{{ data.name }}</span>
          </span>
        </el-tree>

      </el-aside>

      <!-- 编辑查看区域 -->
      <el-main>
        <el-form :model="sysMenu" label-width="100px" ref="from" v-if="sysMenu != null">

          <el-form-item label="菜单名称" prop="name">
            <el-input type="text" v-model="sysMenu.name" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="父菜单" prop="parentId">
            <el-input type="text" v-model="sysMenu.parentId" autocomplete="off" :disabled="true">
              <template slot="prepend">拖拽实现</template>
            </el-input>
          </el-form-item>

          <el-form-item label="路由路径(接口路径)" prop="routerPath">
            <el-input type="text" v-model="sysMenu.routerPath" autocomplete="off" :disabled="sysMenu.type == 2">
              <template slot="prepend">http:// {{ host }}</template>
            </el-input>
          </el-form-item>

          <el-form-item label="路由组件" prop="routerComponent">
            <el-input v-model="sysMenu.routerComponent" :disabled="sysMenu.type == 2">
              <template slot="prepend">@/views</template>
            </el-input>
          </el-form-item>

          <el-form-item label="图标" prop="icon">
            <el-input v-model.number="sysMenu.icon" :disabled="sysMenu.type == 2">
              <template slot="prepend">
                <i :class="sysMenu.icon"></i>
              </template>
            </el-input>
          </el-form-item>

          <el-form-item label="类型" prop="order">
            <el-radio-group v-model="sysMenu.type">
              <el-radio-button label="0">菜单</el-radio-button>
              <el-radio-button label="1">页面</el-radio-button>
              <el-radio-button label="2">权限</el-radio-button>
            </el-radio-group>
          </el-form-item>

          <el-form-item label="排序" prop="order">
            <el-input v-model.number="sysMenu.order" :disabled="true">
              <template slot="prepend">拖拽实现</template>
            </el-input>
          </el-form-item>

          <el-form-item label="描述" prop="description">
            <el-input v-model="sysMenu.description"></el-input>
          </el-form-item>


          <el-form-item>
            <el-button type="primary" @click="submitForm">提交</el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
  </el-container>
</template>
<script>

import http from '@/utils/http.js'

export default {

  data() {
    return {
      sysMenuList: [],

      //当前选中的菜单
      sysMenu: null,

      host: null
    }
  },

  created() {
    this.host = window.location.host
    this.query()
  },

  methods: {
    query() {
      http.get("/home/system/sysMenu").then(resp => {
        if (resp.code == 500) {
          this.$message.error(resp.message)
          return;
        }

        this.sysMenuList = resp.data
      })
    },

    //菜单被点击的回调
    sysMenuClick(sysMenu) {
      this.sysMenu = sysMenu
    },

    //删除勾选的菜单
    async remove() {
      let ids = this.$refs.tree.getCheckedKeys()
      if (ids.length == 0) {
        return;
      }

      if (ids.length == 1) {
        await http.get("/home/system/sysMenu")
      } else {
        await http.get("/home/system/sysMenu", { data: { ids: ids } })
      }

      this.query()
    },

    //拖拽菜单时回调函数（阻止某些时候的拖拽）
    allowDrop(draggingNode, dropNode, type) {
      //如果放到菜单下面，必须是按钮（权限）
      if (dropNode.data.routerPath != null) {
        return false;
      }

      return true
    },

    //拖拽菜单成功时回调函数 before inner
    nodeDrop(draggingNode, dropNode, type, event) {
      if (type == "inner") {
        draggingNode.data.parentId = dropNode.data.id
      } else if (type == "before") {
        draggingNode.data.parentId = dropNode.data.parentId
        draggingNode.data.order = dropNode.data.order - 1
      } else if (type == "after") {
        draggingNode.data.parentId = dropNode.data.parentId
        draggingNode.data.order = dropNode.data.order + 1
      }

      let param = draggingNode.data;
      delete param.subMenu
      http.patch("/home/system/sysMenu", param).then(resp => {
        if (!(resp.data > 0)) {
          this.$message.error("修改失败")
        }
        this.query()
      })
    },

    submitForm() { },
    resetForm() {
      this.sysMenu = {
        ...this.sysMenu,
        name: null,
        routerPath: null,
        routerComponent: null,
        icon: null,
        description: null
      }
    }

  }
}
</script>

<style></style>