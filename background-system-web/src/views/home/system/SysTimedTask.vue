<template>
  <el-container style="height: 100%;">

    <!-- 按钮 -->
    <el-header style="text-align: left;height: @rowheight*10 !important;">

      <el-form :inline="true" @submit.native.prevent>

        <el-form-item label="任务类名">
          <el-input v-model="querySysTimedTask.className" placeholder="请输入内容" size="small"
            @keydown.enter.native="query" />
        </el-form-item>

        <el-form-item label="状态">
          <el-radio-group v-model="querySysTimedTask.status" size="small">
            <el-radio-button label="0">停止</el-radio-button>
            <el-radio-button label="1">运行</el-radio-button>
          </el-radio-group>
        </el-form-item>

        <el-form-item>
          <el-button @click="query" type="primary" size="small">查询</el-button>
        </el-form-item>
        <br />

        <el-button @click="add" type="primary" size="small">增加</el-button>
        <el-button @click="remove" type="danger" size="small">删除</el-button>

      </el-form>

    </el-header>

    <!-- 表格 -->
    <el-main>
      <!-- 表格 -->
      <el-table :cell-style="{ 'text-align': 'center' }" style="width: 100%;height: @rowheight*10 !important;"
        :data="sysTimedTaskList" border ref="checkedTable" :header-cell-style="headerCellStyle">
        <el-table-column align="center" type="selection">
        </el-table-column>

        <el-table-column fixed prop="id" label="id">
        </el-table-column>

        <el-table-column prop="className" label="任务类名">
        </el-table-column>

        <el-table-column prop="cron" label="cron表达式">
        </el-table-column>

        <el-table-column prop="param" label="参数">
        </el-table-column>

        <el-table-column prop="status" label="状态" width="80">
          <template slot-scope="scope">

            <div v-if="scope.row.status == 0"
              style="color: #FF0000; background-color: pink; border-color: #FF0000;border-style: solid; border-width: 1px">
              停止
            </div>
            <div v-else
              style="color: #00EE00; background-color: #C1FFC1; border-color: #00EE00;border-style: solid; border-width: 1px">
              运行
            </div>

          </template>
        </el-table-column>

        <el-table-column prop="description" label="日志" width="80">
          <template slot-scope="scope">
            <el-button @click="showLog(scope.row)" size="mini">查看</el-button>
          </template>
        </el-table-column>

        <el-table-column prop="description" label="描述">
        </el-table-column>

        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="show(scope.row)">查看</el-button>
            <el-button type="text" size="small" @click="update(scope.row)">编辑</el-button>
            <el-button type="text" size="small" @click="run(scope.row)">执行一次</el-button>
          </template>
        </el-table-column>
      </el-table>

    </el-main>

    <!-- 分页 -->
    <el-footer style="text-align:center; height: @rowheight*10 !important;">
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

    <!-- 查看日志 -->
    <el-dialog title="查看日志" :visible.sync="logDialog" width="70%" custom-class="dialogClass">

      <el-form :inline="true" @submit.native.prevent>

        <el-form-item label="状态">
          <el-radio-group v-model="sysTimedTaskLog.executeStatus" size="small">
            <el-radio-button label="0">异常</el-radio-button>
            <el-radio-button label="1">正常</el-radio-button>
          </el-radio-group>
        </el-form-item>

        <el-form-item>
          <el-button @click="queryLog" type="primary" size="small">查询</el-button>
        </el-form-item>
        <br />
      </el-form>


      <el-table :cell-style="{ 'text-align': 'center' }" style="width: 100%;height: @rowheight*10 !important;"
        :data="sysTimedTaskLogList" border ref="checkedTable" :header-cell-style="headerCellStyle">

        <!-- <el-table-column fixed prop="id" label="id">
        </el-table-column> -->

        <el-table-column prop="startExecuteTime" label="开始执行时间">
          <template slot-scope="scope">
            {{ new Date(scope.row.startExecuteTime).toLocaleString() }}
          </template>
        </el-table-column>

        <el-table-column prop="executeParam" label="执行参数">
        </el-table-column>

        <el-table-column prop="executeResult" label="执行结果">
          <!-- <template slot-scope="scope">
            <el-input
              :value="scope.row.executeResult"
              type="textarea" />
            
          </template> -->
        </el-table-column>

        <el-table-column prop="elapsedTime" label="执行时间（毫秒）">
        </el-table-column>

        <el-table-column prop="executeStatus" label="执行状态" width="80">
          <template slot-scope="scope">

            <div v-if="scope.row.executeStatus == 0"
              style="color: #FF0000; background-color: pink; border-color: #FF0000;border-style: solid; border-width: 1px">
              异常
            </div>
            <div v-else
              style="color: #00EE00; background-color: #C1FFC1; border-color: #00EE00;border-style: solid; border-width: 1px">
              正常
            </div>

          </template>
        </el-table-column>
      </el-table>

      <el-pagination background @size-change="logHandleSizeChange" @current-change="logHandleCurrentChange"
        :page-sizes="pageSizes" :current-page="logPage" :page-size="logPageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="logTotal">
      </el-pagination>
      <span slot="footer" class="dialog-footer">
        <el-button @click="logDialog = false">关闭</el-button>
      </span>
    </el-dialog>

    <!-- 表单 -->
    <el-dialog :title="formTitle" :visible.sync="dialog" width="40%" custom-class="dialogClass">

      <el-form :model="sysTimedTask" label-width="100px" ref="from">

        <el-form-item label="任务类名" prop="className">
          <el-input type="text" v-model="sysTimedTask.className" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="cron表达式" prop="cron">
          <el-input type="text" v-model="sysTimedTask.cron" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="参数" prop="param">
          <el-input type="textarea" v-model="sysTimedTask.param" autosize></el-input>
        </el-form-item>

        <el-form-item label="状态" prop="状态">
          <el-radio-group v-model="sysTimedTask.status">
            <el-radio-button label="0">停止</el-radio-button>
            <el-radio-button label="1">运行</el-radio-button>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="描述" prop="description">
          <el-input type="textarea" v-model="sysTimedTask.description" autosize></el-input>
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
      //获取路径参数
      let query = this.$route.query

      //判断是否有路径参数，没有则手动添加
      if (JSON.stringify(query) == '{}') {
        this.$router.replace({ path: this.$route.path, query: { page: 1, pageSize: 10 } })
        return;
      }

      http.get("/home/system/sysTimedTask", { params: query }).then(resp => {
        if (resp.code != 200) {
          this.$message.error(resp.message)
          this.$router.replace({ path: this.$route.path, query: { page: 1, pageSize: 10 } })
          return;
        }
        this.total = resp.data.total
        this.sysTimedTaskList = resp.data.sysTimedTaskList
        if (this.sysTimedTaskList.length == 0 && query.page > 1) {
          this.$router.replace({ path: this.$route.path, query: { ...query, page: query.page - 1, } })
          return;
        }

      })
    }
  },

  data() {
    return {
      //每页条数选择
      pageSizes: [5, 10],

      //总数据量
      total: 0,

      //当前页数据
      sysTimedTaskList: [],

      //对话框
      dialog: false,

      //当前对话框状态 0关闭，1增加，2修改，3查看
      dialogStatus: 0,

      //表单标题，根据对话框状态进行切换
      formTitle: '',

      //默认数据模型
      sysTimedTask: {
        id: null,
        className: null,
        cron: null,
        param: null,
        status: 0,
        description: null
      },

      querySysTimedTask: {
        className: null,
        status: null,
      },

      //查看日志对话框
      logDialog: false,

      //查看的日志
      sysTimedTaskLogList: [],

      //查看日志的总数量
      logTotal: 0,

      //查看日志的分页
      logPage: 1,
      logPageSize: 5,

      //数据模板
      sysTimedTaskLog: {
        timedTaskId: null,
        executeStatus: null
      }

    }
  },

  created() {
    this.query()
  },

  methods: {

    //查询
    query() {
      //从路径参数获取
      let query = this.$route.query

      //请求参数
      let param = { ...query }

      //清空查询输入
      let input = false;
      for (var key in this.querySysTimedTask) {
        if (this.querySysTimedTask[key] != null) {
          input = true;
          param[key] = this.querySysTimedTask[key]
          this.querySysTimedTask[key] = null
        }
      }

      if (input) {
        //说明用户有输入
        this.$router.replace({ path: this.$route.path, query: param })
      } else {
        //说明用户没有输入
        if (JSON.stringify(query) == "{}") {
          //说明直接点击的查询，相当于重置
          this.$router.replace({ path: this.$route.path, query: { page: 1, pageSize: 10 } })
        } else {
          //有可能是重复点击查询，也有可能是刷新页面
          this.$router.replace({ path: this.$route.path, query: {} })
        }
      }
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
        await http.delete("/home/system/sysTimedTask/" + ids[0])
      } else {
        await http.delete("/home/system/sysTimedTask/", { data: { ids: ids } })
      }

      this.query()
    },


    //提交表单
    submitForm() {
      let sysTimedTask = this.sysTimedTask

      //判断状态 1增加，2修改
      if (this.dialogStatus == 1) {
        http.post("/home/system/sysTimedTask", sysTimedTask).then(resp => {
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
        http.patch("/home/system/sysTimedTask", sysTimedTask).then(resp => {
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
      this.sysTimedTask = {
        id: null,
        className: null,
        cron: null,
        param: null,
        status: 0,
        description: null
      }
    },

    //增加
    add() {
      this.formTitle = "增加任务"
      this.dialog = true
      this.dialogStatus = 1
      this.resetForm()
    },

    //修改
    update(sysTimedTask) {
      this.formTitle = "修改任务"
      this.dialog = true
      this.sysTimedTask = { ...sysTimedTask }
      this.dialogStatus = 2
    },

    //查看
    show(sysTimedTask) {
      this.formTitle = "查看任务"
      this.dialog = true
      this.sysTimedTask = { ...sysTimedTask }
      this.dialogStatus = 3

    },

    //关闭提示框
    dialogClose() {
      this.dialog = false
      this.dialogStatus = 0
    },

    headerCellStyle() {
      return "background-color:#1989fa;color:#fff;font-weight:400";
    },

    showLog(sysTimedTask) {
      this.logDialog = true
      this.sysTimedTaskLog.timedTaskId = sysTimedTask.id
      this.queryLog()
    },

    queryLog() {
      let param = {
        ...this.sysTimedTaskLog,
        page: this.logPage,
        pageSize: this.logPageSize
      }


      http.get("/home/system/sysTimedTask/log", { params: param }).then(resp => {
        this.sysTimedTaskLogList = resp.data.sysTimedTaskLogList
        this.logTotal = resp.data.total
      })
    },

    logHandleSizeChange(pageSize) {
      this.logPageSize = pageSize
      this.queryLog()
    },
    logHandleCurrentChange(page) {
      this.logPage = page
      this.queryLog()
    },
    run(sysTimedTask) {
      http.post("/home/system/sysTimedTask/run", sysTimedTask).then(resp => {
        if (resp.code == 200) {
          this.$message.success("执行成功")
        } else {
          this.$message.error(resp.message)
        }
      })
    }
  }

}
</script>

<style>
.dialogClass {
  border-radius: 20px;
}
</style>