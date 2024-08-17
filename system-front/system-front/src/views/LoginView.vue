<script>
import {defineComponent} from "vue";
import {myAxios} from "@/http/httpRequest.js";
import {messageTip, getTokenName, removeToken} from "@/util/utils.js";

export default defineComponent({
  //组件的名字
  name: "LoginView",

  //定义页面使用的变量，定义时初始值都是给个空的
  data() {
    return {
      user: {
        rememberMe: false,
      },
      //定义登录表单的验证规则
      loginRules: {
        //定义账号的验证规则，可以有多个
        loginAct: [
          { required: true, message: '请输入登录账号', trigger: 'blur' },
        ],
        //定义密码的验证规则，可以有多个
        loginPwd: [
          { required: true, message: '请输入登录密码', trigger: 'blur' },
          { min: 6, max:16, message: '登录密码长度为6~16位', trigger: 'blur' },
        ],
      }
    }
  },

  // 页面渲染dom元素后会调用该函数，vue组件生命周期
  mounted() {
    this.freeLogin()
  },

  // 页面上需要使用功能的js函数，都在methods属性中定义
  methods: {
    // 登录函数
    login(){
      // 提交前验证输入框的合法性
      this.$refs.loginRefForm.validate((isValid)=>{
        // isValid是验证后的结果，如果是true表示验证通过，否则未通过
        if(isValid){
          // 验证通过，可以提交登录
          let formData = new FormData();
          formData.append("loginAct", this.user.loginAct);
          formData.append("loginPwd", this.user.loginPwd);
          formData.append("rememberMe", this.user.rememberMe);
          // 登录请求
          myAxios.postForm("/api/login",formData).then((resp)=>{
            console.log(resp);
            if(resp.data.code === 200){
              // 登录成功，提示一下
              messageTip("登录成功",'success')

              // 删除一下历史sessionStorage和localStorage中的数据
              removeToken()

              // 前端存储jwt
              if(this.user.rememberMe === true){
                window.localStorage.setItem(getTokenName(),resp.data.data);
              }else {
                window.sessionStorage.setItem(getTokenName(),resp.data.data);
              }

              // 跳转到系统的主页面
              window.location.href="/dashboard";
            }else{
              // 登录失败，提示一下
              messageTip("登录失败","error")
            }
          })
        }
      })
    },

    // 自动登录
    freeLogin(){
      let token = window.localStorage.getItem(getTokenName())
      // 如果token存在，即上次操作记住了密码
      if(token){
        myAxios.get("/api/login/free").then(resp=>{
          if(resp.data.code === 200){
            // token验证通过了，可以免登录
            window.location.href = "/dashboard"
          }
        })
      }
    }
  }
})
</script>

<template>
  <el-container>
    <!--左侧-->
    <el-aside width="200px">
      <img src="../assets/loginBox.svg" alt="网络不稳定"/>
      <p class="imgText">欢迎使用啥都不是系统</p>
    </el-aside>

    <!--右侧-->
    <el-main>
      <div class="loginTitle">欢迎登录</div>
      <el-form ref="loginRefForm" :model="user" label-width="auto" style="max-width: 600px"
               :rules="loginRules">
        <el-form-item label="账号" prop="loginAct">
          <el-input v-model="user.loginAct"/>
        </el-form-item>
        <el-form-item label="密码" prop="loginPwd">
          <el-input type="password" v-model="user.loginPwd"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="login">登录</el-button>
        </el-form-item>
        <el-form-item>
          <el-checkbox label="记住我" v-model="user.rememberMe"/>
        </el-form-item>
      </el-form>
    </el-main>
  </el-container>
</template>

<style scoped>

.el-aside {
  background-color: #1a1a1a;
  width: 40%;
  text-align: center;
}
.el-main {
  height: calc(100vh);
}
img {
  height: 40%;
}
.imgText {
  color: #f9f9f9;
  font-size: 20px;
}
.el-form {
  width: 60%;
  margin: auto;
}
.loginTitle {
  text-align: center;
  margin-top: 15%;
  margin-bottom: 5%;
  font-weight: bolder;
  font-size: xx-large;
}
.el-button {
  width: 90%;
  margin-left: 10%;
}
.el-checkbox{
  margin-left: 10%;
}
</style>