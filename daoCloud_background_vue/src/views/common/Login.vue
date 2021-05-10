<template>
  <div class="login-wrapper" v-title data-title="登录">
    <div class="brand-info">
      <img src="~@/assets/logo.png" alt="">
      <h2 class="brand-info__text" style="display: inline-block">到 云</h2>
      <p class="brand-info__intro">到云——第16组</p>
    </div>
    <div class="login-content">
      <div class="login-main">
        <h2 class="login-main-title">管理员登录</h2>
        <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" status-icon>
          <el-form-item prop="userPhone">
            <el-input v-model="dataForm.userPhone" placeholder="手机号"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="dataForm.password" type="password" :placeholder="language.password"></el-input>
          </el-form-item>
          <el-form-item>
            <a @click="forgetPassword()" style="color: beige">忘记密码</a>
            <br>
            <a @click="loginChange()" style="color: beige">验证码登录</a>
          </el-form-item>
          <el-form-item>
            <el-button class="login-btn-submit" type="primary" :class="{disabled: !this.check}" :disabled="check" @click="dataFormSubmit()">登录</el-button>
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
    const rightPhone = (rule, value, callback) =>{
      if (value === '') {
        callback(new Error('手机号不能为空'))
      }else if (!(/^1[3456789]\d{9}$/.test(value))) {
        callback(new Error('手机号格式不对'))
      }else {
        this.check = false
        callback()
      }
    }
    return {
      dataForm: {
        userPhone: '',
        password: '',
      },
      check: true,
      dataRule: {
        userPhone: [
            {required: true, validator: rightPhone, trigger: 'blur'}
        ],
        password: [
            {required: true, message: '密码不能为空', trigger: 'blur'}
        ]
      },
    }
  },
  computed: {
    // 国际化
    language() {
      return {
        title: this.$t("login.title"),
        userPhone: this.$t("login.userName"),
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
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          let val = {
            username: this.dataForm.userPhone,
            password: this.dataForm.password
          }
          this.$http.commonUser.getToken(val).then(response => {
            if (response.status === 200 && response.data.msg === '登录成功(Login Success.)') {
              this.$message({
                message: '登录成功',
                type: 'success'
              })
              // 保存 token
              this.$cookie.set('token', response.headers.authorization,1)
              this.updateName(this.dataForm.userPhone)
              this.$router.push({
                name: 'Home'
              })
            }else {
              this.$message({
                type: "info",
                message: '用户名或密码错误'
              })
            }
          }).catch((err) =>{
            console.log(err)
            this.$message({
              type: "info",
              message: '账号不存在或出问题了'
            })
          })
        }
      })
    },
    toRegister () {
      this.$router.push('/register')
    },
    forgetPassword() {
      this.$router.push('/forgetPassWord')
    },
    loginChange() {
      this.$router.push('/loginByCode')
    }
  },
  created() {
    // 进入画面前，移除主页面保存的 state 信息
    localStorage.removeItem("store")
    this.resetState()
    // 登录页面，默认选择当前语言
    this.dataForm.language = this.$i18n.locale
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
  background-image: url(~@/assets/login_bg.jpg);
}

.login-content {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  margin: auto;
  height: 400px;
  width: 400px;
  background-color: #112234;
  opacity: .8;
}
.brand-info {
  margin: 50px 1000px 0 90px;
  color: #fff;
}
.brand-info__text {
  margin:  0 0 22px 0;
  font-size: 48px;
  font-weight: 400;
  text-transform : uppercase;
}
.brand-info__intro {
  margin: 10px 0;
  font-size: 16px;
  line-height: 1.58;
  opacity: .6;
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
