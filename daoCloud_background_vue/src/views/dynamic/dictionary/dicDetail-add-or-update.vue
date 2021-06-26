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
            v-model="dicName"
            placeholder="名称"
            :disabled="dicKeyDisabled"
        >{{ dicName }}
        </el-input>
      </el-form-item>
      <el-form-item label="关键字" prop="dicKey">
        <el-input
            v-model="dicCode"
            placeholder="关键字"
            :disabled="dicKeyDisabled"
        >{{ dicCode }}
        </el-input>
      </el-form-item>
      <el-form-item label="value" prop="value">
        <el-input
            v-model="dataForm.value"
            placeholder="value"
        ></el-input>
      </el-form-item>
      <el-form-item label="数值" prop="num">
        <el-input
            v-model="dataForm.num"
            placeholder="数值"
        ></el-input>
      </el-form-item>
      <el-form-item label="默认值" size="mini" prop="default">
        <el-radio-group v-model="locked">
          <el-radio :label="1" @change="changeRadio($event)">否</el-radio>
          <el-radio :label="2" @change="changeRadio($event)">是</el-radio>
        </el-radio-group>
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
    const validator = (rule, value, callback) => {
      if (rule.required === false) {
        callback()
      }
      let list = this.list
      if (value === '') {
        callback(new Error('数值不能为空'))
      }
      let count = 0
      for (let i = 0; i < list.length; i++) {
        if (value + '' === list[i].dicdetailValue) {
          count++
        }
      }
      if (count === 0) {
        callback()
      } else if (count > 0) {
        callback(new Error('数值重复'))
      }
    }
    return {
      inner: '添加字典明细',
      oldN: false,
      locked: 1,
      Num: 0,
      rNum: 0,
      dicName: '',
      dicCode: '',
      dicDetailCode: '',
      disabled: false,
      showDetail: false,
      label: '0',
      update: false,
      visible: false,
      disable: true,
      dicKeyDisabled: true,
      dataForm: {
        dicId: null,
        dicName: '',
        dicKey: '',
        value: '',
        dicCode: '',
        num: '',
      },
      list: [],
      dataRule: {
        num: [
          {required: true, validator: validator, trigger: 'blur'},
        ],
        value: [
          {required: true, message: 'value不能为空', trigger: 'blur'},
        ]
      }
    }
  },
  methods: {
    init(row = {}, list, dicName, dicCode) {
      this.dicCode = dicCode
      this.dicName = dicName
      this.rNum = row.dicDetailCode
      this.inner = '添加字典明细'
      this.list = list
      this.dataForm.dicId = row.dictionarydetailId === undefined ? null : row.dictionarydetailId
      this.visible = true
      this.$nextTick(() => {
        if (this.$refs['dataForm']) {
          this.$refs['dataForm'].clearValidate()
        }
        if (
            this.dataForm.dicId !== undefined &&
            this.dataForm.dicId !== null
        ) {
          this.dicKeyDisabled = true
          this.update = false
          this.disable = true
          this.dataRule.num[0].required = false
          this.dataForm.dicName = row.dicName
          this.dataForm.dicKey = row.dicdetailDescription
          this.dataForm.num = row.dicdetailValue
          this.dataForm.dicCode = row.dicCode
          this.dataForm.value = row.dicdetailName
          this.dicDetailCode = row.dicdetailCode
          if (this.dataForm.dicKey === '是') {
            this.locked = 2
          } else if (this.dataForm.dicKey === '否') {
            this.locked = 1
          }
        } else {
          this.dicKeyDisabled = true
          this.showDetail = false
          this.update = true
          this.disable = false
          this.dataForm = {
            dicId: null,
            dicName: '',
            dicKey: '',
            value: '',
            dicCode: '',
            num: list.length
          }
        }
      })
    },
    dataFormSubmit() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          let token = this.$cookie.get('token')
          let method = this.dataForm.dicId === null ? 'post' : 'put'
          let type = ''
          if (this.locked === 2) {
            type = '是'
          } else if (this.locked === 1) {
            type = '否'
          }
          if (method === 'post') {
            let a = Math.random()
            this.dicDetailCode = a
          }
          let data = {
            dictionarycode: this.dicCode,
            dicdetailcode: this.dicDetailCode,
            dicdetailvalue: this.dataForm.num,
            dicdetailname: this.dataForm.value,
            dicdetaildiscp: type,

          }
          this.$http.dicConfig.dicDelCurd(method, data, token).then(res => {
            if (res.data.code === 200) {
              this.$router.back(-1)
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500
              })
            } else if (res.data.code === 500 && res.data.msg === '添加字典详情失败，已经存在。') {
              this.$message({
                message: '该记录已存在',
                type: 'warning',
                duration: 1500
              })
            }
          })
        }
      })
    },
    changeRadio(val) {
      console.log(val)
      this.locked = val
    }
  }
}
</script>
