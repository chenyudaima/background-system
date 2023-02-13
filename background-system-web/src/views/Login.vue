<template>
  <section>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <div class="signin">
      <div class="content">
        <h2>Sign In</h2>
        <div class="form">
          <div class="input-box">
            <input type="text" v-model="account" required>
            <i>Username</i>
          </div>
          <div class="input-box">
            <input type="password" v-model="password" required>
            <i>Password</i>
          </div>
          <div class="links">
            <a href="#">Forgot Password</a>
            <a href="#">Singup</a>
          </div>
          <div class="input-box">
            <input type="submit" value="Login" @click="login">
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import http from '@/utils/http'
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
    if(message != null) {
      this.$message.error(message)
    }

    let my = this
    document.onkeyup = function (e) {
      //取出按键信息中的按键代码(大部分浏览器通过keyCode属性获取按键代码，但少部分浏览器使用的却是charCode)
      var code = e.charCode || e.keyCode;
      if (code == 13) {
        my.login()
      }
    }
  },

  methods: {
    login() {
      this.message = "";
      if (this.account.length == 0) {
        this.$message.error("请输入用户名");
        return;
      }

      if (this.password == 0) {
        this.$message.error("请输入密码");
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
          this.$message.error(resp.message);
        }
      });
    },
  },
}
</script>

<style scoped>

*{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
body{
  font-family: 'Quicksand' sans-serif;
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #000;
}
section {
  position: absolute;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 100vh;
  gap: 2px;
  flex-wrap: wrap;
  overflow: hidden;
}
section::before{
  content: '';
  position: absolute;
  width: 100%;
  height: 100%;
  background: linear-gradient(#000, #0f0, #000);
  animation: animate 5s linear infinite;
}
@keyframes animate {
  0%{
    transform: translateY(-100%);
  }
  100%{
    transform: translateY(100%);
  }
}
section span{
  position: relative;
  display: block;
  background-color: #181818;
  width: calc(6.25vw - 2px);
  height: calc(6.25vw - 2px);
  z-index: 2;
  transition: 1.5s;
}
section span:hover{
  background-color: #0f0;
  transition: 0s;
}
section .signin {
  position: absolute;
  width: 400px;
  background-color: #222;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 40px;
  border-radius: 4px;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.5);
  z-index: 1000;
}

section .signin .content {
  position: relative;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  gap: 40px;
}

section .signin .content h2{
  font-size: 2em;
  color: #0f0;
  text-transform: uppercase;
}

section .signin .content .form{
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 25px;
}
section .signin .content .form .input-box{
  position: relative;
  width: 100%;
}
section .signin .content .form .input-box input{
  position: relative;
  width: 100%;
  background-color: #333;
  border: none;
  outline: none;
  padding: 25px 10px 7.5px;
  border-radius: 4px;
  color: #fff;
  font-size: 1em;
  font-weight: 500;
}
section .signin .content .form .input-box i{
  position: absolute;
  left: 0;
  padding: 15px 10px;
  font-style: normal;
  color: #aaa;
  transition: 0.5s;
  pointer-events: none;
}
.signin .content .form .input-box input:focus ~ i,
.signin .content .form .input-box input:valid ~ i{
  transform: translateY(-7.5px);
  font-size: 0.5em;
  color: #fff;
}
.signin .content .form .links {
  position: relative;
  width: 100%;
  display: flex;
  justify-content: space-between;
}

.signin .content .form .links a{
  color: #fff;
  text-decoration: none;
}
.signin .content .form .links a:last-child{
  color: #0f0;
  font-weight: 600;
}

.signin .content .form .input-box input[type="submit"] {
  padding: 10px;
  background-color: #0f0;
  color: #111;
  font-weight: 600;
  font-size: 1.25em;
  letter-spacing: 0.05em;
  cursor: pointer;
}

@media (max-width: 900px) {
  section span{
    width: calc(10vw - 2px);
    height: calc(10vw - 2px);
  }
}
@media (max-width: 600px) {
  section span{
    width: calc(20vw - 2px);
    height: calc(20vw - 2px);
  }
}
</style>