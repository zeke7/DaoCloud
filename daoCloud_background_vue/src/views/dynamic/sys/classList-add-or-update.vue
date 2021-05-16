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
      <el-form-item label="学校名称" prop="schoolName">
        <el-input
            v-model="dataForm.schoolName"
            placeholder="学校名称"
        ></el-input>
      </el-form-item>
      <el-form-item label="院系名称" prop="departmentName">
        <el-input
            v-model="dataForm.departmentName"
            placeholder="院系名称"
        ></el-input>
      </el-form-item>
      <el-form-item label="课程名称" prop="className">
        <el-input
            v-model="dataForm.className"
            placeholder="课程名称"
        ></el-input>
      </el-form-item>
      <el-form-item label="课程编号" prop="classCode">
        <el-input
            v-model="dataForm.classCode"
            placeholder="课程编号"
        ></el-input>
      </el-form-item>
      <el-form-item label="任课老师" prop="teacher">
        <el-input v-model="dataForm.teacher" placeholder="任课老师"></el-input>
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
        schoolName: '',
        departmentName: '',
        className: '',
        classCode: '',
        teacher: '',
        num: '',
        classId: '',
        semester: ''
      },
      userPhone: '',
      dataRule: {
        schoolName: [
          { required: true, message: '学校名称不能为空', trigger: 'blur' }
        ],
        departmentName: [
          { required: true, message: '院系名称不能为空', trigger: 'blur' }
        ],
        className: [
          { required: true, message: '课程名称不能为空', trigger: 'blur' }
        ],
        classCode: [
          { required: true, message: '课程编号不能为空', trigger: 'blur' }
        ],
        teacher: [
          { required: true, message: '老师名称不能为空', trigger: 'blur' }
        ],
        num: [
          { required: true, message: '学生数量不能为空', trigger: 'blur' }
        ],
        semester: [
          { required: true, message: '课程学期不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    init (row = {}, userName) {
      this.userPhone = userName
      this.dataForm.classId = row.classId === undefined ? '' : row.classId
      console.log(this.dataForm.classId)
      this.visible = true
      this.$nextTick(() => {
        if (this.$refs['dataForm']) {
          this.$refs['dataForm'].clearValidate()
        }
        if (
            this.dataForm.classId !== undefined &&
            this.dataForm.classId !== null
        ) {
          this.dataForm.schoolName = row.userSchool
          this.dataForm.className = row.className
          this.dataForm.departmentName = row.userDepartment
          this.dataForm.classCode = row.classCode
          this.dataForm.teacher = row.userName
          this.dataForm.num = row.classNum
          this.dataForm.classId = row.classId
          this.dataForm.semester = row.classSemester
        } else {
          this.dataForm = {
            className: '',
            schoolName: '',
            departmentName: '',
            teacher: '',
            num: '',
            classId: ''
          }
        }
      })
    },
    // 表单提交
    dataFormSubmit () {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          let token = this.$cookie.get('token')
          let method = this.dataForm.classId === undefined ? 'post' : 'put'
          let data = {
            classname: this.dataForm.className,
            userPhone: this.userPhone,
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
