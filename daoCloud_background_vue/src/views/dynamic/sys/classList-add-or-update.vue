<template>
  <el-dialog
      :title="dataForm.classId === undefined ? '新增' : '修改'"
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
      <el-form-item label="课程名称" prop="className">
        <el-input
            v-model="dataForm.className"
            placeholder="课程名称"
        ></el-input>
      </el-form-item>
      <el-form-item label="创建者机号" prop="userPhone">
        <el-input
            v-model="dataForm.userPhone"
            placeholder="手机号"
        ></el-input>
      </el-form-item>
      <el-form-item label="课程编号" prop="classCode">
        <el-input
            v-model="dataForm.classCode"
            placeholder="课程编号"
        ></el-input>
      </el-form-item>
      <el-form-item label="学生数量" prop="num">
          <el-input v-model="dataForm.num" placeholder="学生数量"></el-input>
      </el-form-item>
      <el-form-item label="课程学期" prop="semester">
        <el-input v-model="dataForm.semester" placeholder="课程学期"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import user from "../../../store/module/user";

export default {
  inject:['reload'],
  data () {
    return {
      visible: false,
      dataForm: {
        className: '',
        classCode: '',
        num: '',
        classId: '',
        semester: '',
        userPhone: ''
      },
      dataRule: {
        className: [
          { required: true, message: '课程名称不能为空', trigger: 'blur' }
        ],
        classCode: [
          { required: true, message: '课程编号不能为空', trigger: 'blur' }
        ],
        num: [
          { required: true, message: '学生数量不能为空', trigger: 'blur' }
        ],
        semester: [
          { required: true, message: '课程学期不能为空', trigger: 'blur' }
        ],
        userPhone: [
          { required: true, message: '手机号不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    init (row = {}, userName) {
      this.dataForm.classId = row.classId === undefined ? '' : row.classId
      this.visible = true
      this.$nextTick(() => {
        if (this.$refs['dataForm']) {
          this.$refs['dataForm'].clearValidate()
        }
        if (this.dataForm.classId !== '') {
          this.dataForm.className = row.className
          this.dataForm.classCode = row.classCode
          this.dataForm.num = row.classNum
          this.dataForm.classId = row.classId
          this.dataForm.semester = row.classSemester
          this.dataForm.userPhone = userName
        } else {
          this.dataForm = {
            className: '',
            schoolName: '',
            departmentName: '',
            teacher: '',
            num: '',
            classId: '',
            userPhone: ''
          }
        }
      })
    },
    // 表单提交
    dataFormSubmit () {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          let token = this.$cookie.get('token')
          let method = this.dataForm.classId === '' ? 'post' : 'put'
          let data = {
            classname: this.dataForm.className,
            userphone: this.dataForm.userPhone,
            classmembers: this.dataForm.num,
            classsemester: this.dataForm.semester,
            classcode: this.dataForm.classCode
          }
          this.$http.classConfig.classCurd(method, data, token).then(res =>{
            if (res) {
              console.log(res)
              this.visible = false
              this.reload();
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }
      })
    }
  }
}
</script>
