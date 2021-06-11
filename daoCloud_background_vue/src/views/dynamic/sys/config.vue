<template>
  <div class="mod-config" v-title data-title="参数管理">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <!-- <el-form-item>
        <el-input v-model="dataForm.paramKey" placeholder="参数名" clearable></el-input>
      </el-form-item> -->
      <el-form-item>
        <!-- <el-button @click="getDataList()">查询</el-button> -->
        <el-button type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <!-- <el-button type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button> -->
      </el-form-item>
    </el-form>
    <el-table
        :data="dataList.slice((this.pageIndex - 1) * this.pageSize, (this.pageIndex - 1) * this.pageSize + this.pageSize)"
        border
        v-loading="dataListLoading"
        @selection-change="selectionChangeHandle"
        style="width: 100%;">
      <el-table-column
          header-align="center"
          align="center"
          width="80"
          label="ID">
        <template slot-scope="scope">
          <span>{{ scope.row.sysId }}</span>
        </template>
      </el-table-column>
      <el-table-column
          header-align="center"
          align="center"
          label="参数名称">
        <template slot-scope="scope">
          <span>{{ scope.row.sysName }}</span>
        </template>
      </el-table-column>
      <el-table-column
          prop="keyword"
          header-align="center"
          align="center"
          label="关键字">
        <template slot-scope="scope">
          <span>{{ scope.row.sysKey }}</span>
        </template>
      </el-table-column>
      <el-table-column
          prop="eachSignTime"
          header-align="center"
          align="center"
          label="参数值">
        <template slot-scope="scope">
          <span>{{ scope.row.sysParameter }}</span>
        </template>
      </el-table-column>
      <!--<el-table-column-->
      <!--    prop="status"-->
      <!--    header-align="center"-->
      <!--    align="center"-->
      <!--    label="状态（禁用 / 启用）">-->
      <!--  <template slot-scope="scope">-->
      <!--    <el-button size="mini" @click="enable(scope.row.sysId)">-->
      <!--      {{ scope.row.status == 0 ? "启用" : "禁用" }}-->
      <!--    </el-button>-->
      <!--  </template>-->
      <!--</el-table-column>-->
      <el-table-column
          fixed="right"
          header-align="center"
          align="center"
          width="150"
          label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.sysId, scope.row.sysKey)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        @size-change="sizeChangeHandle"
        @current-change="currentChangeHandle"
        :current-page="pageIndex"
        :page-sizes="[5, 10, 20, 50]"
        :page-size="pageSize"
        :total="this.dataList.length"
        layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>

  </div>
</template>

<script>
import AddOrUpdate from './config-add-or-update'

export default {
  inject:['reload'],
  data() {
    return {
      dataForm: {
        paramKey: ''
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
      this.$http.systemConfig.getParamters(token).then(res => {
        if (res) {
          console.log(res)
          for (let i = 0; i < res.data.data.length; i++) {
            this.dataList[i] = res.data.data[i]
          }
        }
        // if (res) {
        //   this.dataList = res.rows
        //   this.totalPage = rese.total
        // } else {
        //   this.dataList = []
        //   this.totalPage = 0
        // }
        this.dataListLoading = false
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
    addOrUpdateHandle(row) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(row, this.dataList)
      })
    },
    // 删除
    deleteHandle(id, key) {
      let token = this.$cookie.get('token')
      this.$confirm(`确定删除${key}?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http.systemConfig.delConfig(id, token).then(res => {
          if (res) {
            console.log(res)
            this.reload()
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
            })
          }
          // else {
          //   this.$message.error()
          // }
        })
      }).catch(() => {
      })
    },
  }
}
</script>
