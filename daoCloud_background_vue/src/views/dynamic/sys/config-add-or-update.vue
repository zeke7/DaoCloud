<template>
  <el-dialog
      :title="dataForm.sysId === '' ? '新增' : '修改'"
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
      <el-form-item label="参数名称" prop="sysName">
        <el-input
            v-model="dataForm.sysName"
            placeholder="参数名称"
        ></el-input>
      </el-form-item>
      <el-form-item label="关键字" prop="keyword">
        <el-input
            v-model="dataForm.keyword"
            placeholder="关键字"
        ></el-input>
      </el-form-item>
      <el-form-item label="参数值" prop="sysParameter">
        <el-input
            v-model="dataForm.sysParameter"
            placeholder="参数值"
        ></el-input>
      </el-form-item>
      <!--      <el-form-item label="状态">-->
      <!--        <el-select v-model="dataForm.status">-->
      <!--          <el-option label="启用" value="0"></el-option>-->
      <!--          <el-option label="禁用" value="1"></el-option>-->
      <!--        </el-select>-->
      <!--      </el-form-item>-->
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
    const validator = (rule, value, callback) => {
      let list = this.dataList
      let num = 0
      for (let i = 0; i < list.length; i++) {
        if (value === list[i].sysKey) {
          num++
        }
      }
      if (num === 0) {
        callback()
      } else if (num > 0) {
        callback(new Error('关键字重复'))
      }
    }
    return {
      dataList: [],
      visible: false,
      dataForm: {
        sysId: '',
        sysName: '',
        sysParameter: '',
        keyword: ''
      },
      dataRule: {
        sysName: [
          {required: true, message: '参数名不能为空', trigger: 'blur'}
        ],
        sysParameter: [
          {required: true, message: '参数值不能为空', trigger: 'blur'}
        ],
        keyword: [
          {required: true, message: '参数值不能为空', trigger: 'blur'},
          {validator: validator}
        ]
      }
    }
  },
  methods: {
    init(row = {}, dataList) {
      this.dataList = dataList
      this.dataForm.sysId = row.sysId === undefined ? '' : row.sysId
      this.visible = true
      this.$nextTick(() => {
        if (this.$refs['dataForm']) {
          this.$refs['dataForm'].clearValidate()
        }
        if (
            this.dataForm.sysId !== '' ||
            this.dataForm.sysId !== null
        ) {
          this.dataForm.sysName = row.sysName
          this.dataForm.sysParameter = row.sysParameter
          this.dataForm.keyword = row.sysKey
        } else {
          this.dataForm = {
            sysId: '',
            sysName: '',
            sysParameter: '',
            keyword: ''
          }
        }
      })
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          console.log("in")
          let token = this.$cookie.get('token')
          let method = this.dataForm.sysId === '' ? 'post' : 'put'
          let data = {}
          if (this.dataForm.sysId === '') {
            data = {
              sysName: this.dataForm.sysName,
              sysValue: this.dataForm.sysParameter,
              sysKey: this.dataForm.keyword
            }
          } else {
            data = {
              sysName: this.dataForm.sysName,
              sysValue: this.dataForm.sysParameter,
              sysId: this.dataForm.sysId,
              sysKey: this.dataForm.keyword
            }
          }
          console.log(data)
          this.$http.systemConfig.systemCurd(method, data, token).then(res => {
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
