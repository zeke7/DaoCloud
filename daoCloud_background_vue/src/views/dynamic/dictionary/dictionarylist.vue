<template>
  <div class="app-container" v-title data-title="数据字典">
    <el-card class="filter-container" shadow="never" style="margin-top: 15px">
      <div style="margin-top: 15px">
        <el-form
            :inline="true"
            size="small"
            v-model="dataForm"
            label-width="140px"
        >
          <el-form-item label="输入搜索：">
            <el-input
                v-model="dataForm.dicCode"
                style="width: 203px"
                placeholder="关键字"
                clearable @clear="clear()"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button
                style="float: right"
                type="primary"
                v-on:click="getList"
                size="small"
            >
              查询结果
            </el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-card>

    <div class="operate-container" shadow="never" style="margin-top: 15px">
      <div slot="header" class="clearfix">
        <el-button size="mini" class="btn-add" @click="addOrUpdateHandle()"
        >新增
        </el-button
        >
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
          <template slot-scope="scope">
            <span>{{ scope.row.dicName }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="关键字">
          <template slot-scope="scope">
            <span>{{ scope.row.dicCode }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="字典详情">
          <template slot-scope="scope">
            <el-button
                type="text"
                size="small"
                @click="detailDic(scope.row)"
            >查看字典详情
            </el-button>
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
                @click="deleteHandle(scope.row.dicCode, scope.row.dicId)"
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
import AddOrUpdate from './dic-add-or-update'
import Pagination from "@/components/Pagination"; // Secondary package based on el-pagination
export default {
  inject: ['reload'],
  name: "bannerAdvList",
  components: {AddOrUpdate},
  data() {
    return {
      dataForm: {
        dicCode: ''
      },
      multipleSelection: [], // 当前选择
      list: [], // 当前list
      detailList: [],
      total: 0,
      listLoading: false,
      pageIndex: 1,
      pageSize: 5,
      addOrUpdateVisible: false, // 编辑界面是否显示
      detailVisible: false, // 编辑界面是否显示
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
      let token = this.$cookie.get('token')
      this.$http.dicConfig.getDictionary(token).then(res => {
        if (res) {
          console.log(res)
          let data = res.data.data
          this.list = data
        }
      })
    },
    // 当点击查询按钮的时候，将tables数组赋值给el-table的data中绑定的list，这样页面渲染的就是通过搜索筛选出来的数据了
    getList() {
      let token = this.$cookie.get("token")
      this.$http.dicConfig.selectDic(token, this.dataForm.dicCode).then(res => {
        if (res) {
          this.list = []
          let data = res.data.data
          console.log(data)
          this.list[0] = data
          let detailLen = data.detailist.length
          this.list[0].dicDetailType = ''
          if (detailLen === 0) {
            this.list[0].dicDetailType = '无数据'
          } else {
            this.list[0].dicDetailType = data.detailist[0].dicdetailDescription
          }
        }
      }).catch((err) =>{
        this.list = []
        this.$message({
          type: "warning",
          message: '没有此纪录'
        })
      })
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
        this.$refs.addOrUpdate.init(row, this.list)
      })
    },
    //查看详情
    detailDic(row = {}) {
      this.$router.push({
        name: 'detailDictionary',
        params: {row}
      })
    },
    // 删除
    deleteHandle(dicCode) {
      this.$confirm(`确定对${dicCode}进行删除操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let token = this.$cookie.get('token')
        this.$http.dicConfig.delDic(dicCode, token).then(res => {
          console.log(res)
          if (res && res.data.msg === '删除字典类别成功') {
            this.reload()
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
            })
          } else if (res && res.data.msg === '删除字典类别失败') {
            this.$message({
              message: '请先删除字典详情',
              type: 'error',
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
