<template>
  <el-container style="height: 100%;">

    <!-- 按钮区域 -->
    <el-header style="height: @rowheight*10 !important">
      <el-button @click="add" type="primary" size="small">添加</el-button>
      <el-button @click="remove" type="danger" size="small">删除</el-button>
    </el-header>

    <el-container>

      <!-- 菜单权限Tree区域 -->
      <el-aside width="500px">

        <el-tree :default-expanded-keys="defaultExpandedKeys" ref="tree" :draggable="true" @node-drop="nodeDrop" :allow-drop="allowDrop" :data="sysMenuList"
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

          <!-- <el-form-item label="父菜单" prop="parentId">
            <el-input type="text" v-model="sysMenu.parentId" autocomplete="off" :disabled="true">
              <template slot="prepend">拖拽实现</template>
            </el-input>
          </el-form-item> -->

          <el-form-item label="路由路径" prop="routerPath" v-if="sysMenu.type == 1">
            <el-input type="text" v-model="sysMenu.routerPath" autocomplete="off">
              <template slot="prepend">{{ protocol }}//{{ host }}</template>
            </el-input>
          </el-form-item>
          <el-form-item label="接口路径" prop="routerPath" v-if="sysMenu.type == 2">
            <el-input type="text" v-model="sysMenu.routerPath" autocomplete="off">
              <template slot="prepend">Controller</template>
            </el-input>
          </el-form-item>


          <el-form-item label="路由组件" prop="routerComponent" v-if="sysMenu.type == 1">
            <el-input v-model="sysMenu.routerComponent">
              <template slot="prepend">@/views</template>
            </el-input>
          </el-form-item>

          <el-form-item label="图标" prop="icon" v-if="sysMenu.type != 2">
            <el-input v-model.number="sysMenu.icon">
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

          <!-- <el-form-item label="排序" prop="order">
            <el-input v-model.number="sysMenu.order" :disabled="true">
              <template slot="prepend">拖拽实现</template>
            </el-input>
          </el-form-item> -->

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

      protocol: null,
      host: null,

      defaultExpandedKeys: []
    }
  },

  created() {
    this.protocol = window.location.protocol
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
      if(this.defaultExpandedKeys.indexOf(sysMenu.id) == -1) {
        this.defaultExpandedKeys.push(sysMenu.id)
      }else {
        this.defaultExpandedKeys = this.defaultExpandedKeys.filter(id => id != sysMenu.id)
      }
      
      this.sysMenu = sysMenu
    },

    //删除勾选的菜单
    async remove() {
      let ids = this.$refs.tree.getCheckedKeys()
      if (ids.length == 0) {
        return;
      }

      if (ids.length == 1) {
        await http.delete("/home/system/sysMenu/" + ids[0])
      } else {
        await http.delete("/home/system/sysMenu", { data: { ids: ids } })
      }

      this.query()
    },

    add() {
      if (this.sysMenu == null || this.sysMenu.type == 2) {
        this.$message.error("请点击要添加到哪个菜单/页面下面")
        return;
      }

      let type = 0;

      if (this.sysMenu.type == 1) {
        type = 2
      } else if (this.sysMenu.type == 0) {
        type = 1
      }

      this.sysMenu = {
        id: null,
        name: null,
        routerPath: null,
        routerComponent: null,
        icon: null,
        type: type,
        order: 1,
        parentId: this.sysMenu.id,
        description: null,
        subMenu: null
      }

    },

    //拖拽菜单时回调函数（阻止某些时候的拖拽）
    allowDrop(draggingNode, dropNode, type) {

      //如果拖动的是菜单或者页面，只能放菜单下面
      if ((draggingNode.data.type == 0 || draggingNode.data.type == 1) && dropNode.data.type != 0) {
        return false;
      }

      //如果拖动的是权限，只能放页面下面
      if (draggingNode.data.type == 2 && dropNode.data.type != 1) {
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

    submitForm() {
      let sysMenu = this.sysMenu
      delete sysMenu.subMenu
      if (this.sysMenu.id == null) {
        http.post("/home/system/sysMenu", sysMenu).then(resp => {
          if (resp.code == 200) {
            this.$message.success("添加成功")
            this.query()
          } else {
            this.$message.error(resp.message)
          }
        })
      } else {
        http.patch("/home/system/sysMenu", sysMenu).then(resp => {
          if (resp.code == 200) {
            this.$message.success("修改成功")
            this.query()
          } else {
            this.$message.error(resp.message)
          }
        })
      }
    },

    resetForm() {
      this.sysMenu = {
        ...this.sysMenu,
        name: null,
        routerPath: null,
        routerComponent: null,
        icon: null,
        type: 0,
        description: null
      }
    }

  }
}
</script>

<style></style>