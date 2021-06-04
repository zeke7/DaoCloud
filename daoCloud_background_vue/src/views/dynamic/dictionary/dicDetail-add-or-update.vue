<template>
  <el-dialog
      :title="dataForm.dictionarydetailId === undefined ? '新增' : '修改'"
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
      <el-form-item label="dicCode" prop="dicCode">
        <el-input
            v-model="dataForm.dicCode"
            placeholder="dicCode"
            :disabled="dicDisable"
        ></el-input>
      </el-form-item>
      <el-form-item label="detailCode" prop="detailCode">
        <el-input
            v-model="dataForm.detailCode"
            placeholder="detailCode"
            :disabled="disable"
        ></el-input>
      </el-form-item>
      <el-form-item label="value" prop="dicDetailValue">
        <el-input
            v-model="dataForm.dicDetailValue"
            placeholder="value"
        ></el-input>
      </el-form-item>

      <el-form-item label="名称" prop="dicDetailName">
        <el-input
            v-model="dataForm.dicDetailName"
            placeholder="名称"
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
      dicDisable: true,
      dataForm: {
        dictionarydetailId: null,
        dicCode: '',
        detailCode: '',
        dicDetailType: '字符串',
        dicDetailValue: '',
        dicDetailName: '',
      },
      dataRule: {
        dicCode: [
          { required: true, message: '总数据字典编码不能为空', trigger: 'blur' }
        ],
        detailCode: [
          { required: true, message: '详细字典编码不能为空', trigger: 'blur' }
        ],
        dicDetailType: [
          { required: true, message: '数据类型不能为空', trigger: 'blur' }
        ],
        dicDetailName: [
          { required: true, message: '名称不能为空', trigger: 'blur' }
        ],
        dicDetailValue: [
          { required: true, message: 'value不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    init (row = {}, dicCode) {
      console.log(dicCode)
      this.dataForm.dictionarydetailId = row.dictionarydetailId === undefined ? null : row.dictionarydetailId
      this.visible = true
      this.$nextTick(() => {
        if (this.$refs['dataForm']) {
          this.$refs['dataForm'].clearValidate()
        }
        if (
            this.dataForm.dictionarydetailId !== undefined &&
            this.dataForm.dictionarydetailId !== null
        ) {
          this.disable = true
          this.dataForm.dicCode = dicCode
          this.dataForm.detailCode = row.dicdetailCode
          this.dataForm.dicDetailType = row.dicdetailDescription
          this.dataForm.dicDetailValue = row.dicdetailValue
          this.dataForm.dicDetailName = row.dicdetailName
        } else {
          this.disable = false
          this.dataForm = {
            dicCode: dicCode,
            detailCode: '',
            dicDetailType: '',
            dicDetailValue: '',
            dicDetailName: '',
          }
        }
      })
      console.log(this.dataForm.dicCode)
    },
    // 表单提交
    dataFormSubmit () {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          let token = this.$cookie.get('token')
          console.log(this.dataForm.dictionarydetailId)
          let method = this.dataForm.dictionarydetailId === undefined ? 'post' : 'put'
          let data = {
            dictionarycode: this.dataForm.dicCode,
            dicdetailcode: this.dataForm.detailCode,
            dicdetailname: this.dataForm.dicDetailName,
            dicdetailvalue: this.dataForm.dicDetailValue,
            dicdetaildiscp: this.dataForm.dicDetailType
          }
          let temp = this.dataForm
          this.$http.dicConfig.detailDicCurd(method, data, token).then(res => {
            if (res) {
              console.log(res)
              this.$router.back(-1)
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
