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
      <div slot="header" class="clearfix">
        <el-button size="mini" class="btn-add" @click="addOrUpdateHandle('', dicName, dicCode)"
        >新增
        </el-button>
      </div>
      <el-table
          v-loading="listLoading"
          :data="list.slice((this.pageIndex - 1) * this.pageSize, (this.pageIndex - 1) * this.pageSize + this.pageSize)"
          border
          fit
          highlight-current-row
          style="width: 100%;margin-top: 15px"
      >

        <el-table-column align="center" label="名称">
          <template>
            <span>{{ dicName }}</span>
          </template>
        </el-table-column>

        <el-table-column align="center" label="关键字">
          <template >
            <span>{{ dicCode }}</span>
          </template>
        </el-table-column>

        <el-table-column align="center" label="value">
          <template slot-scope="scope">
            <span>{{ scope.row.dicdetailName }}</span>
          </template>
        </el-table-column>

        <el-table-column align="center" label="默认值">
          <template slot-scope="scope">
            <span>{{ scope.row.dicdetailDescription }}</span>
          </template>
        </el-table-column>

        <el-table-column align="center" label="数值">
          <template slot-scope="scope">
            <span>{{ scope.row.dicdetailValue }}</span>
          </template>
        </el-table-column>

        <el-table-column align="center" label="操作" width="180">
          <template slot-scope="scope">
            <el-button
                type="text"
                size="small"
                @click="addOrUpdateHandle(scope.row)"
            >修改
            </el-button>
            <el-button
                type="text"
                size="small"
                @click="deleteHandle(scope.row.dicdetailCode)"
            >删除
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
          :total="this.list.length"
          layout="total, sizes, prev, pager, next, jumper">
      </el-pagination>
    </div>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
// var _index;
import AddOrUpdate from './dicDetail-add-or-update'

export default {
  inject: ['reload'],
  name: "bannerAdvList",
  components: {AddOrUpdate},
  data() {
    return {
      dicCode: '',
      dicName: '',
      multipleSelection: [], // 当前选择
      list: [], // 当前list
      detailList: [],
      total: 0,
      listLoading: false,
      pageIndex: 1,
      pageSize: 5,
      addFormVisible: false, // 新增界面是否显示
      addLoading: false,
      addFormRules: {
        name: [{required: true, message: "请输入名称", trigger: "blur"}]
      },
      addOrUpdateVisible: false, // 编辑界面是否显示
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
      let data = this.$route.params.row.detailist
      this.dicCode = this.$route.params.row.dicCode
      this.dicName = this.$route.params.row.dicName
      if (data.length === 0) {
        this.list = []
      } else {
        for (let i = 0; i < data.length; i++) {
          this.list[i] = {}
          this.list[i] = data[i]
        }
      }
    },
    clear() {
      this.reload()
    },
    handleAdd: function () {
      // 当点击添加按钮时，addFormVisible为true,即显示弹框
      this.addFormVisible = true;
    },
    // 新增 / 修改
    addOrUpdateHandle: function (row = {}) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(row, this.list, this.dicName, this.dicCode)
      })
    },
    // 删除
    deleteHandle(detailCode) {
      this.$confirm(`确定对[detailCode=${detailCode}]进行删除操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let token = this.$cookie.get('token')
        this.$http.dicConfig.delDetailDic(detailCode, token).then(res => {
          if (res) {
            console.log(res)
            this.$router.back(-1)
            this.$message({
              message: '操作成功',
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
    getDataList(id) {
      console.log(id)
    },
    delall() {
      // 批量删：删除数组中0-数组长度的项，即全部
      this.list.splice(0, this.list.length);
    }
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
