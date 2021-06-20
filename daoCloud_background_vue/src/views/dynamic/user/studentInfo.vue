<template>
  <div class="app-container" v-title data-title="数据字典">
    <el-card class="filter-container" shadow="never" style="margin-top: 15px">
      <div style="margin-top: 15px">
        <el-form
            :inline="true"
            size="small"
            label-width="140px"
        >
          <el-form-item>
            <el-button
                style="float: right"
                type="primary"
                v-on:click="$router.back(-1)"
                size="small"
            >
              返回
            </el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-card>

    <div class="operate-container" shadow="never" style="margin-top: 15px">
      <el-table
          v-loading="listLoading"
          :data="list.slice((this.pageIndex - 1) * this.pageSize, (this.pageIndex - 1) * this.pageSize + this.pageSize)"
          border
          fit
          highlight-current-row
          style="width: 100%;margin-top: 15px"
      >

        <el-table-column align="center" label="姓名">
          <template slot-scope="scope">
            <span>{{ scope.row.userName }}</span>
          </template>
        </el-table-column>

        <el-table-column align="center" label="所属学院">
          <template slot-scope="scope">
            <span>{{ scope.row.userDepartment }}</span>
          </template>
        </el-table-column>

        <el-table-column align="center" label="联系方式">
          <template slot-scope="scope">
            <span>{{ scope.row.userPhone }}</span>
          </template>
        </el-table-column>

        <el-table-column align="center" label="学号">
          <template slot-scope="scope">
            <span>{{ scope.row.userSno }}</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-pagination
        @size-change="sizeChangeHandle"
        @current-change="currentChangeHandle"
        :current-page="pageIndex"
        :page-sizes="[5]"
        :page-size="pageSize"
        :total="this.list.length"
        layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
  </div>
</template>

<script>
export default {
  inject: ['reload'],
  name: "bannerAdvList",
  components: {},
  data() {
    return {
      dicCode: '',
      multipleSelection: [], // 当前选择
      list: [], // 当前list
      total: 0,
      listLoading: false,
      pageIndex: 1,
      pageSize: 5,
    };
  },
  computed: {
  },
  created() {
  },
  mounted() {
    this.initDataList();
  },
  methods: {
    initDataList() {
      let token = this.$cookie.get("token")
      this.$http.userInfo.getStudentInfo(token, this.$route.params.row.classCode).then(res =>{
        if (res) {
          this.list = res.data.data
          if (res.data.data.length === 0) {
            this.$message({
              message: '暂无学生',
              type: 'info',
              duration: 1500,
            })
          }
        }
      })
    },
    clear() {
      this.reload()
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
  }
};
</script>

<style scoped>
.edit-input {
  padding-right: 100px;
}

.cancel-btn {
  position: absolute;
  right: 15px;
  top: 10px;
}
</style>
