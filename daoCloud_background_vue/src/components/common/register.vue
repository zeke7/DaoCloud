<template>
  <div class="login-wrapper">
    <div class="login-content">
      <div class="login-main">
        <h2 class="login-main-title">账号注册</h2>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" @keyup.enter.native="ruleFormSubmit()" status-icon>
          <el-form-item prop="userName">
            <el-input v-model="ruleForm.userName" placeholder="用户名"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="ruleForm.password" type="password" placeholder="密码"></el-input>
          </el-form-item>
          <el-form-item prop="checkPass">
            <el-input v-model="ruleForm.checkPass" type="password" placeholder="确认密码"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button class="login-btn-submit" type="primary" @click="backToLogin()">返回</el-button>
            <el-button class="login-btn-submit" type="primary" @click="submitForm('ruleForm')">注册</el-button>
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
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass')
        }
        callback()
      }
    }
    const validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.ruleForm.password) {
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
        password: '',
        checkPass: ''
      },
      rules: {
        userName: [
          { required: true, message: '请输入您的名称', trigger: 'blur' },
          { min: 2, max: 5, message: '长度在 2 到 5 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, validator: validatePass, trigger: 'blur' },
          { min: 6, message: '长度在要大于等于6个字符', trigger: 'blur' }
        ],
        checkPass: [
          { required: true, validator: validatePass2, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$http.login.register(this.ruleForm).then(res =>{
            console.log(res)
          })
          // this.$http({
          //   url: this.$http.adornUrl('register'),
          //   method: 'post',
          //   data: this.$http.adornData({
          //     'userName': this.ruleForm.userName,
          //     'password': this.ruleForm.password
          //   })
          // }).then(({ data }) => {
          //   if (data.success) {
          //     this.$message({
          //       type: 'success',
          //       message: '注册成功'
          //     })
          //   } else {
          //     this.$message({
          //       type: 'info',
          //       message: '用户名已经存在'
          //     })
          //   }
          // })
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
