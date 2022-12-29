<template>
  <div id="app">
    <div
      style="height: 100%; width: 100%"
      id="heatmap"
      @mousemove="getTheCoordinates($event)"
    >
      <div id="temperaturePop"></div>
    </div>
  </div>
</template>
<script>
import Heatmap from "heatmap.js";
export default {
  data() {
    return {
      /**
       * 热力图对象
       * 更新数据(也用于初始化):heatmapInstance.setData({x: 10, y: 10, value: 45})
       * 添加数据:heatmapInstance.addData({x: 10, y: 10, value: 45})
       * 更新最大值:heatmapInstance.setDataMax(number)
       * 更新最小值:heatmapInstance.setDataMin(number)
       * 更新配置:heatmapInstance.configure(configObject)
       * 获取数据:heatmapInstance.getData()
       * 查询某个点的value:heatmapInstance.getValueAt({x: 10, y: 10})
       * 获取热力图base64编码:heatmapInstance.getDataURL()
       * 重新绘制整个热图画布:heatmapInstance.repaint()
       */
      heatmapInstance: null,

      //热力图最大值
      max: 80,

      //热力图最小值
      min: 0,

      //热力图渐变颜色
      gradient: {
        0.25: "rgb(0,0,255)", //根据热力图最大值计算 70 x 0.25 = 17.5°
        0.55: "rgb(0,255,0)",
        0.85: "yellow",
        // 0.90: "rgb(219,112,147)",
        1.0: "rgb(255,0,0)",
      },

      //数据 (x和y是页面坐标，value是温度值)
      points: [],
    };
  },
  mounted() {
    //热力图配置
    let config = {
      //热力图容器
      container: document.getElementById("heatmap"),

      //热力图渐变颜色
      gradient: this.gradient,

      // defaultMaxOpacity: 1,//1 透明度最大值
      // defaultMinOpacity: 0.3,//0 透明度最小值
      defaultBlur: 1, //0.85 颜色渐变因子值
    };

    //根据配置创建热力图
    this.heatmapInstance = Heatmap.create(config);
  },

  created() {
    setInterval(() => {
      this.update();
    }, 1000);
  },

  methods: {
    /**
     * 获取鼠标当前位置信息
     */
    getTheCoordinates(e) {
      let temperature = this.heatmapInstance.getValueAt({ x: e.x, y: e.y });
      let temperaturePop = document.getElementById("temperaturePop");
      // if (temperature > 0) {
      temperaturePop.innerText = `温度: ${temperature}`;
      temperaturePop.innerText += `\nx:${e.x}\ny:${e.y}`;
      temperaturePop.style.display = "block";
      temperaturePop.style.left = `${e.x + 20}px`;
      temperaturePop.style.top = `${e.y - 5}px`;
      // } else {
      //   $("#temperaturePop").css({ display: "none" });
      // }
    },

    //初始化的时候进行，生成虚拟节点
    generateVirtualNode(node) {
      let virtualNode = [];

      node.forEach((node, index) => {
        //向有节点方向外扩散虚拟节点

        //求两个坐标的中点
        // (x = (x1 + x2) / 2), (y = (y1 + y2) / 2);

        //向外扩散10次
        let i = 2;
        while (i--) {
          let value = node.value - 6 * i;
          //左
          virtualNode.push({
            x: node.x - 100 * i,
            y: node.y,
            value: value,
            radius: 200,
          });

          //右
          virtualNode.push({
            x: node.x + 70 * i,
            y: node.y,
            value: value,
            radius: 200,
          });

          //上
          virtualNode.push({
            x: node.x,
            y: node.y - 70 * i,
            value: value,
            radius: 200,
          });

          //下
          virtualNode.push({
            x: node.x,
            y: node.y + 70 * i,
            value: value,
            radius: 200,
          });

          // virtualNode.push({ x: node.x + 20 * i, y: node.y + 20 * i, value: value, radius: 100 });
          // virtualNode.push({ x: node.x - 20 * i, y: node.y - 20 * i, value: value, radius: 100 });
        }
      });

      return virtualNode;
    },

    /**
       * // let points = [
      //   { x: 510, y: 100, value: Math.random()*(78 - 75) + 75, radius: 410 },
      //   { x: 600, y: 200, value: Math.random()*(66 - 54) + 54, radius: 410 },
      //   { x: 1300, y: 270, value: Math.random()*(60 - 55) + 55, radius: 410 },

      //   { x: 394, y: 680, value: Math.random()*(65 - 60) + 60, radius: 410 },
      //   { x: 900, y: 450, value: Math.random()*(75 - 70) + 70, radius: 410 },
      //   { x: 1100, y: 450, value: Math.random()*(65 - 55) + 55, radius: 410 },

      //   { x: 600, y: 702, value: Math.random()*(75 - 70) + 70, radius: 410 },
      //   { x: 900, y: 702, value: Math.random()*(65 - 55) + 55, radius: 410 },
      //   { x: 1300, y: 702, value: Math.random()*(75 - 72) + 72, radius: 410 },
      // ];
       */

    //虚拟更新热力图（用于测试）
    update() {
      //真实节点
      let node = [
        { x: 510, y: 100, value: Math.random() * (78 - 75) + 75, radius: 100 },
        { x: 436, y: 289, value: Math.random() * (66 - 54) + 54, radius: 100 },
        { x: 1300, y: 270, value: Math.random() * (60 - 55) + 55, radius: 100 },

        { x: 394, y: 680, value: Math.random() * (65 - 60) + 60, radius: 100 },
        { x: 900, y: 450, value: Math.random() * (75 - 70) + 70, radius: 100 },
        { x: 1100, y: 450, value: Math.random() * (65 - 55) + 55, radius: 100 },

        { x: 600, y: 702, value: Math.random() * (75 - 70) + 70, radius: 100 },
        { x: 900, y: 702, value: Math.random() * (65 - 55) + 55, radius: 100 },
        { x: 1300, y: 702, value: Math.random() * (75 - 72) + 72, radius: 100 },
      ];

      //虚拟节点
      let virtualNode = this.generateVirtualNode(node);

      this.heatmapInstance.setData({
        max: this.max,
        min: this.min,

        //合并真实节点和虚拟节点
        data: [...node, ...virtualNode],
      });
    },
  },
};
</script>
<style>
#temperaturePop {
  width: 90px;
  height: 30px;
  text-align: center;
  font-size: 20px;
  background: darken(#000, 100%);
  position: absolute;
  display: none;
  z-index: 9999;
}
</style>
