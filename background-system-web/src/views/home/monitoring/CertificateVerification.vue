<template>
  <div>
    <!-- 等待画面 -->
    <div class="wait" v-if="ng == -1">
      等待检测
    </div>

    <!-- 没有ng 需要车辆识别代号 -->
    <div v-if="ng == 0">

      <!-- 车辆识别代号显示 -->
      <el-row>
        <el-col :span="24" style="font-size: 75px; font-weight: bolder;text-align: center;">
          <div>
            {{ certificate.vehicleIdentificationNumber }}
          </div>
        </el-col>
      </el-row>

      <!-- 线 -->
      <ei-row>
        <el-col :span="24" style="background: #0f0f0f">
          <div style="height:5px"></div>
        </el-col>
      </ei-row>

      <el-row>
        <el-col :span="12" style="font-size: 100px; font-weight: bolder;text-align: center;line-height: 450px;">
          <div style="height: 450px">铭牌</div>
        </el-col>
        <el-col :span="12"
          style="background: #00ff00;font-size: 100px; font-weight: bolder;text-align: center;line-height: 450px;">
          <div style="height: 450px">OK</div>
        </el-col>
      </el-row>

      <!-- 线 -->
      <ei-row>
        <el-col :span="24" style="background: #0f0f0f">
          <div style="height:5px"></div>
        </el-col>
      </ei-row>

      <el-row>
        <el-col :span="12" style="font-size: 100px; font-weight: bolder;text-align: center;line-height: 450px;">
          <div style="height: 450px">拓印号</div>
        </el-col>
        <el-col :span="12"
          style="background: #00ff00;font-size: 100px; font-weight: bolder;text-align: center;line-height: 450px;">
          <div style="height: 450px">OK</div>
        </el-col>
      </el-row>
    </div>



    <!-- 只有一个ng -->
    <el-container v-if="ng == 1" style="height: 100%;">
      <el-aside width="50%">
        <div style="text-align: center;font-size: 33px;font-weight:bolder">
          <h1 style="text-align: center">铭牌识别</h1>
          <p>品牌: <span v-html="certificate.brand"></span></p>
          <p>制造国: <span v-html="certificate.manufacturingCountry"></span></p>
          <p>整车型号: <span v-html="certificate.vehicleType"></span></p>
          <p>车辆识别代号: <span v-html="certificate.vehicleIdentificationNumber"></span></p>
          <p>发动机型号: <span v-html="certificate.engineType"></span></p>
          <p>发动机排量 <span v-html="certificate.engineCapacity"></span></p>
          <p>发动机最大净功率 <span v-html="certificate.maximumNetEnginePower"></span></p>
          <p>制造年月 <span v-html="certificate.vintage"></span></p>
          <p>最大允许总质量 <span v-html="certificate.maximumAllowableTotalMass"></span></p>
          <p>乘坐人数: <span v-html="certificate.numberOfPassengers"></span></p>
          <p>生产厂名: <span v-html="certificate.nameOfManufacturer"></span></p>
          <!-- <p>制造厂专用号: <span v-html="certificate.manufacturerSpecialNumber"></span></p> -->
        </div>
      </el-aside>

      <el-main>
        <img :src="image" width="100%" height="100%" />
      </el-main>
    </el-container>

    <!-- 两个ng -->
  </div>
</template>

<script>
import http from "@/utils/http.js"
export default {
  data() {
    return {
      certificateCheckLog: {
        //合格证数据
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

        //铭牌识别数据
        nameplateData: {
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

        //铭牌图片
        nameplateBase64: "",

        //拓印号识别数据
        frontierMarkingData: "",

        //拓印号图片
        frontierMarkingBase64: ""
      },

      //质检配置
      certificateConfig: {
        //品牌
        brand: false,
        //制造国
        manufacturingCountry: false,
        //整车型号
        vehicleType: false,
        //车辆识别代号
        vehicleIdentificationNumber: true,
        //发动机型号
        engineType: false,
        //发动机排量
        engineCapacity: false,
        //发动机最大净功率
        maximumNetEnginePower: false,
        //制造年月
        vintage: true,
        //最大允许总质量
        maximumAllowableTotalMass: false,
        //乘坐人数
        numberOfPassengers: true,
        //生产厂名
        nameOfManufacturer: false,
      },

      //定时器id
      interval: null,

      //页面状态 0等待 1质检
      status: 0
    }
  },

  created() {
    this.interval = setInterval(() => {
      http.get("/home/monitoring/certificateCheckMonitoring").then(resp => {
        //为空进入等待
        if (resp.data == null) {
          this.status = 0
          return;
        }

        this.status = 1
        let certificateCheckLog = resp.data.certificateCheckLog

        //id相同不发生变化
        if (this.certificateCheckLog != null && this.certificateCheckLog.id == certificateCheckLog.id) {
          return;
        }

        //数据
        this.certificateCheckLog = {
          ...certificateCheckLog,
          certificateData: JSON.parse(certificateCheckLog.certificateData),
          nameplateData: JSON.parse(certificateCheckLog.nameplateData)
        }

        //配置
        this.certificateConfig = JSON.parse(resp.data.certificateConfig)
        this.pictureUpdate()
      })
    }, 1000);
  },

  methods: {
    /**
     * 画面更新
     */
    pictureUpdate() {

    }
  },

  /**
   * 销毁前清除定时器
   */
  beforeDestroy() {
    clearInterval(this.interval)
  }
}
</script>

<style scoped>
.wait {
  text-align: center;
  background-color: #fff;
  border-radius: 20px;
  width: 300px;
  height: 350px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  font-size: 70px;
  font-weight: bolder;

}
</style>