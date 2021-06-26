<template>
  <el-dialog
      :title="userId === '' ? '新增' : '修改'"
      :close-on-click-modal="false"
      :visible.sync="visible"
  >
    <el-form
        :model="dataForm"
        :rules="dataRule"
        ref="dataForm"
        @keyup.enter.native="dataFormSubmit()"
        label-width="120px"
    >
      <el-form-item label="手机号" prop="userPhone">
        <el-input
            v-model="dataForm.userPhone"
            placeholder="手机号"
            :disabled="disabled"
        ></el-input>
      </el-form-item>
      <el-form-item label="用户工号" prop="userSno">
        <el-input
            v-model="dataForm.userSno"
            placeholder="用户工号"
            :disabled="disabled"
        ></el-input>
      </el-form-item>
      <el-form-item label="用户姓名" prop="userName">
        <el-input
            v-model="dataForm.userName"
            placeholder="用户姓名"
        ></el-input>
      </el-form-item>
      <el-form-item label="所属学校" prop="userSchool">
        <el-input
            v-model="dataForm.userSchool"
            placeholder="所属学校"
        ></el-input>
      </el-form-item>
      <el-form-item label="所属学院" prop="userDepartment">
        <el-input
            v-model="dataForm.userDepartment"
            placeholder="所属学院"
        ></el-input>
      </el-form-item>

    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>

export default {
  inject: ['reload'],
  data() {
    const rightPhone = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('手机号不能为空'))
      } else if (!(/^1[3456789]\d{9}$/.test(value))) {
        callback(new Error('手机号格式不对'))
      } else {
        this.check = false
        callback()
      }
    }
    return {
      dataList: [],
      visible: false,
      dataForm: {
        userPhone: '',
        userSchool: '',
        userDepartment: '',
        userName: '',
        userSno: '',
      },
      userId: '',
      disabled: true,
      dataRule: {
        userPhone: [
          {required: true, validator: rightPhone, trigger: 'blur'}
        ],
        userName: [
          {required: true, message: '用户名不能为空', trigger: 'blur'}
        ],
        userSchool: [
          {required: true, message: '所属学校不能为空', trigger: 'blur'}
        ],
        userDepartment: [
          {required: true, message: '所属学院不能为空', trigger: 'blur'}
        ],
        userSno: [
          {required: true, message: '用户工号不能为空', trigger: 'blur'}
        ],
      }
    }
  },
  methods: {
    init(row = {}, dataList) {
      console.log(row)
      this.dataList = dataList
      this.userId = row.userId === undefined ? '' : row.userId
      this.visible = true
      this.$nextTick(() => {
        if (this.$refs['dataForm']) {
          this.$refs['dataForm'].clearValidate()
        }
        if (this.userId !== '') {
          this.disabled = true
          this.dataForm.userPhone = row.userPhone
          this.dataForm.userName = row.userName
          this.dataForm.userSchool = row.userSchool
          this.dataForm.userDepartment = row.userDepartment
          this.dataForm.userSno = row.userSno
        } else {
          this.disabled = false
          this.dataForm = {
            userPhone: '',
            userSchool: '',
            userDepartment: '',
            userName: '',
            userSno: '',
          }
        }
      })
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          let token = this.$cookie.get('token')
          let data = {
            'userphone': this.dataForm.userPhone,
            'username': this.dataForm.userName,
            'userschool': this.dataForm.userSchool,
            'userdepartment': this.dataForm.userDepartment,
            'usersno': this.dataForm.userSno
          }
          this.$http.userInfo.userUpdate(token, data).then(res => {
            if (res) {
              console.log(res)
              this.reload()
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500
              })
            } else {
              this.$message.error(res.data.msg)
            }
          })
        }
      })
    }
  }
}
</script>
