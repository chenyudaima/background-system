<template>
  <el-container style="height: 100%;">

    <!-- 按钮 -->
    <el-header style="text-align: left;height: @rowheight*10 !important;">

      <el-form :inline="true" @submit.native.prevent>

        <el-form-item label="菜单名称">
          <el-input v-model="querySysMenu.name" @keydown.enter.native="query" />
        </el-form-item>

        <el-form-item label="路由路径">
          <el-input v-model="querySysMenu.routerPath" @keydown.enter.native="query" />
        </el-form-item>

        <el-form-item label="路由组件">
          <el-input v-model="querySysMenu.routerComponent" @keydown.enter.native="query" />
        </el-form-item>

        <el-form-item>
          <el-button @click="query" type="primary">查询</el-button>
        </el-form-item>
        <br />

        <el-button @click="add" type="primary">增加</el-button>
        <el-button @click="remove" type="danger">删除</el-button>
        <el-button @click="exportExcel" type="primary">导出</el-button>

      </el-form>

    </el-header>

    <!-- 表格 -->
    <el-main>
      <!-- 表格 -->
      <el-table :cell-style="{ 'text-align': 'center' }" style="width: 100%;height: @rowheight*10 !important;"
        :data="sysMenuList" border ref="checkedTable" :header-cell-style="headerCellStyle">
        <el-table-column align="center" type="selection">
        </el-table-column>

        <el-table-column fixed prop="id" label="id">
        </el-table-column>

        <el-table-column prop="name" label="菜单名称">
        </el-table-column>

        <el-table-column prop="parentId" label="父菜单">
        </el-table-column>

        <el-table-column prop="routerPath" label="路由路径">
        </el-table-column>

        <el-table-column prop="routerComponent" label="路由组件">
        </el-table-column>

        <el-table-column prop="order" label="排序">
        </el-table-column>

        <el-table-column prop="description" label="描述">
        </el-table-column>

        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="show(scope.row)">查看</el-button>
            <el-button type="text" size="small" @click="update(scope.row)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>

    </el-main>

    <!-- 分页 -->
    <el-footer style="text-align:center;height: @rowheight*10 !important;">
      <!--
         分页
         background 是否为分页按钮添加背景色
         size-change 每页条数改变时触发
         current-change 当前页改变时触发
         current-page 当前页
         page-sizes 每页条数选择框
         page-size 每页条数
       -->
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :page-sizes="pageSizes" :current-page="parseInt(this.$route.query.page)"
        :page-size="parseInt(this.$route.query.pageSize)" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </el-footer>

    <!-- 表单 -->
    <el-dialog :title="formTitle" :visible.sync="dialog" width="30%" custom-class="dialogClass">

      <el-form :model="sysMenu" label-width="100px" ref="from">

        <el-form-item label="菜单名称" prop="name">
          <el-input type="text" v-model="sysMenu.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="父菜单" prop="parentId">
          <el-input type="text" v-model="sysMenu.parentId" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="路由路径" prop="routerPath">
          <el-input type="text" v-model="sysMenu.routerPath" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="路由组件" prop="routerComponent">
          <el-input v-model="sysMenu.routerComponent"></el-input>
        </el-form-item>

        <el-form-item label="排序" prop="order">
          <el-input v-model.number="sysMenu.order"></el-input>
        </el-form-item>

        <el-form-item label="描述" prop="description">
          <el-input v-model="sysMenu.description"></el-input>
        </el-form-item>


        <el-form-item v-if="dialogStatus != 3">
          <el-button type="primary" @click="submitForm">提交</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogClose">取 消</el-button>
      </span>
    </el-dialog>

  </el-container>
</template>
<script>

import http from '@/utils/http.js'

