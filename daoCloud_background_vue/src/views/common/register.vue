<template>
  <div class="login-wrapper" v-title data-title="注册">
    <div class="brand-info">
      <img src="~@/assets/logo.png" alt="">
      <h2 class="brand-info__text" style="display: inline-block">到 云</h2>
      <p class="brand-info__intro">到云——第16组</p>
    </div>
    <div class="login-content">
      <div class="login-main">
        <h2 class="login-main-title">账号注册</h2>
        <el-form :model="dataForm" :rules="rules" ref="dataForm" @keyup.enter.native="ruleFormSubmit()" status-icon>
          <el-form-item prop="userName">
            <el-input v-model="dataForm.userName" placeholder="用户名"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="dataForm.password" type="password" placeholder="密码"></el-input>
          </el-form-item>
          <el-form-item prop="userSchool">
            <el-input v-model="dataForm.userSchool"  placeholder="用户学校"></el-input>
          </el-form-item>
          <el-form-item prop="userDepartment">
            <el-input v-model="dataForm.userDepartment"  placeholder="用户院系"></el-input>
          </el-form-item>
          <el-form-item prop="userSno">
            <el-input v-model="dataForm.userSno"  placeholder="用户工号"></el-input>
          </el-form-item>
<!--          <el-form-item prop="userRole">-->
<!--            <el-input v-model="dataForm.userRole" placeholder="用户身份"></el-input>-->
<!--          </el-form-item>-->
          <el-form-item prop="userPhone">
            <el-input v-model="dataForm.userPhone" placeholder="手机号"></el-input>
          </el-form-item>
          <el-form-item prop="captcha">
            <el-input v-model="dataForm.captcha" placeholder="输入验证码"></el-input>
          </el-form-item>
          <el-form-item prop="userRole">
            <el-select v-model="dataForm.userRole" placeholder="请选择您的身份">
              <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button class="login-btn-submit" type="primary" @click="backToLogin()">返回</el-button>
            <el-button type="primary" class="login-btn-submit" :class="{disabled: !this.canClick}" @click="countDown" :disabled="!canClick">{{content}}</el-button>
            <el-button class="login-btn-submit" type="primary" @click="submitForm('dataForm')">注册</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
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
      options: [{
        value: 'admin',
        label: '管理员'
      }, {
        value: 'teacher',
        label: '教师'
      }],
      value: '',
      dataForm: {
        userPhone: '',
        userName: '',
        password: '',
        userSchool: '',
        userDepartment: '',
        userSno: '',
        userRole: '',
        captcha: ''
      },
      content: '发送验证码',
      canClick: true,
      totalTime: 60,
      rules: {
        userName: [
          { required: true, message: '请输入您的用户名', trigger: 'blur' },
          { min: 2, max: 5, message: '长度在 2 到 5 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, validator: validatePass, trigger: 'blur' },
          { min: 6, message: '长度在要大于等于6个字符', trigger: 'blur' }
        ],
        userPhone: [
          { required: true, validator: rightPhone, trigger: 'blur' }
        ],
        userSchool: [
          { required: true, message: '请输入您的学校', trigger: 'blur' }
        ],
        userRole: [
          { required: true, message: '请输入您的身份', trigger: 'blur' }
        ],
        userDepartment: [
          { required: true, message: '请输入您的院系', trigger: 'blur' }
        ],
        userSno: [
          { required: true, message: '请输入您的工号', trigger: 'blur' }
        ],
      }
    }
  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let userPhone = this.dataForm.userPhone
          let userName =  this.dataForm.userName + ''
          let password = this.dataForm.password
          let userSchool = this.dataForm.userSchool + ''
          let userDepartment = this.dataForm.userDepartment + ''
          let usersno = this.dataForm.userSno
          let userole = this.dataForm.userRole
          let codeFromUser = this.dataForm.captcha
          let mobileDevice = 'BACKEND'
          let type =  'S1'
          this.$http.commonUser.register(userPhone, userName, password, userSchool, userDepartment, usersno, userole, codeFromUser, mobileDevice, type).then(res =>{
            if (res.status === 200 && res.data.msg === '账户已经存在'){
              console.log(res)
              this.$message({
                type: 'warning',
                message: '账户已经存在',
                duration: 1000
              })
            } else if (res.status === 200 && res.data.msg === '验证码错误') {
              this.$message({
                type: 'warning',
                message: '验证码错误',
                duration: 1000
              })
            } else if (res.status === 200 && res.data.msg === '添加成功') {
                this.$message({
                  type: 'success',
                  message: '注册成功',
                  duration: 1000
                })
              this.$router.push({
                name: 'Login'
              })
              }
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    countDown () {
      this.getCaptcha()
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
    getCaptcha () {
      if (this.dataForm.userPhone === null || this.dataForm.userPhone === '') {
        this.$message.error('请输入您的手机号')
        return false
      } else {
        this.$http.commonUser.getCaptcha(this.dataForm.userPhone, "S1").then(res =>{
          if (res.status === 200 && res.data.msg === 'ok') {
            console.log(res)
          }else if (res.data.msg === '验证码已存在，还未过期') {
            console.log(res)
            this.$message.error('验证码获取频繁，请稍后在获取')
          }
        })
      }
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    },
    backToLogin () {
      this.$router.push('login')
    }
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
  height: 650px;
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
