<template>
  <el-container style="height: 100%;">

    <!-- 按钮 -->
    <el-header style="text-align: left;height: @rowheight*10 !important;">

      <el-form :inline="true" @submit.native.prevent>
        <el-form-item label="OK/NG">
          <el-radio-group v-model="querySysInterfaceRequestLog.status" size="small">
            <el-radio-button label="0">OK</el-radio-button>
            <el-radio-button label="1">NG</el-radio-button>
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
        :data="certificateCheckLogList" border ref="checkedTable" :header-cell-style="headerCellStyle">

        <!-- <el-table-column align="center" type="selection">
        </el-table-column> -->
        <!-- 
        <el-table-column fixed prop="id" label="id" width="180">
        </el-table-column> -->

        <el-table-column prop="certificateData" label="合格证数据" show-overflow-tooltip>
        </el-table-column>

        <el-table-column prop="nameplateData" label="铭牌识别数据" show-overflow-tooltip>
        </el-table-column>

        <el-table-column prop="frontierMarkingData" label="拓印号识别数据" show-overflow-tooltip>
        </el-table-column>

        <el-table-column label="核对结果" width="80">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="show(scope.row)">核对结果</el-button>
          </template>
        </el-table-column>

        <el-table-column prop="type" label="类型" width="80">
          <template slot-scope="scope">
            {{ scope.row.type == 0 ? "上线" : "下线" }}
          </template>
        </el-table-column>


        <el-table-column fixed="right" prop="createTime" label="质检时间" width="150">
          <template slot-scope="scope">
            {{ new Date(scope.row.createTime).toLocaleString() }}
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

    <el-dialog :visible.sync="dialog" width="100%" custom-class="dialogClass">

      <el-row>
        <el-col :span="12">
          <img width="450px" height="450px" :src="'data:image/png;base64,' + showCertificateCheckLog.nameplateBase64" />
        </el-col>

        <el-col :span="12">
          <div style="text-align: center;font-size: 26px;font-weight:bolder;color:black">
            <p>品牌: <span v-html="showCertificateCheckLog.certificateData.brand"></span></p>
            <p>制造国: <span v-html="showCertificateCheckLog.certificateData.manufacturingCountry"></span></p>
            <p>整车型号: <span v-html="showCertificateCheckLog.certificateData.vehicleType"></span></p>
            <p>车辆识别代号: <span v-html="showCertificateCheckLog.certificateData.vehicleIdentificationNumber"></span></p>
            <p>发动机型号: <span v-html="showCertificateCheckLog.certificateData.engineType"></span></p>
            <p>发动机排量 <span v-html="showCertificateCheckLog.certificateData.engineCapacity"></span></p>
            <p>发动机最大净功率 <span v-html="showCertificateCheckLog.certificateData.maximumNetEnginePower"></span></p>
            <p>制造年月 <span v-html="showCertificateCheckLog.certificateData.vintage"></span></p>
            <p>最大允许总质量 <span v-html="showCertificateCheckLog.certificateData.maximumAllowableTotalMass"></span></p>
            <p>乘坐人数: <span v-html="showCertificateCheckLog.certificateData.numberOfPassengers"></span></p>
            <p>生产厂名: <span v-html="showCertificateCheckLog.certificateData.nameOfManufacturer"></span></p>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <img width="450px" height="450px" :src="'data:image/png;base64,' + showCertificateCheckLog.frontierMarkingBase64" />
        </el-col>
        <el-col :span="12">
          <div style="text-align: center;font-size: 26px;font-weight:bolder;color:black">
            <p>拓印号识别数据: <span>{{ showCertificateCheckLog.frontierMarkingData }}</span></p>
          </div>
        </el-col>
      </el-row>


    </el-dialog>

  </el-container>
</template>
<script>

import http from '@/utils/http.js'
import dcitUtil from '@/utils/dictUtil.js'
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

      http.get("/home/log/certificateCheckLog", { params: query }).then(resp => {
        if (resp.code != 200) {
          this.$message.error(resp.message)
          this.$router.replace({ path: this.$route.path, query: { page: 1, pageSize: 10 } })
          return;
        }

        this.total = resp.data.total
        this.certificateCheckLogList = resp.data.certificateCheckLogList

        if (this.certificateCheckLogList.length == 0 && query.page > 1) {
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
      certificateCheckLogList: [],

      //查询参数
      queryCertificateCheckLog: {

      },

      //对话框显示
      dialog: false,

      //查看的数据
      showCertificateCheckLog: {
        certificateData: {
          //品牌
          brand: "",
          //制造国
          manufacturingCountry: "",
          //整车型号
          vehicleType: "",
          //车辆识别代号
          vehicleIdentificationNumber: "",
          //发动机型号
          engineType: "",
          //发动机排量
          engineCapacity: "",
          //发动机最大净功率
          maximumNetEnginePower: "",
          //制造年月
          vintage: "",
          //最大允许总质量
          maximumAllowableTotalMass: "",
          //乘坐人数
          numberOfPassengers: "",
          //生产厂名
          nameOfManufacturer: ""
        },
        nameplateBase64: "",
        frontierMarkingData: "",
        frontierMarkingBase64: ""
      },

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
      for (var key in this.queryCertificateCheckLog) {
        if (this.queryCertificateCheckLog[key] != null) {
          input = true;
          param[key] = this.queryCertificateCheckLog[key]
          this.queryCertificateCheckLog[key] = null
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


    //查看
    show(certificateCheckLog) {
      http.get("/home/log/certificateCheckLog/queryById", { params: { id: certificateCheckLog.id } }).then(resp => {
        if (resp.code == 200 && resp.data != null) {

          this.showCertificateCheckLog = {
            ...resp.data,
            certificateData: JSON.parse(resp.data.certificateData)
          }
          this.dialog = true
        } else {
          this.$message.error(resp.message)
        }
      })

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