export default {
  watch: {
    //路径变化执行查询
    $route(route) {
      this.query()
    }
  },

  data() {
    return {
      //每页条数选择
      pageSizes: [5, 10],

      //总数据量
      total: 0,

      //当前页数据
      sysMenuList: [],

      //对话框
      dialog: false,

      //当前对话框状态 0关闭，1增加，2修改，3查看
      dialogStatus: 0,

      //表单标题，根据对话框状态进行切换
      formTitle: '',

      //默认数据模型
      sysMenu: {
        id: null,
        name: null,
        parentId: null,
        routerPath: null,
        routerComponent: null,
        order: null,
        description: null
      },

      querySysMenu: {
        name: null,
        routerPath: null,
        routerComponent: null
      }

    }
  },

  created() {

    //判断是否有路径参数，没有则手动添加
    if (JSON.stringify(this.$route.query) == '{}') {
      this.$router.replace({ path: this.$route.path, query: { page: 1, pageSize: 10 } })
    } else {
      this.query()
    }
  },

  methods: {

    //查询
    query() {
      //从路径参数获取
      let query = this.$route.query
      let param = {
        page: query.page,
        pageSize: query.pageSize,
        ...this.querySysMenu
      }

      for (var key in this.querySysMenu) {
        this.querySysMenu[key] = null
      }

      http.get("/home/system/sysMenu", { params: param }).then(resp => {
        if (resp.code != 200) {
          this.$router.replace({ path: this.$route.path, query: { page: 1, pageSize: 10 } })
          return;
        }
        this.total = resp.data.total
        if (resp.data.sysMenuList.length == 0 && query.page > 1) {
          this.$router.replace({ path: this.$route.path, query: { ...query, page: query.page - 1, } })
          return;
        }
        this.sysMenuList = resp.data.sysMenuList
      })
    },

    //每页条数变化执行（修改路径参数）
    handleSizeChange(pageSize) {
      this.$router.replace({ path: this.$route.path, query: { ...this.$route.query, pageSize: pageSize } })
    },

    //当前页变化执行（修改路径参数）
    handleCurrentChange(page) {
      this.$router.replace({ path: this.$route.path, query: { ...this.$route.query, page: page } })
    },

    //删除勾选的数据行
    async remove() {
      let ids = []
      this.$refs.checkedTable.selection.forEach(x => { ids.push(x.id) })

      if (ids.length == 0) {
        return;
      }

      if (ids.length == 1) {
        await http.delete("/home/system/sysMenu/" + ids[0])
      } else {
        await http.delete("/home/system/sysMenu/", { data: { ids: ids } })
      }

      this.query()
    },


    //提交表单
    submitForm() {
      let sysMenu = this.sysMenu

      //判断状态 1增加，2修改
      if (this.dialogStatus == 1) {
        http.post("/home/system/sysMenu", sysMenu).then(resp => {
          if (resp.code == 200) {
            this.query()
            this.dialog = false
          } else {
            this.$message.error(resp.message)
          }
        })
        return;
      }

      if (this.dialogStatus == 2) {
        http.patch("/home/system/sysMenu", sysMenu).then(resp => {
          if (resp.code == 200) {
            this.query()
            this.dialog = false
          } else {
            this.$message.error(resp.message)
          }
        })
        return;
      }

    },

    //重置表单
    resetForm() {
      this.sysMenu = {
        id: null,
        name: null,
        parentId: null,
        routerPath: null,
        routerComponent: null,
        order: null,
        description: null
      }
    },

    //增加
    add() {
      this.formTitle = "增加菜单"
      this.dialog = true
      this.dialogStatus = 1
      this.resetForm()
    },

    //修改
    update(sysMenu) {
      this.formTitle = "修改菜单"
      this.dialog = true
      this.sysMenu = { ...sysMenu }
      this.dialogStatus = 2
    },

    //查看
    show(sysMenu) {
      this.formTitle = "查看菜单"
      this.dialog = true
      this.sysMenu = { ...sysMenu }
      this.dialogStatus = 3

    },

    //关闭提示框
    dialogClose() {
      this.dialog = false
      this.dialogStatus = 0
    },

    exportExcel() {
      http.get("/home/system/sysMenu/exportExcel").then(resp => {
        let fileName = resp.headers['content-disposition'].split(';')[1].split('filename=')[1]

        const url = window.URL.createObjectURL(
          new Blob(['\uFEFF' + resp.data], {
            type: 'text/plain;charset=utf-8',
          })
        )

        const link = document.createElement('a')
        link.href = url
        link.setAttribute('download', fileName)
        document.body.appendChild(link)
        link.click()
      })
    },

    headerCellStyle() {
      return "background-color:#1989fa;color:#fff;font-weight:400";
    }
  }

}
</script>

<style>
.dialogClass {
  border-radius: 20px;
}
</style>