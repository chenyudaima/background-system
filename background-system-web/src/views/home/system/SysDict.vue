<template>
  <el-container style="height: 100%;">

    <!-- 按钮 -->
    <el-header style="text-align: left;height: @rowheight*10 !important;">

      <el-form :inline="true" @submit.native.prevent>

        <el-form-item label="字典类型名称">
          <el-input v-model="querySysDictType.name" @keydown.enter.native="query" size="small" placeholder="请输入内容" />
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
        :data="sysDictTypeList" border ref="checkedTable" :header-cell-style="headerCellStyle">
        <el-table-column align="center" type="selection">
        </el-table-column>

        <el-table-column fixed prop="id" label="id">
        </el-table-column>

        <el-table-column prop="name" label="字典类型名称">
        </el-table-column>

        <el-table-column prop="description" label="描述">
        </el-table-column>

        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="show(scope.row)">查看字典</el-button>
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

    <!-- 字典类型表单 -->
    <el-dialog :title="formTitle" :visible.sync="dialog" width="30%" custom-class="dialogClass">

      <el-form :model="sysDictType" label-width="100px" ref="from">

        <el-form-item label="字典类型名称" prop="name">
          <el-input type="text" v-model="sysDictType.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="描述" prop="description">
          <el-input v-model="sysDictType.description"></el-input>
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

    <!-- 字典数据表格 -->
    <el-dialog :visible.sync="showDialog" width="70%" custom-class="dialogClass">
      <el-form :inline="true" @submit.native.prevent>

        <el-form-item label="字典名称">
          <el-input v-model="querySysDictData.name" @keydown.enter.native="queryDictData" size="small"
            placeholder="请输入内容" />
        </el-form-item>

        <el-form-item>
          <el-button @click="queryDictData" type="primary" size="small">查询</el-button>
        </el-form-item>
        <br />

        <el-button @click="addDictData" type="primary" size="small">增加</el-button>
        <el-button @click="removeDictData" type="danger" size="small">删除</el-button>

      </el-form>

      <el-table :cell-style="{ 'text-align': 'center' }" style="width: 100%;height: @rowheight*10 !important;"
        :data="sysDictDataList" border ref="dictDataCheckedTable" :header-cell-style="headerCellStyle">
        <el-table-column align="center" type="selection">
        </el-table-column>

        <el-table-column prop="name" label="字典名称">
        </el-table-column>

        <el-table-column prop="value" label="字典值">
        </el-table-column>

        <el-table-column prop="description" label="描述">
        </el-table-column>

        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="updateDictData(scope.row)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>


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
        <el-pagination background @size-change="dictDataHandleSizeChange" @current-change="dictDataHandleCurrentChange"
          :page-sizes="pageSizes" :current-page="dictDataPage" :page-size="dictDataPageSize"
          layout="total, sizes, prev, pager, next, jumper" :total="dictDataTotal">
        </el-pagination>
      </el-footer>

    </el-dialog>

    <!-- 字典数据表单 -->
    <el-dialog :title="sysDictFormTitle" :visible.sync="dictDataDialog" width="30%" custom-class="dialogClass">

      <el-form :model="sysDictData" label-width="100px" ref="from">

        <el-form-item label="字典名称" prop="name">
          <el-input type="text" v-model="sysDictData.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="字典值" prop="name">
          <el-input type="text" v-model="sysDictData.value" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="描述" prop="description">
          <el-input v-model="sysDictData.description"></el-input>
        </el-form-item>

        <el-form-item label="排序" prop="order">
          <el-input type="text" v-model="sysDictData.order" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="sysDictDataSubmitForm">提交</el-button>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dictDataDialogClose">取 消</el-button>
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
      
      http.get("/home/system/sysDict/type", { params: query }).then(resp => {
        if (resp.code != 200) {
          this.$message.error(resp.message)
          this.$router.replace({ path: this.$route.path, query: { page: 1, pageSize: 10 } })
          return;
        }

        this.total = resp.data.total
        this.sysDictTypeList = resp.data.sysDictTypeList
        if (this.sysDictTypeList.length == 0 && query.page > 1) {
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

      //字典类型数据
      sysDictTypeList: [],

      //对话框
      dialog: false,

      //当前对话框状态 0关闭，1增加，2修改，3查看
      dialogStatus: 0,

      //表单标题，根据对话框状态进行切换
      formTitle: '',

      //字典类型表单数据
      sysDictType: {
        id: null,
        name: null,
        description: null,
      },

      //字典类型查询参数
      querySysDictType: {
        name: null
      },

      //是否显示字典数据
      showDialog: false,

      //字典数据查询参数
      querySysDictData: {
        id: null,
        dictTypeId: null,
        name: null,
        value: null,
        description: null
      },

      //字典数据
      sysDictDataList: [],
      dictDataTotal: null,
      dictDataPage: 1,
      dictDataPageSize: 5,

      dictDataDialog: false,

      sysDictData: {
        id: null,
        dictTypeId: null,
        name: null,
        value: null,
        description: null
      },

      dictDataDialogStatus: 0,

      sysDictFormTitle: ""

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
      for (var key in this.querySysDictType) {
        if (this.querySysDictType[key] != null) {
          input = true;
          param[key] = this.querySysDictType[key]
          this.querySysDictType[key] = null
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
        await http.delete("/home/system/sysDict/type/" + ids[0])
      } else {
        await http.delete("/home/system/sysDict/type/", { data: { ids: ids } })
      }

      this.query()
    },


    //提交表单
    submitForm() {
      let sysDictType = this.sysDictType

      //判断状态 1增加，2修改
      if (this.dialogStatus == 1) {
        http.post("/home/system/sysDict/type", sysDictType).then(resp => {
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
        http.patch("/home/system/sysDict/type", sysDictType).then(resp => {
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
      this.sysDictType = {
        id: null,
        name: null,
        description: null
      }

    },

    //增加
    add() {
      this.formTitle = "增加字典类型"
      this.dialog = true
      this.dialogStatus = 1
      this.$nextTick(() => {
        this.resetForm()
      })

    },

    //修改
    update(sysDictType) {
      this.formTitle = "修改字典类型"
      this.dialog = true
      this.sysDictType = { ...sysDictType }
      this.dialogStatus = 2
    },

    //查看
    show(sysDictType) {
      this.querySysDictData.dictTypeId = sysDictType.id
      this.showDialog = true
      this.queryDictData()
    },

    queryDictData() {
      let param = { ...this.querySysDictData, page: this.dictDataPage, pageSize: this.dictDataPageSize }

      http.get("/home/system/sysDict/data", { params: param }).then(resp => {
        if (resp.code != 200) {
          this.$message.error(resp.message)
          return;
        }
        this.sysDictDataList = resp.data.sysDictDataList
        this.dictDataTotal = resp.data.total
      })
    },

    //关闭提示框
    dialogClose() {
      this.dialog = false
      this.dialogStatus = 0
    },

    //表头样式
    headerCellStyle() {
      return "background-color:#1989fa;color:#fff;font-weight:400";
    },

    dictDataHandleSizeChange(pageSize) {
      this.dictDataPageSize = pageSize
      this.queryDictData()
    },

    dictDataHandleCurrentChange(page) {
      this.dictDataPage = page
      this.queryDictData()
    },

    addDictData() {
      this.sysDictFormTitle = "增加字典"
      this.dictDataDialog = true
      this.dictDataDialogStatus = 1
      this.sysDictData = {
        id: null,
        dictTypeId: this.querySysDictData.dictTypeId,
        name: null,
        value: null,
        description: null
      }

    },

    updateDictData(sysDictData) {
      this.sysDictFormTitle = "修改字典"
      this.sysDictData = sysDictData
      this.dictDataDialogStatus = 2
      this.dictDataDialog = true
    },

    dictDataDialogClose() {
      this.dictDataDialog = false
      this.dictDataDialogStatus = 0
    },

    sysDictDataSubmitForm() {
      let sysDictData = this.sysDictData

      //判断状态 1增加，2修改
      if (this.dictDataDialogStatus == 1) {
        http.post("/home/system/sysDict/data", sysDictData).then(resp => {
          if (resp.code == 200) {
            this.queryDictData()
            this.dictDataDialogClose()
          } else {
            this.$message.error(resp.message)
          }
        })
        return;
      }

      if (this.dictDataDialogStatus == 2) {
        http.patch("/home/system/sysDict/data", sysDictData).then(resp => {
          if (resp.code == 200) {
            this.queryDictData()
            this.dictDataDialogClose()
          } else {
            this.$message.error(resp.message)
          }
        })
        return;
      }
    },

    async removeDictData() {
      let ids = []
      this.$refs.dictDataCheckedTable.selection.forEach(x => { ids.push(x.id) })
      if (ids.length == 0) {
        return;
      }

      if (ids.length == 1) {
        await http.delete("/home/system/sysDict/data/" + ids[0])
      } else {
        await http.delete("/home/system/sysDict/data/", { data: { ids: ids } })
      }

      this.queryDictData()
    }

  }

}
</script>

<style>
.dialogClass {
  border-radius: 20px;
}
</style>