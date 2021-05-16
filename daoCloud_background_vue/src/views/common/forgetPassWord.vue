<template>
  <div class="login-wrapper" v-title data-title="忘记密码">
    <div class="brand-info">
      <img src="~@/assets/logo.png" alt="">
      <h2 class="brand-info__text" style="display: inline-block">到 云</h2>
      <p class="brand-info__intro">到云——第16组</p>
    </div>
    <div class="login-content">
      <div class="login-main">
        <h2 class="login-main-title">找回密码</h2>
        <el-form :model="dataForm" :rules="rules" ref="dataForm" @keyup.enter.native="ruleFormSubmit()" status-icon>
          <el-form-item prop="userPhone">
            <el-input v-model="dataForm.userPhone" placeholder="输入待找回的手机号"></el-input>
          </el-form-item>
          <el-form-item prop="newPassword">
            <el-input v-model="dataForm.newPassword" type="password" placeholder="输入新密码"></el-input>
          </el-form-item>
          <el-form-item prop="checkNewPass">
            <el-input v-model="dataForm.checkNewPass" type="password" placeholder="确认新密码"></el-input>
          </el-form-item>
          <el-form-item prop="captcha">
            <el-input v-model="dataForm.captcha"  placeholder="输入验证码"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button class="login-btn-submit" type="primary" @click="backToLogin()">返回</el-button>
            <el-button type="primary" class="login-btn-submit" :class="{disabled: !this.canClick}" @click="countDown" :disabled="!canClick">{{content}}</el-button>
            <el-button type="primary" :class="{disabled: !this.check}" :disabled="check" class="login-btn-submit" @click="submitForm('dataForm')">完成</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (this.dataForm.checkNewPass !== '') {
          this.$refs.dataForm.validateField('checkPass')
        }
        callback()
      }
    }
    const validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.dataForm.newPassword) {
        console.log(value)
        this.check = true
        callback(new Error('两次输入密码不一致!'))
      } else {
        this.check = false
        callback()
      }
    }
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
      activeName: 'second',
      dataForm: {
        userPhone: '',
        newPassword: '',
        checkNewPass: '',
        captcha: ''
      },
      rules: {
        userPhone: [
          { required: true, validator: rightPhone, trigger: 'blur' }
        ],
        newPassword: [
          { required: true, validator: validatePass, trigger: 'blur' },
          { min: 6, message: '长度在要大于等于6个字符', trigger: 'blur' }
        ],
        checkNewPass: [
          { required: true, validator: validatePass2, trigger: 'blur' }
        ]
      },
      content: '发送验证码',
      canClick: true,
      isForgetPwdUserName: '',
      totalTime: 60,
      check: true
    }
  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$http.commonUser.forgetPassword(this.dataForm.userPhone, this.dataForm.newPassword, this.dataForm.captcha, "R2").then(res=> {
            console.log(res)
            if (res.status === 200 && res.data.msg === '密码修改成功') {
              console.log(res)
              this.$message({
                type: 'success',
                message: '修改成功',
                duration: 1000
              })
              this.$router.push({
                name: 'Login'
              })
            }else if(res.status === 200 && res.data.msg === '验证码不正确') {
              this.$message({
                type: 'warning',
                message: '验证码不正确',
                duration: 1000
              })
            }
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    },
    backToLogin () {
      this.$router.push('login')
    },
    countDown () {
      this.getCapthca()
      if (!this.canClick) return
      if (this.dataForm.userPhone == '' || this.dataForm.userPhone.length < 2) return
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
    getCapthca () {
      if (this.dataForm.userPhone === null || this.dataForm.userPhone === '') {
        this.$message.error('请输入您的手机号')
        return false
      } else {
        this.$http.commonUser.getCaptcha(this.dataForm.userPhone, 'R2').then(res =>{
          if (res.status === 200 && res.data.msg === 'ok') {
            console.log(res)
          }else if (res.status === 200 && res.data.msg === '验证码已存在，还未过期') {
            console.log(res)
            this.$message.error('验证码获取频繁，请稍后在获取')
          }
        })
      }
    },
  },
  name: "register"
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
  height: 430px;
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
