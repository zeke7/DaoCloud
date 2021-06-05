<template>
  <div class="app-container" v-title data-title="数据字典">
    <el-card class="filter-container" shadow="never" style="margin-top: 15px">
      <div style="margin-top: 15px">
        <el-form
            :inline="true"
            :model="listQuery"
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
        <el-button size="mini" class="btn-add" @click="addOrUpdateHandle()"
        >新增
        </el-button>
      </div>
      <el-table
          @selection-change="handleSelectionChange"
          v-loading="listLoading"
          :data="list"
          border
          fit
          highlight-current-row
          style="width: 100%;margin-top: 15px"
      >
        <el-table-column type="selection" width="55"></el-table-column>

        <el-table-column align="center" label="dicCode">
          <template >
            <span>{{ dicCode }}</span>
          </template>
        </el-table-column>

        <el-table-column align="center" label="detailCode">
          <template slot-scope="scope">
            <span>{{ scope.row.dicdetailCode }}</span>
          </template>
        </el-table-column>

        <el-table-column align="center" label="数据类型">
          <template slot-scope="scope">
            <span>{{ scope.row.dicdetailDescription }}</span>
          </template>
        </el-table-column>

        <el-table-column align="center" label="value">
          <template slot-scope="scope">
            <span>{{ scope.row.dicdetailValue }}</span>
          </template>
        </el-table-column>

        <el-table-column align="center" label="名称">
          <template slot-scope="scope">
            <span>{{ scope.row.dicdetailName }}</span>
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
    </div>
    <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="listQuery.pageNo"
        :limit.sync="listQuery.pageSize"
    />
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
// var _index;
import AddOrUpdate from './dicDetail-add-or-update'
import Pagination from "@/components/Pagination"; // Secondary package based on el-pagination

export default {
  inject: ['reload'],
  name: "bannerAdvList",
  components: {Pagination, AddOrUpdate},
  data() {
    return {
      dicCode: '',
      multipleSelection: [], // 当前选择
      list: [], // 当前list
      detailList: [],
      total: 0,
      listLoading: false,
      listQuery: {
        // 查询分页
        name: "",
        pageNo: 1,
        pageSize: 10
      },
      listQueryname: "",
      addFormVisible: false, // 新增界面是否显示
      addLoading: false,
      addFormRules: {
        name: [{required: true, message: "请输入名称", trigger: "blur"}]
      },
      addOrUpdateVisible: false, // 编辑界面是否显示
      detailVisible: false, // 编辑界面是否显示
      editLoading: false,
      // 更新的数据
      update: [],
      typeList: []
    };
  },
  computed: {
    // 用computed监听tables，使用filter过滤数组中包含被搜索的关键词，将匹配到含有关键词的数据筛选出来
    // 返回给tables，此时tables就是一个新的数组
    // toLowerCase是转换小写的，因为有大小写的话some方法会失效
    // 模糊搜索
    tables() {
      const listQueryname = this.listQueryname;
      if (listQueryname) {
        return this.list.filter(data => {
          return Object.keys(data).some(key => {
            return (
                String(data[key])
                    .toLowerCase()
                    .indexOf(listQueryname) > -1
            );
          });
        });
      }
      return this.list;
    }
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
    // 全选
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleAdd: function () {
      // 当点击添加按钮时，addFormVisible为true,即显示弹框
      this.addFormVisible = true;
    },
    // 新增 / 修改
    addOrUpdateHandle: function (row = {}) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(row, this.dicCode)
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
