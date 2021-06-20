<template>
  <div class="mod-config" v-title data-title="用户管理">
    <el-form
        :inline="true"
        :model="dataForm"
    >
      <el-form-item>
        <el-input
            v-model="dataForm.userPhone"
            placeholder="手机号"
            clearable @clear="getList()"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getSingleUser()">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table
        :data="dataList.slice((this.pageIndex - 1) * this.pageSize, (this.pageIndex - 1) * this.pageSize + this.pageSize)"
        border
        v-loading="dataListLoading"
        style="width: 100%;">
      <el-table-column
          header-align="center"
          align="center"
          label="用户名称">
        <template slot-scope="scope">
          <span>{{ scope.row.userName }}</span>
        </template>
      </el-table-column>
      <el-table-column
          header-align="center"
          align="center"
          label="用户身份">
        <template slot-scope="scope">
          <span>{{ scope.row.identity }}</span>
        </template>
      </el-table-column>
      <el-table-column
          header-align="center"
          align="center"
          label="所属学校">
        <template slot-scope="scope">
          <span>{{ scope.row.userSchool }}</span>
        </template>
      </el-table-column>
      <el-table-column
          header-align="center"
          align="center"
          label="所属学院">
        <template slot-scope="scope">
          <span>{{ scope.row.userDepartment }}</span>
        </template>
      </el-table-column>
      <el-table-column
          prop="keyword"
          header-align="center"
          align="center"
          label="用户工号">
        <template slot-scope="scope">
          <span>{{ scope.row.userSno }}</span>
        </template>
      </el-table-column>
      <el-table-column
          prop="eachSignTime"
          header-align="center"
          align="center"
          label="用户联系方式">
        <template slot-scope="scope">
          <span>{{ scope.row.userPhone }}</span>
        </template>
      </el-table-column>
      <el-table-column
          fixed="right"
          header-align="center"
          align="center"
          width="150"
          label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.userName, scope.row.userPhone)">删除</el-button>
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
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>

  </div>
</template>

<script>
import AddOrUpdate from './userInfo-add-or-update'

export default {
  inject: ['reload'],
  data() {
    return {
      dataForm: {
        userPhone: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 5,
      totalPage: 0,
      dataListLoading: true,
      dataListSelections: [],
      addOrUpdateVisible: false
    }
  },
  components: {
    AddOrUpdate
  },
  created() {
    this.getDataList()
  },
  methods: {
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true
      let token = this.$cookie.get('token')
      let roleId = this.$cookie.get('identity')
      this.$http.userInfo.getUserInfo('get', token, null).then(res => {
        if (res) {
          console.log(res)
          let data = res.data.data
          for (let i = 0; i < data.length; i++) {
            this.dataList[i] = data[i]
            if (data[i].roleId === 1) {
              this.dataList[i].identity = '管理员'
            } else if (data[i].roleId === 2) {
              this.dataList[i].identity = '教师'
            } else if (data[i].roleId === 3) {
              this.dataList[i].identity = '学生'
            }
          }
        }
        this.dataListLoading = false
      })
    },
    getSingleUser() {
      let token = this.$cookie.get('token')
      this.$http.userInfo.getSingleUser(token, this.dataForm.userPhone).then(res => {
        if (res) {
          console.log(res.data)
          let ans = []
          this.dataList = []
          this.dataList[0] = {}
          this.dataList[0] = res.data.data
          if (res.data.data.roleId === 1) {
            this.dataList[0].identity = '管理员'
          } else if (res.data.data.roleId === 2) {
            this.dataList[0].identity = '教师'
          } else if (res.data.data.roleId === 3) {
            this.dataList[0].identity = '学生'
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
    // 删除
    deleteHandle(userName, userPhone) {
      console.log(userPhone)
      this.$confirm(`确定将${userName}删除吗?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let token = this.$cookie.get('token')
        this.$http.userInfo.delUser(token, userPhone).then(res => {
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
    getList() {
      this.reload()
    },
    // 新增 / 修改
    addOrUpdateHandle(row) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(row, this.dataList)
      })
    },
  }
}
</script>
