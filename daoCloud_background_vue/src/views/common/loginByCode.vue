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
            <el-input v-model="dataForm.userPhone" placeholder="请输入您的手机号"></el-input>
          </el-form-item>
          <el-form-item prop="chaptca">
            <el-input v-model="dataForm.chaptca" placeholder="请输入验证码"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button class="login-btn-submit" type="primary" @click="toLogin()">返回</el-button>
            <el-button type="primary" class="login-btn-submit" :class="{disabled: !this.canClick}" @click="countDown" :disabled="!canClick">{{content}}</el-button>
            <el-button class="login-btn-submit" type="primary" :class="{disabled: !this.check}" :disabled="check"  @click="dataFormSubmit('dataForm')">登录</el-button>
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
        chaptca: '',
      },
      check: true,
      dataRule: {
        userPhone: [
          {required: true, validator: rightPhone, trigger: 'blur'}
        ]
      },
      content: '发送验证码',
      canClick: true,
      totalTime: 60,
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
    toLogin() {
      this.$router.push('login')
    },
    countDown () {
      this.getCaptcha()
      if (!this.canClick) return
      if (this.dataForm.userPhone === '' || this.dataForm.userPhone.length < 2) return
      this.canClick = false
      this.content = this.totalTime + 's后重新发送'
      let clock = window.setInterval(() => {
        this.totalTime--
        this.content = this.totalTime + 's后重新发送'
        if (this.totalTime < 0) {
          window.clearInterval(clock)
          this.content = '重新发送验证码'
          this.totalTime = 60
          this.canClick = true  // 这里重新开启
        }
      }, 1000)
    },
    getCaptcha () {
      if (this.dataForm.userPhone === null || this.dataForm.userPhone === '') {
        this.$message.error('请输入您的手机号')
        return false
      } else {
        // this.$http.commonUser.getCaptcha1().then(res =>{
        //   if (res.status === 200 && res.data.msg === 'ok') {
        //     console.log(res)
        //   }else if (res.data.msg === '验证码已存在，还未过期') {
        //     console.log(res)
        //     this.$message.error('验证码获取频繁，请稍后在获取')
        //   }
        // })
        this.$http.commonUser.getCaptcha(this.dataForm.userPhone, "L0").then(res =>{
          console.log(res)
          if (res.status === 200 && res.data.msg === 'ok') {
            console.log(res)
          }else if (res.data.msg === '验证码已存在，还未过期') {
            console.log(res)
            this.$message.error('验证码获取频繁，请稍后在获取')
          }
        })
      }
    },
    // 提交表单
    dataFormSubmit() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http.commonUser.loginByCode(this.dataForm.userPhone, this.dataForm.chaptca, 'L0').then(response => {
            console.log(response)
            if (response.status === 200 && response.data.msg === '登录成功(Login Success.)') {
              this.$message({
                message: '登录成功',
                type: 'success'
              })
              // 保存 token
              this.$cookie.set('token', response.headers.authorization, 1)
              this.updateName(this.dataForm.userPhone)
              this.$router.push({
                name: 'Home'
              })
            }else {
              this.$message({
                type: "info",
                message: '登录失败'
              })
            }
          }).catch((err) =>{
            console.log(err)
            this.$message({
              type: "info",
              message: '用户名或密码错误，登录失败'
            })
          })
        }
      })
    },
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
  height: 350px;
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
