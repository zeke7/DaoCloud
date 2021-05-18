<template>
  <div class="mod-role" v-title data-title="班课列表">
    <el-form
        :inline="true"
        :model="dataForm"
        @keyup.enter.native="getDataList()"
    >
      <!--      <el-form-item>-->
      <!--        <el-input-->
      <!--          v-model="dataForm.roleName"-->
      <!--          placeholder="班课Id"-->
      <!--          clearable-->
      <!--        ></el-input>-->
      <!--      </el-form-item>-->
      <el-form-item>
        <el-input
            v-model="dataForm.classCode"
            placeholder="课程编号"
            clearable @clear="getList()"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button
            v-if="haveAuth('sys:user:save')"
            type="primary"
            @click="addOrUpdateHandle()"
        >新增
        </el-button
        >
      </el-form-item>
    </el-form>
    <el-table
        :data="dataList"
        border
        v-loading="dataListLoading"
        @selection-change="selectionChangeHandle"
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
          <!--          <el-button size="mini" @click="alertModal(scope.row)">{{scope.row.classes[0].classMember || 0 }}</el-button>-->
        </template>
      </el-table-column>
      <el-table-column
          prop="classSemester"
          header-align="center"
          align="center"
          label="课程学期"
      >
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
          </el-button
          >
          <el-button
              type="text"
              size="small"
              @click="deleteHandle(scope.row.classId, scope.row.classCode)"
          >删除
          </el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        @size-change="sizeChangeHandle"
        @current-change="currentChangeHandle"
        :current-page="pageIndex"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="pageSize"
        :total="totalPage"
        layout="total, sizes, prev, pager, next, jumper"
    >
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update
        v-if="addOrUpdateVisible"
        ref="addOrUpdate"
        :schoolList="schoolList"
        :nowSchool="nowSchool"
        @refreshDataList="getDataList"
    ></add-or-update>
    <el-dialog title="课程列表" :visible.sync="dialogTableVisible">
      <el-table :data="stuData">
        <el-table-column
            property="studentNo"
            label="学生编号"
        ></el-table-column>
        <el-table-column
            property="studentName"
            label="学生姓名"
        ></el-table-column>
        <el-table-column
            property="studentPhonenumber"
            label="手机号"
        ></el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import {delClass, getClassByCode} from "../../../http/modules/classConfig";

var _index;
import {isAuth} from '../../../utils'
import {mapState} from "vuex";
import AddOrUpdate from "./classList-add-or-update";

export default {
  inject:['reload'],
  data() {
    return {
      dataForm: {
        classCode: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false,
      schoolList: [],
      nowSchool: 0,
      dialogTableVisible: false,
      stuData: []
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
  mounted() {
    let token = this.$cookie.get('token')
    this.$http.classConfig.getClassInfo(this.userName, token).then(res => {
      if (res) {
        console.log(res.data)
        let temp = []
        let data = res.data.data
        let total_len = 0
        for (let i = 0; i < data.length; i++) {
          let course = data[i].classes
          if (course.length >= 1) {
            let last_len = 0;
            if (i === 0) {
              last_len = 0
            }else {
              last_len = data[i - 1].classes.length
            }
            let now_len = course.length
            total_len = total_len + now_len
            let t = 0;
            for (let j = last_len; j < total_len; j++) {
              temp[j] = {}
              temp[j].userSchool = data[i].userSchool
              temp[j].userDepartment = data[i].userDepartment
              temp[j].userName = data[i].userName
              temp[j].className = course[t].className
              temp[j].classCode = course[t].classCode
              temp[j].classNum = course[t].classMember
              temp[j].classId = course[t].classId
              if (course[t].classSemester === null) {
                temp[j].classSemester = '暂无'
              }else {
                temp[j].classSemester = course[t].classSemester
              }
              t++
            }
          }
        }
        this.dataList = temp
      }
    }).catch((err) => {
      console.log(err)
      this.$message({
        type: "info",
        message: '目前没有班课'
      })
    })
    // this.getSchool().then(() => {
    //   if (this.$route.query.id !== undefined) {
    //     this.nowSchool = this.$route.query.id
    //   } else {
    //     this.nowSchool = this.schoolList[0].value || 0
    //   }
    //
    //   this.getDataList(this.nowSchool)
    // })
  },
  methods: {
    // 获取数据列表
    getSchool() {
      console.log(this.userName)
      let val = {
        username: this.userName
      }
      this.$http.classConfig.getClassInfo(val).then(res => {
        if (res) {
          console.log(res)
        }
      })
    },
    getList() {
      this.reload()
    },
    getDataList() {
      let token = this.$cookie.get('token')
      this.$http.classConfig.getClassByCode(this.dataForm.classCode, token).then(res => {
        if (res) {
          console.log(res.data)
          let ans = []
          console.log(res.data.data.classCode)
          for (let i = 0; i < this.dataList.length; i++) {
            console.log(this.dataList[i].classCode)
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
    // 多选
    selectionChangeHandle(val) {
      this.dataListSelections = val
    },
    // 新增 / 修改
    addOrUpdateHandle: function (row = {}, userName = '') {
      userName = this.userName
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(row, userName)
      })
    },
    // 删除
    deleteHandle(id, classCode) {
      this.$confirm(`确定对[id=${id}]进行删除操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let token = this.$cookie.get('token')
        this.$http.classConfig.delClass(classCode, token).then(res => {
          if (res) {
            this.reload()
            this.$message({
              message: '删除成功',
              type: 'success',
              duration: 1500,
            })
          }
        })
      }).catch(() => {
          })
    },
    haveAuth(auth) {
      console.log(auth + ' ' + isAuth(auth))
      // return isAuth(auth)
      return true
    },
    /* 弹出学生列表 */
    alertModal(row) {
      console.log(row)
    },
  }
}
</script>

<style>

</style>
