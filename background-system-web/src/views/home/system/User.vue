<template>

  <el-table :data="userList" border style="width: 100%">
    <el-table-column fixed prop="id" label="id" width="150">
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
    <el-table-column fixed="right" label="操作" width="100">
      <template>
        <el-button  type="text" size="small">查看</el-button>
        <el-button type="text" size="small">编辑</el-button>
      </template>
    </el-table-column>
  </el-table>
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
  }

}
</script>

<style>

</style>