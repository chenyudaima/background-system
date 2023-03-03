<template>
  <el-container style="height: 100%;">

    <!-- 按钮 -->
    <el-header style="text-align: left;height: @rowheight*10 !important;">

      <el-form :inline="true" @submit.native.prevent>

        <el-form-item label="角色名称">
          <el-input v-model="queryRole.name" @keydown.enter.native="query" />
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
      <el-table style="width: 100%;height: @rowheight*10 !important;" :data="roleList" border ref="checkedTable"
        :header-cell-style="headerCellStyle">
        <el-table-column align="center" type="selection">
        </el-table-column>

        <el-table-column fixed prop="id" label="id">
        </el-table-column>

        <el-table-column prop="name" label="角色名称">
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

      <el-form :model="role" label-width="100px" ref="from">

        <el-form-item label="角色名称" prop="name">
          <el-input type="text" v-model="role.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="描述" prop="description">
          <el-input v-model="role.description"></el-input>
        </el-form-item>

        <el-form-item label="角色菜单" prop="description">
          <el-tree ref="tree" :data="menuList" show-checkbox node-key="id" :props="{ children: 'children', label: 'name'}">
          </el-tree>
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
      roleList: [],

      //对话框
      dialog: false,

      //当前对话框状态 0关闭，1增加，2修改，3查看
      dialogStatus: 0,

      //表单标题，根据对话框状态进行切换
      formTitle: '',

      menuList: [],

      //默认数据模型
      role: {
        id: null,
        name: null,
        description: null,
        menuIds: []
      },

      queryRole: {
        name: null
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
        ...this.queryRole
      }

      for (var key in this.queryRole) {
        this.queryRole[key] = null
      }

      http.get("/home/system/role", { params: param }).then(resp => {
        if (resp.code != 200) {
          this.$router.replace({ path: this.$route.path, query: { page: 1, pageSize: 10 } })
          return;
        }

        this.total = resp.data.total
        this.menuList = resp.data.menuList
        if (resp.data.roleList.length == 0 && query.page > 1) {
          this.$router.replace({ path: this.$route.path, query: { ...query, page: query.page - 1, } })
          return;
        }
        this.roleList = resp.data.roleList
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
        await http.delete("/home/system/role/" + ids[0])
      } else {
        await http.delete("/home/system/role/", { data: { ids: ids } })
      }

      this.query()
    },


    //提交表单
    submitForm() {
      this.role.menuIds = this.$refs.tree.getCheckedKeys()
      let role = this.role

      //判断状态 1增加，2修改
      if (this.dialogStatus == 1) {
        http.post("/home/system/role", role).then(resp => {
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
        http.patch("/home/system/role", role).then(resp => {
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
      this.role = {
        id: null,
        name: null,
        description: null,
        menuIds: []
      }
      
      this.$refs.tree.setCheckedKeys([])
    },

    //增加
    add() {
      this.formTitle = "增加角色"
      this.dialog = true
      this.dialogStatus = 1
      this.$nextTick(() => {
        this.resetForm()
      })
      
    },

    //修改
    update(role) {
      this.formTitle = "修改角色"
      this.dialog = true
      this.role = { ...role }
      this.dialogStatus = 2

      this.$nextTick(() => {
        this.$refs.tree.setCheckedKeys([])
        role.menuIds.forEach(x => {
          this.$refs.tree.setChecked(x, true)
        })
      })
    },

    //查看
    show(role) {
      this.formTitle = "查看角色"
      this.dialog = true
      this.role = { ...role }
      this.dialogStatus = 3

      this.$nextTick(() => {
        this.$refs.tree.setCheckedKeys([])
        role.menuIds.forEach(x => {
          this.$refs.tree.setChecked(x, true)
        })
      })
      
    },

    //关闭提示框
    dialogClose() {
      this.dialog = false
      this.dialogStatus = 0
    },

    //导出excel
    exportExcel() {
      http.get("/home/system/role/exportExcel").then(resp => {
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

    //表头样式
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