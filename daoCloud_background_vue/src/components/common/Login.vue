<template>
  <div class="login-wrapper">
    <div class="login-content">
      <div class="login-main">
        <h2 class="login-main-title">管理员登录</h2>
        <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" status-icon>
          <el-form-item prop="userName">
            <el-input v-model="dataForm.userName" :placeholder="language.userName"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="dataForm.password" type="password" :placeholder="language.password"></el-input>
          </el-form-item>
          <el-form-item>
            <a @click="forgetPassword()" style="color: beige">忘记密码</a>
          </el-form-item>
          <el-form-item>
            <el-button class="login-btn-submit" type="primary" @click="dataFormSubmit()">登录</el-button>
            <el-button class="login-btn-submit" type="primary" @click="toRegister">注册</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>
<script>
import {mapActions} from 'vuex'
import {setToken} from '@/http/auth.js'
// import axios from "axios";
export default {
  data() {
    return {
      dataForm: {
        userName: '',
        password: '',
        language: 'zh'
      },
      dataRule: {}
    }
  },
  computed: {
    // 国际化
    language() {
      return {
        title: this.$t("login.title"),
        userName: this.$t("login.userName"),
        password: this.$t("login.password"),
        language: this.$t("login.language"),
        zh: this.$t("language.zh"),
        en: this.$t("language.en"),
        signIn: this.$t("login.signIn"),
        userNameNotNull: this.$t("login.userNameNotNull"),
        passwordNotNull: this.$t("login.passwordNotNull")
      }
    }
  },
  methods: {
    ...mapActions('user', ['updateName']),
    ...mapActions('common', {
      updateLang: "updateLanguage",
      resetState: "resetState"
    }),
    // 提交表单
    dataFormSubmit() {
      // TODO：登录代码逻辑待完善
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          let val = {
            account: this.dataForm.userName,
            passWord: this.dataForm.password
          }
          this.$http.login.getToken(val).then(response => {
            console.log(response)
            // this.$message({
            //   message: this.$t("login.signInSuccess"),
            //   type: 'success'
            // })
            // 保存 token
            setToken(response.data.token)
            this.updateName(this.dataForm.userName)
            this.$router.push({
              name: 'Home'
            })
          })
          // this.$http.login.getToken().then(response => {
          //     this.$http.menu.getMenus().then(response => {
          //       console.log(response)
          //     })
          //   this.$message({
          //     message: this.$t("login.signInSuccess"),
          //     type: 'success'
          //   })
          //   // 保存 token
          //   setToken(response.data.token)
          //   this.updateName(this.dataForm.userName)
          //   this.$router.push({
          //     name: 'Home'
          //   })
          // })
        }
      })
    },
    toRegister () {
      this.$router.push('/register')
    },
    forgetPassword() {
      this.$router.push('/forgetPassWord')
    }
  },
  created() {
    // 进入画面前，移除主页面保存的 state 信息
    localStorage.removeItem("store")
    this.resetState()
    // 登录页面，默认选择当前语言
    this.dataForm.language = this.$i18n.locale
    this.dataRule = {
      userName: [{
        required: true,
        message: '用户名不能为空',
        trigger: 'blur'
      }],
      password: [{
        required: true,
        message: '密码不能为空',
        trigger: 'blur'
      }]
    }
  }
}
</script>
<style>
.login-wrapper {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  overflow: hidden;
  background-color: aliceblue;
  background-size: 100% 100%;
}

.login-content {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  margin: auto;
  height: 350px;
  width: 400px;
  background-color: #112234;
  opacity: .8;
}

.login-main {
  color: beige;
  padding: 20px 20px 10px 20px;
}

.el-scrollbar__wrap {
  overflow-x: scroll !important;
}

.login-select {
  left: -120px;
  width: 120px;
}
</style>
