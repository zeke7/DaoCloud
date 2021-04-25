<template>
  <el-dialog
      :title="dataForm.sysSettingsId == null ? '新增' : '修改'"
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
      <el-form-item label="id" prop="sysSettingsId" hidden>
        <el-input v-model="dataForm.sysSettingsId" placeholder="id"></el-input>
      </el-form-item>

      <el-form-item label="院系名称" prop="schoolName">
        <el-input
            v-model="dataForm.schoolName"
            placeholder="院系名称"
        ></el-input>
      </el-form-item>
      <el-form-item label="课程名称" prop="className">
        <el-input
            v-model="dataForm.className"
            placeholder="课程名称"
        ></el-input>
      </el-form-item>
      <el-form-item label="任课老师" prop="teacher">
        <el-input v-model="dataForm.teacher" placeholder="任课老师"></el-input>
      </el-form-item>
      <el-form-item label="学生数量" prop="num">
          <el-input v-model="dataForm.num" placeholder="学生数量"></el-input>
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
  data () {
    return {
      visible: false,
      dataForm: {
        sysSettingsId: null,
        schoolName: '',
        className: '',
        teacher: '',
        num: ''
      },
      dataRule: {
        schoolName: [
          { required: true, message: '院系名称不能为空', trigger: 'blur' }
        ],
        className: [
          { required: true, message: '课程名称不能为空', trigger: 'blur' }
        ],
        teacher: [
          { required: true, message: '课程名称不能为空', trigger: 'blur' }
        ],
        num: [
          { required: true, message: '课程名称不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    init (row = {}) {
      this.dataForm.sysSettingsId =
          row.sysSettingsId == undefined ? null : row.sysSettingsId
      this.visible = true
      this.$nextTick(() => {
        if (this.$refs['dataForm']) {
          this.$refs['dataForm'].clearValidate()
        }
        console.log(this.dataForm.sysSettingsId)

        if (
            this.dataForm.sysSettingsId != undefined &&
            this.dataForm.sysSettingsId != null
        ) {
          this.dataForm.eachSignExp = row.eachSignExp
          this.dataForm.eachSignTime = row.eachSignTime
          this.dataForm.status = '1'
          this.dataForm.remark = row.remark
        } else {
          this.dataForm = {
            sysSettingsId: null,
            eachSignExp: '',
            eachSignTime: '',
            remark: '',
            status: '1'
          }
        }
      })
    },
    // 表单提交
    dataFormSubmit () {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          let method =
              this.dataForm.sysSettingsId == undefined ? 'post' : 'put'
          this.$http({
            url: this.$http.adornUrl(`syssettings/settings`),
            method: method,
            data: this.$http.adornData(this.dataForm)
          }).then(({ data }) => {
            if (data && data.code === 200) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.visible = false
                  this.$emit('refreshDataList')
                }
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
