<template>
  <div class="login_div">

    <!-- 背景 -->
    <img :src="'/image/login/login_background.jpg'" style="width: 100%; height: 100%; z-index: -1" />

    <div class="login_context">

      <!-- 登录表单 -->
      <el-form class="login_box">

        <el-form-item style="text-align: center">
          <h2>合格证质检系统</h2>
        </el-form-item>

        <el-form-item prop="account">
          <el-input prefix-icon="el-icon-user" v-model="account" placeholder="账号"/>
        </el-form-item>

        <el-form-item prop="password">
          <el-input prefix-icon="el-icon-lock" type="password" placeholder="密码" v-model="password" @keyup.enter.native="login"/>
        </el-form-item>

        <el-form-item prop="message">
          <span style="color: red">{{ message }}</span>
        </el-form-item>

        <el-form-item class="btns">
          <el-button type="primary" class="Button" @click="login">登 录</el-button>
        </el-form-item>

      </el-form>
    </div>
  </div>
</template>
<script>
import http from '@/utils/http.js'
export default {
  data() {
    return {
      account: "",
      password: "",
      message: "",
    };
  },

  created() {
    let message = this.$route.query.message
    if (message != null) {
      this.$message.error(message)
      this.$router.replace({ path: this.$route.path })
    }
    
    

    let vue = this
    document.onkeyup = function (e) {
      //取出按键信息中的按键代码(大部分浏览器通过keyCode属性获取按键代码，但少部分浏览器使用的却是charCode)
      var code = e.charCode || e.keyCode;
      if (code == 13) {
        vue.login()
      }
    }
  },

  methods: {
    //登录按钮
    login() {
      this.message = "";
      if (this.account.length == 0) {
        this.message = "请输入用户名";
        return;
      }

      if (this.password.length == 0) {
        this.message = "请输入密码";
        return;
      }

      let params = new URLSearchParams();
      params.append("account", this.account);
      params.append("password", this.password);

      http.post("/login", params).then((resp) => {
        if (resp.code == 200) {
          localStorage.setItem("token", resp.data);
          this.$router.push("/")
        } else {
          this.message = resp.message;
        }
      });
    },
  },
};
</script>

<style scoped>

.login_div {
  height: 100%;
  background: rgb(43, 75, 107);
  position: relative;
  overflow: hidden
}

.login_context {
  width: 500px;
  height: 380px;
  background: #fff;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  border-radius: 10px;
  box-shadow: 0 0 1px 2px #000000;
}

.login_box {
  width: 100%;
  position: absolute;
  bottom: 20px;
  padding: 0 70px;
  box-sizing: border-box;
}

.Button {
  padding: 10px 160px;
  font-size: 17px;
  width: 100%;
}

.btns {
  display: flex;
  justify-content: center;
}

.checkbox {
  float: left;
}
</style>
