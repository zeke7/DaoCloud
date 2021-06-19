<template>
  <div class="mod-role" v-title data-title="班课列表">
    <el-form
        :inline="true"
        :model="dataForm"
        @keyup.enter.native="getDataList()"
    >
      <el-form-item>
        <el-input
            v-model="dataForm.classCode"
            placeholder="课程编号"
            clearable @clear="getList()"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getSingleClass()">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table
        :data="dataList.slice((this.pageIndex - 1) * this.pageSize, (this.pageIndex - 1) * this.pageSize + this.pageSize)"
        border
        v-loading="dataListLoading"
        style="width: 100%;"
    >
      <el-table-column

          header-align="center"
          align="center"
          label="学校名称"
      >
        <template v-slot="scope">
          <span>{{ scope.row.userSchool }}</span>
        </template>
      </el-table-column>
      <el-table-column
          prop="userDepartment"
          header-align="center"
          align="center"
          label="院系名称"
      >
      </el-table-column>
      <el-table-column
          header-align="center"
          align="center"
          label="课程名称"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.className }}</span>
        </template>
      </el-table-column>
      <el-table-column
          header-align="center"
          align="center"
          label="课程编号"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.classCode }}</span>
        </template>
      </el-table-column>
      <el-table-column
          prop="userName"
          header-align="center"
          align="center"
          label="任课老师"
      >
      </el-table-column>
      <el-table-column
          header-align="center"
          align="center"
          label="学生数量"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.classNum || 0 }}</span>
        </template>
      </el-table-column>
      <el-table-column
          prop="classSemester"
          header-align="center"
          align="center"
          label="课程学期"
      >
      </el-table-column>
      <el-table-column align="center" label="班课学生信息">
        <template slot-scope="scope">
          <el-button
              type="text"
              size="small"
              @click="studentInfo(scope.row)"
          >班课学生信息
          </el-button>
        </template>
      </el-table-column>
      <el-table-column
          fixed="right"
          header-align="center"
          align="center"
          width="150"
          label="操作"
      >
        <template slot-scope="scope">
          <el-button
              type="text"
              size="small"
              @click="addOrUpdateHandle(scope.row)"
          >修改
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        @size-change="sizeChangeHandle"
        @current-change="currentChangeHandle"
        :current-page="pageIndex"
        :page-sizes="[5]"
        :page-size="pageSize"
        :total="this.dataList.length"
        layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update
        v-if="addOrUpdateVisible"
        ref="addOrUpdate"
        @refreshDataList="getDataList"
    ></add-or-update>
  </div>
</template>

<script>

import {isAuth} from '../../../utils'
import {mapState} from "vuex";
import AddOrUpdate from "./dtc-add-or-update";

export default {
  inject: ['reload'],
  data() {
    return {
      dataForm: {
        classCode: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 5,
      totalPage: 0,
      dataListLoading: true,
      addOrUpdateVisible: false,
      dialogTableVisible: false,
    }
  },
  components: {
    AddOrUpdate
  },
  computed: {
    ...mapState('user', ['userName']),
  },
  // activated() {
  //   this.getDataList();
  // },
  created() {
    this.getDataList()
  },
  methods: {
    getDataList() {
      this.dataListLoading = true
      let token = this.$cookie.get('token')
      this.$http.classConfig.detailTeacherClass(token, this.userName).then(res => {
        if (res) {
          console.log(res.data)
          let data = res.data.data
          let course = data.classes
          for (let j = 0; j < course.length; j++) {
            this.dataList[j] = {}
            this.dataList[j].userSchool = data.userSchool
            this.dataList[j].userDepartment = data.userDepartment
            this.dataList[j].userName = data.userName
            this.dataList[j].className = course[j].className
            this.dataList[j].classCode = course[j].classCode
            this.dataList[j].classNum = course[j].classMember
            this.dataList[j].classId = course[j].classId
            if (course[j].classSemester === null) {
              this.dataList[j].classSemester = '暂无'
            } else {
              this.dataList[j].classSemester = course[j].classSemester
            }
          }
          this.dataListLoading = false
        }
      })
    },
    getList() {
      this.reload()
    },
    getSingleClass() {
      let token = this.$cookie.get('token')
      this.$http.classConfig.getClassByCode(this.dataForm.classCode, token).then(res => {
        if (res) {
          console.log(res.data)
          let ans = []
          for (let i = 0; i < this.dataList.length; i++) {
            if (this.dataList[i].classCode === res.data.data.classCode) {
              console.log(this.dataList[i])
              ans[0] = {}
              ans[0] = this.dataList[i]
              this.dataList = []
            }
          }
          if (ans !== null) {
            this.dataList = ans
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
            })
          }
        }
      }).catch(() => {
        this.$message({
          message: "无此记录",
          type: 'info',
          duration: 1500
        })
        this.dataList = []
      })
    },
// 每页数
    sizeChangeHandle(val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getDataList()
    },
// 当前页
    currentChangeHandle(val) {
      this.pageIndex = val
      this.getDataList()
    },
    // 修改
    addOrUpdateHandle: function (row = {}, userName = '') {
      userName = this.userName
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(row, userName)
      })
    },
    //查看详情
    studentInfo(row = {}) {
      this.$router.push({
        name: 'studentInfo',
        params: {row}
      })
    },
    haveAuth(auth) {
      console.log(auth + ' ' + isAuth(auth))
      // return isAuth(auth)
      return true
    },
  }
}
</script>

<style>

</style>
