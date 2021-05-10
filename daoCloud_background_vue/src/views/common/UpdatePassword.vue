<template>
  <el-dialog title="修改密码" :visible.sync="visible" :append-to-body="true">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="100px">
      <el-form-item label="账号">
        <span>{{ userName }}</span>
      </el-form-item>
      <el-form-item label="原密码" prop="password">
        <el-input type="password" v-model="dataForm.password" placeholder="原密码"></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="newPassword">
        <el-input type="password" v-model="dataForm.newPassword" placeholder="新密码"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input type="password" v-model="dataForm.confirmPassword" placeholder="确认密码"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
            <el-button @click="visible = false">取消</el-button>
            <el-button type="primary" :class="{disabled: !this.check}" :disabled="check" @click="dataFormSubmit()">确定</el-button>
        </span>
  </el-dialog>
</template>

<script>
import {mapState} from 'vuex'

export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('新密码不能为空'))
      }
      if (value.length < 6) {
        callback(new Error('密码长度不能小于6'))
      } else {
        if (this.dataForm.confirmPassword !== '') {
          this.$refs.ruleForm.validateField('checkPass')
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
    return {
      // userName: 'admin',
      visible: false,
      check: true,
      dataForm: {
        password: '',
        newPassword: '',
        confirmPassword: ''
      },
      dataRule: {
        password: [
          {required: true, message: '原密码不能为空', trigger: 'blur'},
          {min: 6, message: '长度在要大于等于6个字符', trigger: 'blur'}
        ],
        newPassword: [
          {required: true, validator: validatePass, trigger: 'blur'},
          {min: 6, message: '长度在要大于等于6个字符', trigger: 'blur'}
        ],
        confirmPassword: [
          {required: true, validator: validatePass2, trigger: 'blur'}
        ]
      }
    }
  },
  computed: {
    ...mapState('user', ['userName']),
  },
  methods: {
    // 初始化
    init() {
      this.visible = true
      // 初始化清空表单内容
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
      })
    },
    // 表单提交，回到登录界面
    dataFormSubmit() {
      let token = this.$cookie.get('token')
      console.log(token)
      this.$http.commonUser.modifyPassword(this.userName, this.dataForm.password, this.dataForm.newPassword).then(res => {
        if (res.status === 200) {
          this.visible = false;
          this.$nextTick(() => {
            this.$router.push({
              name: "Login"
            })
          })
        } else {
          this.$message({
            type: "info",
            message: '修改失败'
          })
        }
      })
    }
  }
}
</script>
