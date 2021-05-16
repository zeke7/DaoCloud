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
      <el-form-item label="名称" prop="dicName">
        <el-input
            v-model="dataForm.dicName"
            placeholder="每次签到经验"
        ></el-input>
      </el-form-item>
      <el-form-item label="value" prop="dicValue">
        <el-input
            v-model="dataForm.dicValue"
            placeholder="值"
        ></el-input>
      </el-form-item>
      <el-form-item label="描述" prop="dicDiscription">
        <el-input
            v-model="dataForm.dicDiscription"
            placeholder="描述"
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
  data () {
    return {
      visible: false,
      dataForm: {
        dicId: null,
        dicName: '',
        dicValue: '',
        dicDiscription: '',
        remark: '',
        status: '1'
      },
      dataRule: {
        dicName: [
          { required: true, message: '名称不能为空', trigger: 'blur' }
        ],
        dicValue: [
          { required: true, message: '值不能为空', trigger: 'blur' }
        ],
        dicDiscription: [
          { required: true, message: '描述不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    init (row = {}) {
      this.dataForm.sysId = row.dicId == undefined ? null : row.dicId
      this.visible = true
      this.$nextTick(() => {
        if (this.$refs['dataForm']) {
          this.$refs['dataForm'].clearValidate()
        }
        if (
            this.dataForm.dicId != undefined &&
            this.dataForm.dicId != null
        ) {
          this.dataForm.dicName = row.dicName
          this.dataForm.dicValue = row.dicValue
          this.dataForm.dicDiscription = row.dicDiscription
          this.dataForm.status = '1'
          this.dataForm.remark = row.remark
        } else {
          this.dataForm = {
            dicId: null,
            dicName: '',
            dicValue: '',
            dicDiscription: '',
            remark: '',
            status: '1'
          }
        }
      })
      console.log(row)
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
