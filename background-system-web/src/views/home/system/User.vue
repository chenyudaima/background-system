<template>
  <div>

    <p>
      <!-- 按钮和查询 -->
      <el-button>删除</el-button>
      <el-input placeholder="搜索" @keydown.enter.native="query" style="width: 20%" />
      <el-button>查询</el-button>
    </p>

    <!-- 表格 -->
    <el-table :data="userList" border style="width: 70%" class="table">
      <el-table-column align="center" type="selection">
        </el-table-column>
      <el-table-column fixed prop="id" label="id" width="120">
      </el-table-column>
      <el-table-column prop="name" label="姓名" width="120">
      </el-table-column>
      <el-table-column prop="account" label="用户名" width="120">
      </el-table-column>
      <el-table-column prop="phone" label="手机号" width="120">
      </el-table-column>
      <el-table-column prop="age" label="年龄" width="120">
      </el-table-column>
      <el-table-column prop="sex" label="性别" width="120">
      </el-table-column>
      <el-table-column prop="status" label="状态" width="120">
      </el-table-column>
      <el-table-column label="操作" width="100">
        <template>
          <el-button type="text" size="small">查看</el-button>
          <el-button type="text" size="small">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage4"
      :page-sizes="[100, 200, 300, 400]" :page-size="100" layout="total, sizes, prev, pager, next, jumper" :total="400">
    </el-pagination>
  </div>
</template>
<script>

import http from '@/utils/http.js'
export default {
  data() {
    return {
      userList: []
    }
  },
  created() {
    http.get("/home/system/user").then(resp => {
      this.userList = resp.data
    })
  },
  methods: {
    //查询
    query() {

    }
  }

}
</script>

<style>
.table {
  margin-bottom: 20px;
  margin-top: 20px;
}
</style>