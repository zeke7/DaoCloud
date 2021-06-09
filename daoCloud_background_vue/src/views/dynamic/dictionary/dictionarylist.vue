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
          <el-form-item label="输入搜索：">
            <el-input
                style="width: 203px"
                v-model="listQueryname"
                placeholder="编号"
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
          @selection-change="handleSelectionChange"
          v-loading="listLoading"
          :data="list"
          border
          fit
          highlight-current-row
          style="width: 100%;margin-top: 15px"
      >
        <el-table-column type="selection" width="55"></el-table-column>



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
        <el-table-column align="center" label="value">
          <template slot-scope="scope">
            <span>{{ scope.row.dicDetailName }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="数值">
          <template slot-scope="scope">
            <span>{{ scope.row.num }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="默认值">
          <template slot-scope="scope">
            <span>{{ scope.row.dicKey }}</span>
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
    </div>
    <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="listQuery.pageNo"
        :limit.sync="listQuery.pageSize"
        @pagination="getList"
    />
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
// var _index;
import AddOrUpdate from './dic-add-or-update'
import Pagination from "@/components/Pagination"; // Secondary package based on el-pagination
const statusMap = {
  "0": "禁用",
  "1": "启用"
};
export default {
  inject: ['reload'],
  name: "bannerAdvList",
  components: {Pagination, AddOrUpdate},
  filters: {
    statusFilter(status) {
      return statusMap[status];
    }
  },
  data() {
    return {
      advStatusMap: [
        {
          value: "0",
          label: "禁用"
        },
        {
          value: "1",
          label: "启用"
        }
      ],
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
      addOrUpdateVisible: false, // 编辑界面是否显示
      detailVisible: false, // 编辑界面是否显示
      editLoading: false,
    };
  },
  computed: {
  },
  created() {
    this.getType();
  },
  mounted() {
    this.initDataList();
  },
  methods: {
    getType() {
    },
    initDataList() {
      let token = this.$cookie.get('token')
      this.$http.dicConfig.getDictionary(token).then(res => {
        if (res) {
        //   let data = res.data.data
        //   console.log(data)
        //   this.list = data
        //   console.log(this.list)
        //   for (let i = 0; i < data.length; i++) {
        //     this.list[i].dicDetailType = ''
        //     if (data[i].detailist.length === 0) {
        //       this.list[i].dicDetailType = '字符串'
        //     } else {
        //       this.list[i].dicDetailType = data[i].detailist[0].dicdetailDescription
        //     }
        //   }
        // console.log(this.list)
          let temp = []
          let data = res.data.data
          console.log(data)
          let total_len = 0
          let w = 0
          for (let i = 0; i < data.length; i++) {
            let detailList = data[i].detailist
            if (detailList.length >= 1) {
              let last_len = 0;
              if (i === 0) {
                last_len = 0
              }else {
                last_len = data[i - 1].detailist.length
              }
              let now_len = detailList.length
              total_len = total_len + now_len
              let t = 0;
              for (let j = w; j < total_len; j++) {
                temp[j] = {}
                temp[j].dicCode = data[i].dicCode
                temp[j].dicId = data[i].dicId
                temp[j].dicName = data[i].dicName
                temp[j].num = detailList[t].dicdetailValue
                temp[j].dicDetailName = detailList[t].dicdetailName
                temp[j].dicKey = detailList[t].dicdetailDescription
                temp[j].dicDetailCode = detailList[t].dicdetailCode
                t++
                w++
              }
            }
          }
          this.list = temp
          console.log(this.list)
        }
      })
      // let url =
      //   this.$route.query.type != undefined
      //     ? `sysdict/data/dictType/${this.$route.query.type}`
      //     : "sysdict/data/list";
      // this.$http({
      //   url: this.$http.adornUrl(url),
      //   method: "get",
      //   params: this.$http.adornParams()
      // }).then(({ data }) => {
      //   if (data && data.code === 200) {
      //     this.list = data.rows;
      //     this.totalPage = data.total;
      //   } else {
      //     this.dataList = [];
      //     this.totalPage = 0;
      //   }
      //   this.dataListLoading = false;
      // });
    },
    // 当点击查询按钮的时候，将tables数组赋值给el-table的data中绑定的list，这样页面渲染的就是通过搜索筛选出来的数据了
    getList() {
      let token = this.$cookie.get("token")
      this.$http.dicConfig.selectDic(token, this.listQueryname).then(res => {
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
            // for (let i = 0; i < detailLen; i++) {
            //   if (i === data.detailist.length - 1) {
            //     this.list[0].dicDetailType = this.list[0].dicdetailDescription + data.detailist[i].dicdetailDescription
            //   } else {
            //     this.list[0].dicDetailType = this.list[0].dicdetailDescription + data.detailist[i].dicdetailDescription + '、'
            //   }
            // }
            // console.log(this.list)
          }
        }
      })
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
          if (res) {
            console.log(res)
            this.reload()
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
    test(id) {
      this.editForm.dictDataId = id || 0;
      this.$http({
        url: this.$http.adornUrl(`sysdict/data/${this.editForm.dictDataId}`),
        method: "get",
        params: this.$http.adornParams()
      }).then(({data}) => {
        if (data && data.code === 200) {
          this.editForm.dictDataId = data.data.dictDataId;
          this.editForm.dictLabel = data.data.dictLabel;
          this.editForm.dictValue = data.data.dictValue;
          this.editForm.dictDataSort = data.data.dictDataSort;
        }
      });
    },
    // 编辑
    _editSubmit() {
      // var editData = _index;
      // this.list[editData].id = this.update.id;
      // this.list[editData].name = this.update.name;
      // this.list[editData].value = this.update.value;
      // this.list[editData].solt = this.update.solt;
      // this.$http({
      //   url: this.$http.adornUrl(`sysdict/data`),
      //   method: "put",
      //   data: this.$http.adornData(this.editForm)
      // }).then(({ data }) => {
      //   if (data && data.code === 200) {
      //     this.$message({
      //       message: "操作成功",
      //       type: "success",
      //       duration: 1500,
      //       onClose: () => {
      //         this.editFormVisible = false;
      //         this.initDataList();
      //       }
      //     });
      //   } else {
      //     this.$message.error(data.msg);
      //   }
      // });
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
