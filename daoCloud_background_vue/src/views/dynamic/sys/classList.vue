<template>
  <div class="mod-role" v-title data-title="班课列表">
    <el-form
        :inline="true"
        :model="dataForm"
        @keyup.enter.native="getDataList()"
    >
      <el-form-item>
        <el-form-item label="院系列表">
          <el-select v-model="nowSchool">
            <el-option
                :label="item.label"
                :value="item.value"
                v-for="item in schoolList"
                :key="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-input
          v-model="dataForm.roleName"
          placeholder="班课Id"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-input
            v-model="dataForm.roleName"
            placeholder="班课名称"
            clearable
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList(nowSchool)">查询</el-button>
        <el-button
            v-if="haveAuth('sys:user:save')"
            type="primary"
            @click="addOrUpdateHandle()"
        >新增</el-button
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
          prop="schoolName"
          header-align="center"
          align="center"
          label="学校名称"
      >
      </el-table-column>
      <el-table-column
          prop="uniacadaName"
          header-align="center"
          align="center"
          label="院系名称"
      >
      </el-table-column>
      <el-table-column
          prop="courseName"
          header-align="center"
          align="center"
          label="课程名称"
      >
      </el-table-column>
      <el-table-column
          prop="teacherName"
          header-align="center"
          align="center"
          label="任课老师"
      >
      </el-table-column>
      <el-table-column
          prop="studentNum"
          header-align="center"
          align="center"
          label="学生数量"
      >
        <template slot-scope="scope">
          <el-button size="mini" @click="alertModal(scope.row)">{{
              scope.row.studentNum || 0
            }}</el-button>
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
          >修改</el-button
          >
          <el-button
              type="text"
              size="small"
              @click="deleteHandle(scope.row.courseId)"
          >删除</el-button
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
import { isAuth } from '../../../utils'
import {mapState} from "vuex";
import AddOrUpdate from "@/views/dynamic/sys/classList-add-or-update";
export default {
  data () {
    return {
      dataForm: {
        roleName: ''
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
  mounted () {
    this.$http.login.getClassInfo(this.userName).then(res =>{
      if (res){
        console.log(res)
      }
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
    getSchool () {
      console.log(this.userName)
      let val = {
        username: this.userName
      }
      this.$http.login.getClassInfo(val).then(res => {
        if (res) {
          console.log(res)
        }
      })
    },
    getDataList (id) {
      console.log(id)
    },
    // 每页数
    sizeChangeHandle (val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getDataList()
    },
    // 当前页
    currentChangeHandle (val) {
      this.pageIndex = val
      this.getDataList()
    },
    // 多选
    selectionChangeHandle (val) {
      this.dataListSelections = val
    },
    // 新增 / 修改
    addOrUpdateHandle (row = {}) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(row)
      })
    },
    // 删除
    deleteHandle (courseId) {
      this.$confirm(`确定对[id=${courseId}]进行删除操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
          .then(() => {

          })
          .catch(() => {})
    },
    haveAuth (auth) {
      console.log(auth + ' ' + isAuth(auth))
      // return isAuth(auth)
      return true
    },
    /* 弹出学生列表 */
    alertModal (row) {
      console.log(row)
    }
  }
}
</script>

<style>

</style>
