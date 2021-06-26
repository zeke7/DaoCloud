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
            placeholder="名称"
        ></el-input>
      </el-form-item>
      <el-form-item label="关键字" prop="dicKey">
        <el-input
            v-model="dataForm.dicKey"
            placeholder="关键字"
            :disabled="dicKeyDisabled"
        ></el-input>
      </el-form-item>
      <el-form-item label="数值" prop="num" v-if="showDetail" >
        <el-input
            v-model="dataForm.num"
            placeholder="数值"
        ></el-input>
      </el-form-item>
      <el-form-item label="value" prop="value" v-if="showDetail">
        <el-input
            v-model="dataForm.value"
            placeholder="value"
        ></el-input>
      </el-form-item>
      <el-form-item label="默认值" size="mini" prop="default" v-if="showDetail">
        <el-radio-group v-model="locked">
          <el-radio :label="1" @change="changeRadio($event)">否</el-radio>
          <el-radio :label="2" @change="changeRadio($event)">是</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item align="center" v-if="update">
        <el-button
            type="text"
            size="small"
            align="center"
            @click="show()"
        >{{ inner }}
        </el-button>
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
      console.log(rule)
      if (rule.required === false)
      {
        callback()
      }
      let list = this.list
      console.log(list)
      if (value === undefined) {
        callback(new Error('数值不能为空'))
      }
      let count = 0
      for (let i = 0; i < list.length; i++) {
        if (value + '' === list[i].num) {
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
      locked: -1,
      Num: 0,
      rNum: 0,
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
        dicName: [
          {required: true, message: '名称不能为空', trigger: 'blur'}
        ],
        dicKey: [
          {required: true, message: '关键字不能为空', trigger: 'blur'}
        ],
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
    init(row = {}, list) {
      this.rNum = row.dicDetailCode
      this.inner = '添加字典明细'
      this.list = list
      if (list.length !== 0) {
        this.dataForm.num = list.length
      } else {
        this.dataForm.num = 0
      }
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
          this.dicKeyDisabled = true
          this.update = false
          this.disable = true
          this.dataRule.num[0].required = false
          this.dataForm.dicName = row.dicName
          this.dataForm.dicKey = row.dicCode
          this.dataForm.num = row.num
          this.dataForm.dicCode = row.dicCode
          this.dataForm.value = row.dicDetailName
          if (this.dataForm.dicKey === '是') {
            this.locked = 2
          } else if(this.dataForm.dicKey === '否') {
            this.locked = 1
          }
        } else {
          this.dicKeyDisabled = false
          this.showDetail = false
          this.update = true
          this.disable = false
          this.dataForm = {
            dicId: null,
            dicName: '',
            dicKey: '',
            value: '',
            dicCode: '',
            num: 0
          }
        }
      })
    },
    // 表单提交
    dataFormSubmit() {
      if (this.showDetail === false) {
        this.$refs['dataForm'].validate(valid => {
          if (valid) {
            let token = this.$cookie.get('token')
            let method = this.dataForm.dicId === null ? 'post' : 'put'
            let data = {}
            if (method === 'post'){
              data = {
                dictionarycode: this.dataForm.dicKey,
                dicname: this.dataForm.dicName,
              }
            }else if (method === 'put') {
              data = {
                dictionarycode: this.dataForm.dicKey,
                dicname: this.dataForm.dicName,
              }
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
      }else {
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
            let a = Math.random()
            let n = 0;
            // for (let i = 0; i < this.list.length; i++) {
            //   for (let j = 0;j < this.list.detailList)
            // }
            console.log(this.dataForm.dicKey)
            let data = {}
            if (method === 'post'){
              data = {
                dictionarycode: this.dataForm.dicKey,
                dicname: this.dataForm.dicName,
                dicdiscp: '1',
                dicdetailname: this.dataForm.value,
                dicdetaildiscp: type,
                dicdetailcode: a + '',
                dicvalue: '1',
                dicdetailvalue: this.dataForm.num + ''
              }
            }else if (method === 'put') {
              data = {
                dictionarycode: this.dataForm.dicKey,
                dicname: this.dataForm.dicName,
                dicdiscp: '1',
                dicdetailname: this.dataForm.value,
                dicdetaildiscp: type,
                dicdetailcode: this.rNum + '',
                dicvalue: '1',
                dicdetailvalue: this.dataForm.num + ''
              }
            }

            this.$http.dicConfig.dicDelCurd(method, data, token).then(res => {
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

    },
    show() {
      console.log(this.dataForm.dicKey !== '')
      this.locked = 1
      if (this.inner === '添加字典明细') {
        this.inner = '取消'
        this.showDetail = true
      } else if (this.inner === '取消') {
        this.inner = '添加字典明细'
        this.showDetail = false;
      }
      if (this.dataForm.dicKey !== '') {
        let token = this.$cookie.get('token')
        this.$http.dicConfig.selectDic(token, this.dataForm.dicKey).then(res =>{
          this.dataForm.num = res.data.data.detailist.length
        }).catch((err) =>{
          console.log(err)
        })
      } else {
        this.dataForm.num = 0
      }
    },
    changeRadio(val) {
      console.log(val)
      this.locked = val
    }
  }
}
</script>
