<template>
  <el-container style="height: 100%;">

    <!-- 按钮 -->
    <el-header style="text-align: left;height: @rowheight*10 !important;">
      <el-form :inline="true" @submit.native.prevent>

        <el-form-item label="姓名">
          <el-input v-model="queryUser.name" @keydown.enter.native="query" size="small" placeholder="请输入内容" />
        </el-form-item>

        <el-form-item label="用户名">
          <el-input v-model="queryUser.account" @keydown.enter.native="query" size="small" placeholder="请输入内容" />
        </el-form-item>

        <el-form-item label="状态">
          <el-radio-group v-model="queryUser.status" size="small">
            <el-radio-button label="0">冻结</el-radio-button>
            <el-radio-button label="1">正常</el-radio-button>
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
        :data="userList" border ref="checkedTable" :header-cell-style="headerCellStyle">
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
          <template slot-scope="scope">{{ scope.row.sex == 0 ? '男' : '女' }}</template>
        </el-table-column>

        <el-table-column prop="status" label="状态">
          <template slot-scope="scope">{{ scope.row.status == 1 ? '正常' : '冻结' }}</template>
        </el-table-column>

        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="show(scope.row)">查看</el-button>
            <el-button type="text" size="small" @click="update(scope.row)">编辑</el-button>
            <el-button type="text" size="small" @click="updatePassword(scope.row)">修改密码</el-button>
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

      <el-form :model="sysUser" label-width="100px" ref="from">

        <el-form-item label="姓名" prop="name">
          <el-input type="text" v-model="sysUser.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="用户名" prop="account">
          <el-input type="text" v-model="sysUser.account" autocomplete="off" :disabled="dialogStatus != 1"></el-input>
        </el-form-item>

        <el-form-item label="密码" prop="password" v-if="dialogStatus == 1">
          <el-input type="password" v-model="sysUser.password" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="手机号" prop="phone">
          <el-input v-model.number="sysUser.phone"></el-input>
        </el-form-item>

        <el-form-item label="年龄" prop="age">
          <el-input v-model.number="sysUser.age"></el-input>
        </el-form-item>

        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="sysUser.sex">
            <el-radio :label="0">男</el-radio>
            <el-radio :label="1">女</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="sysUser.status">
            <el-radio :label="0">冻结</el-radio>
            <el-radio :label="1">正常</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="角色" prop="roleIds">
          <el-select v-model="sysUser.roleIds" placeholder="请选择" multiple>
            <el-option v-for="item in roleList" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
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

    <!-- 修改密码表单 -->
    <el-dialog title="修改密码" :visible.sync="updatePasswordDialog" width="30%" custom-class="dialogClass">

      <el-form :model="sysUser" label-width="100px" ref="from">

        <el-form-item label="用户名" prop="account">
          <el-input type="text" v-model="sysUser.account" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>

        <el-form-item label="新密码" prop="name">
          <el-input type="password" v-model="sysUser.password" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm">提交</el-button>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="updatePasswordDialog = false">取 消</el-button>
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
      let query = this.$route.query
      //判断是否有路径参数，没有则手动添加
      if (JSON.stringify(query) == '{}') {
        this.$router.replace({ path: this.$route.path, query: { page: 1, pageSize: 10 } })
        return;
      }

      http.get("/home/system/sysUser", { params: query }).then(resp => {
        if (resp.code != 200) {
          this.$message.error(resp.message)
          this.$router.replace({ path: this.$route.path, query: { page: 1, pageSize: 10 } })
          return;
        }

        this.total = resp.data.total
        this.roleList = resp.data.roleList
        this.userList = resp.data.userList
        if (this.userList.length == 0 && query.page > 1) {
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
      userList: [],

      //所有角色
      roleList: [],

      //对话框
      dialog: false,

      //当前对话框状态 0关闭，1增加，2修改，3查看
      dialogStatus: 0,

      //表单标题，根据对话框状态进行切换
      formTitle: '',

      //默认数据模型
      sysUser: {
        id: null,
        name: null,
        account: null,
        password: null,
        phone: null,
        age: null,
        sex: 0,
        status: 1,
        roleIds: []
      },

      queryUser: {
        name: null,
        account: null,
        status: null
      },

      updatePasswordDialog: false

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

      let param = { ...query }

      //清空查询输入
      let input = false;
      for (var key in this.queryUser) {
        if (this.queryUser[key] != null) {
          input = true;
          param[key] = this.queryUser[key]
          this.queryUser[key] = null
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
        await http.delete("/home/system/sysUser/" + ids[0])
      } else {
        await http.delete("/home/system/sysUser/", { data: { ids: ids } })
      }

      this.query()
    },


    //提交表单
    submitForm() {
      let sysUser = this.sysUser

      //判断状态 1增加，2修改
      if (this.dialogStatus == 1) {
        http.post("/home/system/sysUser", sysUser).then(resp => {
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
        http.patch("/home/system/sysUser", sysUser).then(resp => {
          if (resp.code == 200) {
            this.query()
            this.dialog = false
            this.updatePasswordDialog = false
            this.$message.success("修改成功")
          } else {
            this.$message.error(resp.message)
          }
        })
        return;
      }

    },

    //重置表单
    resetForm() {
      this.sysUser = {
        id: null,
        name: null,
        account: null,
        password: null,
        phone: null,
        age: null,
        sex: 0,
        status: 1,
        roleIds: []
      }
    },

    //增加
    add() {
      this.formTitle = "增加用户"
      this.dialog = true
      this.dialogStatus = 1
      this.resetForm()
    },

    //修改
    update(sysUser) {
      this.formTitle = "修改用户"
      this.dialog = true
      this.sysUser = { ...sysUser }
      this.dialogStatus = 2
    },

    //查看
    show(sysUser) {
      this.formTitle = "查看用户"
      this.dialog = true
      this.sysUser = { ...sysUser }
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

    updatePassword(sysUser) {
      this.dialogStatus = 2
      this.sysUser = { ...sysUser }
      this.updatePasswordDialog = true
    }
  }

}
</script>

<style>
.dialogClass {
  border-radius: 20px;
}
</style>