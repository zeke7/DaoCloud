<template>
  <el-dialog
      :title="dataForm.dicId === null ? '新增' : '修改'"
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
      <el-form-item label="编码" prop="dicCode">
        <el-input
            v-model="dataForm.dicCode"
            placeholder="编码"
            :disabled="disable"
        ></el-input>
      </el-form-item>
      <el-form-item label="名称" prop="dicName">
        <el-input
            v-model="dataForm.dicName"
            placeholder="名称"
        ></el-input>
      </el-form-item>
      <el-form-item label="描述" prop="dicDiscription">
        <el-input
            v-model="dataForm.dicDiscription"
            placeholder="描述"
        ></el-input>
      </el-form-item>
      <el-form-item label="数据类型" prop="dicDetailType">
        <el-input
            v-model="dataForm.dicDetailType"
            placeholder="数据类型"
            :disabled="disable"
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
  inject:['reload'],
  data () {
    return {
      visible: false,
      disable: true,
      dataForm: {
        dicId: null,
        dicCode: '',
        dicName: '',
        dicDetailType: '',
        dicDiscription: '',
      },
      dataRule: {
        dicCode: [
          { required: true, message: '数据类型不能为空', trigger: 'blur' }
        ],
        dicName: [
          { required: true, message: '名称不能为空', trigger: 'blur' }
        ],
        dicDetailType: [
          { required: true, message: '数据类型不能为空', trigger: 'blur' }
        ],
        dicDiscription: [
          { required: true, message: '描述不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    init (row = {}) {
      this.dataForm.dicId = row.dicId === undefined ? null : row.dicId
      this.visible = true
      this.$nextTick(() => {
        if (this.$refs['dataForm']) {
          this.$refs['dataForm'].clearValidate()
        }
        if (
            this.dataForm.dicId !== undefined &&
            this.dataForm.dicId !== null
        ) {
          this.dataRule.dicCode[0].required = false
          this.dataRule.dicDetailType[0].required = false
          this.disable = true
          this.dataForm.dicCode = row.dicCode
          this.dataForm.dicName = row.dicName
          this.dataForm.dicDetailType = row.dicDetailType
          this.dataForm.dicDiscription = row.dicDiscription
        } else {
          this.dataRule.dicCode[0].required = true
          this.dataRule.dicDetailType[0].required = true
          this.disable = false
          this.dataForm = {
            dicId: null,
            dicCode: '',
            dicName: '',
            dicDetailType: '',
            dicDiscription: '',
          }
        }
      })
    },
    // 表单提交
    dataFormSubmit () {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          let token = this.$cookie.get('token')
          let method = this.dataForm.dicId === null ? 'post' : 'put'
          let data = {
            dictionarycode: this.dataForm.dicCode,
            dicname: this.dataForm.dicName,
            dicdiscp: this.dataForm.dicDiscription,
            dicdetailvalue: this.dataForm.dicDetailType
          }
          this.$http.dicConfig.dicCurd(method, data, token).then(res => {
            if (res) {
              this.reload()
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500
              })
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    }
  }
}
</script>
