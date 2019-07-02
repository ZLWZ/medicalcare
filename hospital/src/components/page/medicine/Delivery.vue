<template>
  <div class="delivery">
    <div class="title">
      <p>药库出药</p>
    </div>
    <div class="filter-container" style="text-align: center">
      <div class="block">
        <el-date-picker
          v-model="value"
          type="datetimerange"
          value-format="yyyy-MM-dd HH:mm:ss"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>
        <div class="clearance"></div>
        <el-button class="filter-item" type="warning" icon="el-icon-search" @click="chaxun">搜索</el-button>
      </div>
    </div>
    <el-table v-loading="loading" ref="multipleTable" height="400" :data="table" border tooltip-effect="dark" stripe :header-cell-style="getRowClass" style="width: 100%;font-size: 14px">
      <el-table-column align="center" label="序列号" width="70" prop="uid">
        <template scope="scope">{{ scope.row.aid }}</template>
      </el-table-column>
      <el-table-column align="center" prop="aname" label="药品名"></el-table-column>
      <el-table-column align="center" prop="atype" label="类别"></el-table-column>
      <el-table-column align="center" prop="specifi.sname" label="规格"></el-table-column>
      <el-table-column align="center" prop="num" label="申请数量"></el-table-column>
      <el-table-column align="center" prop="department.dname" label="申请科室"></el-table-column>
      <el-table-column align="center" prop="mkdate" label="申请时间" width="200px"></el-table-column>
      <el-table-column align="center" prop="outdate" label="处理时间" width="200px"></el-table-column>
      <el-table-column align="center" label="操作">
        <template scope="scope">
          <el-button size="small" type="success" @click="handleOut(scope.row.aid)">出药</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="选择出药" :visible.sync="dialogFormVisible" top="80px" width="50%">

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormEditVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleEditSubmit('tableEdit')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
    export default {
      name: "Delivery",
      data(){
        return{
          value:'',
          loading:true,
          dialogFormVisible:false,
          table:[],
          getRowClass ({row, column, rowIndex, columnIndex}) {
            if (rowIndex === 0) {
              return 'background: rgb(233, 233, 235)'
            } else {
              return ''
            }
          },
        }
      },
      methods:{
        chaxun(){

        },
        handleOut(){
          this.dialogFormVisible = true
        },
        flush(){
          this.loading = true
          this.$axios.get("/api/medicine/getAllApply").then((response)=>{
            response.data.forEach((item)=>{
              item.atype = item.atype == 1?'中药':'西药'
              item.outdate = item.outdate == null ?'- - - - -':item.outdate
            })
            this.table = response.data
            this.loading = false
          })
        }
      },
      created() {
        this.flush()
      }

    }
</script>

<style scoped>
  .delivery .title p{
    font-size:24px;
    text-align:center;
    margin:25px 0;
  }
  .clearance{
    display: inline-block;
    width: 25px;
  }
  .filter-container{
    margin-bottom: 20px;
  }

</style>
