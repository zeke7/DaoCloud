<template>
  <div class="login-wrapper">
    <div class="login-content">
      <div class="login-main">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" @keyup.enter.native="ruleFormSubmit()" status-icon>
          <el-form-item prop="userName">
            <el-input v-model="ruleForm.userName" placeholder="输入待找回的用户名"></el-input>
          </el-form-item>
          <el-form-item prop="newPassword">
            <el-input v-model="ruleForm.newPassword" type="password" placeholder="输入新密码"></el-input>
          </el-form-item>
          <el-form-item prop="checkNewPass">
            <el-input v-model="ruleForm.checkNewPass" type="password" placeholder="确认新密码"></el-input>
          </el-form-item>
          <el-form-item prop="captcha">
            <el-input v-model="ruleForm.captcha" type="password" placeholder="输入验证码"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button class="login-btn-submit" type="primary" @click="backToLogin()">返回</el-button>
            <el-button type="primary" class="login-btn-submit" :class="{disabled: !this.canClick}" @click="countDown" :disabled="!canClick">{{content}}</el-button>
            <el-button type="primary" class="login-btn-submit" >完成</el-button>
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
        if (this.ruleForm.checkNewPass !== '') {
          this.$refs.ruleForm.validateField('checkPass')
        }
        callback()
      }
    }
    const validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.ruleForm.Newpassword) {
        console.log(value)
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      activeName: 'second',
      ruleForm: {
        userName: '',
        newPassword: '',
        checkNewPass: '',
        captcha: ''
      },
      rules: {
        userName: [
          { required: true, message: '请输入您的手机号', trigger: 'blur' },
          { min: 2, max: 5, message: '长度在大于等于2个字符', trigger: 'blur' }
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

    }
  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$http.login.forgetPassword(this.ruleForm).then(res=> {
            console.log(res)
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
      this.getPwd()
      if (!this.canClick) return
      if (this.ruleForm.userName == '' || this.ruleForm.userName.length < 2) return
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
    getPwd () {
      if (this.ruleForm.userName === null || this.ruleForm.userName === '') {
        this.$message.error('账号或工号不能为空')
        return false
      } else {
        return
        // this.$http({
        //   url: this.$http.adornUrl('/role/forgetpassword.do'),
        //   method: 'post',
        //   data: this.$http.adornData({
        //     'userName': this.isForgetPwdUserName
        //   })
        // }).then(({data}) => {
        //   if (data && data.status === 200) {
        //     this.$message.success('发送成功，请前往绑定邮箱查看')
        //     return true
        //   } else {
        //     this.$message.error(data.msg)
        //     return false
        //   }
        // })
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
