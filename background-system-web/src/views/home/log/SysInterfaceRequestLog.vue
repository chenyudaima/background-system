<template>
  <el-container style="height: 100%;">

    <!-- 按钮 -->
    <el-header style="text-align: left;height: @rowheight*10 !important;">

      <el-form :inline="true" @submit.native.prevent>

        <el-form-item label="请求方法">
          <el-select size="small" v-model="querySysInterfaceRequestLog.requestMethod" placeholder="请选择">
            <el-option key="GET" label="GET" value="GET" />
            <el-option key="POST" label="POST" value="POST" />
            <el-option key="DELETE" label="DELETE" value="DELETE" />
            <el-option key="PATCH" label="PATCH" value="PATCH" />
            <el-option key="PUT" label="PUT" value="PUT" />
          </el-select>
        </el-form-item>
        
        <el-form-item label="状态">
          <el-radio-group v-model="querySysInterfaceRequestLog.status" size="small">
            <el-radio-button label="0">异常</el-radio-button>
            <el-radio-button label="1">正常</el-radio-button>
          </el-radio-group>
        </el-form-item>

        <el-form-item>
          <el-button @click="query" type="primary" size="small">查询</el-button>
        </el-form-item>
        <br />
      </el-form>

    </el-header>

    <!-- 表格 -->
    <el-main>
      <!-- 表格 -->
      <el-table :cell-style="{ 'text-align': 'center' }" style="width: 100%;height: @rowheight*10 !important;"
        :data="sysInterfaceRequestLogList" border ref="checkedTable" :header-cell-style="headerCellStyle">
        
        <!-- <el-table-column align="center" type="selection">
        </el-table-column> -->

        <!-- <el-table-column fixed prop="id" label="id" width="180">
        </el-table-column> -->

        <el-table-column prop="requestUrl" label="请求路径" width="450">
        </el-table-column>

        <el-table-column prop="requestParam" label="请求参数" width="80">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="show(scope.row.requestParam)">查看</el-button>
          </template>
        </el-table-column>

        <el-table-column prop="requestMethod" label="请求方法" width="90">
        </el-table-column>

        <el-table-column prop="responseResult" label="响应结果" width="80">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="show(scope.row.responseResult)">查看</el-button>
          </template>
        </el-table-column>

        <el-table-column prop="status" label="状态" width="90">
          <template slot-scope="scope">
            <div v-if="scope.row.status == 0"
              style="color: #FF0000; background-color: pink; border-color: #FF0000;border-style: solid; border-width: 1px">
              异常
            </div>
            <div v-else
              style="color: #00EE00; background-color: #C1FFC1; border-color: #00EE00;border-style: solid; border-width: 1px">
              正常
            </div>
          </template>
        </el-table-column>

        <el-table-column prop="spendTime" label="消耗时间(毫秒)" width="150">
        </el-table-column>

        <el-table-column prop="requestIp" label="请求IP" width="150">
        </el-table-column>

        <el-table-column prop="userId" label="请求用户" width="150">
        </el-table-column>

        <el-table-column fixed="right" prop="requestTime" label="请求时间" width="150">
          <template slot-scope="scope">
            {{ new Date(scope.row.requestTime).toLocaleString() }}
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

    <el-dialog :visible.sync="dialog" width="60%" custom-class="dialogClass">
      <el-input type="textarea" v-model="text" autosize readonly/>
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
      sysInterfaceRequestLogList: [],

      //查询参数
      querySysInterfaceRequestLog: {
        requestMethod: null,
        status: null
      },

      //对话框显示
      dialog: false,

      text: ''
    }
  },

  created() {
    this.query()
  },

  methods: {

    //查询
    query() {
      //判断是否有路径参数，没有则手动添加
      if (JSON.stringify(this.$route.query) == '{}') {
        this.$router.replace({ path: this.$route.path, query: { page: 1, pageSize: 10 } })
        return;
      }

      //从路径参数获取
      let query = this.$route.query
      let param = {
        page: query.page,
        pageSize: query.pageSize,
        ...this.querySysInterfaceRequestLog
      }

      for (var key in this.querySysInterfaceRequestLog) {
        this.querySysInterfaceRequestLog[key] = null
      }

      http.get("/home/log/sysInterfaceRequestLog", { params: param }).then(resp => {
        if (resp.code == 500) {
          this.$message.error(resp.message)
          return;
        }

        if (resp.code != 200) {
          this.$router.replace({ path: this.$route.path, query: { page: 1, pageSize: 10 } })
          return;
        }

        this.total = resp.data.total
        this.sysInterfaceRequestLogList = resp.data.sysInterfaceRequestLogList
        if (resp.data.sysInterfaceRequestLogList.length == 0 && query.page > 1) {
          this.$router.replace({ path: this.$route.path, query: { ...query, page: query.page - 1, } })
          return;
        }
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


    //查看
    show(text) {
      this.text = text
      this.dialog = true
    },

    //表头样式
    headerCellStyle() {
      return "background-color:#1989fa;color:#fff;font-weight:400";
    },
  }


}
</script>

<style>
.dialogClass {
  border-radius: 20px;
}
</style>