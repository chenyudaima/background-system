<template>
  <el-container style="height: 100%;">

    <el-header style="text-align:center">
      <el-button>删除</el-button>
      <el-input placeholder="搜索" @keydown.enter.native="query" style="width: 20%" />
      <el-button>查询</el-button>
    </el-header>

    <el-main>
      <!-- 表格 -->
      <el-table style="width: 100%;height: 100%" :data="userList" border class="table">
        <el-table-column align="center" type="selection">
        </el-table-column>
        <el-table-column fixed prop="id" label="id">
        </el-table-column>
        <el-table-column prop="name" label="姓名">
        </el-table-column>
        <el-table-column prop="account" label="用户名">
        </el-table-column>
        <el-table-column prop="phone" label="手机号">
        </el-table-column>
        <el-table-column prop="age" label="年龄">
        </el-table-column>
        <el-table-column prop="sex" label="性别">
        </el-table-column>
        <el-table-column prop="status" label="状态">
        </el-table-column>
        <el-table-column label="操作">
          <template>
            <el-button type="text" size="small">查看</el-button>
            <el-button type="text" size="small">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>

    </el-main>
    <el-footer style="text-align:center">
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
        :page-sizes="pageSizes" :current-page="parseInt(this.$route.query.page)" :page-size="parseInt(this.$route.query.pageSize)"
        layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </el-footer>
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
      pageSizes: [10, 20],

      //总数据量
      total: 0,

      //当前页数据
      userList: [],

      name: ''
    }
  },

  created() {
    //判断是否有路径参数，没有则手动添加
    if (JSON.stringify(this.$route.query) == '{}') {
      this.$router.replace({ path: this.$router.path, query: { page: 1, pageSize: 10 } })
    } else {
      this.query()
    }
  },

  methods: {

    //查询
    query() {
      //从路径参数获取
      let query = this.$route.query

      http.get("/home/system/user", {
        params: { page: query.page, pageSize: query.pageSize }
      }).then(resp => {
        this.userList = resp.data.data
        this.total = resp.data.total
      })
    },

    //每页条数变化执行（修改路径参数）
    handleSizeChange(pageSize) {
      this.$router.replace({ path: this.$router.path, query: { ...this.$route.query, pageSize: pageSize}})
    },

    //当前页变化执行（修改路径参数）
    handleCurrentChange(page) {
      this.$router.replace({ path: this.$router.path, query: { ...this.$route.query, page: page}})
    }
  }

}
</script>

<style></style>