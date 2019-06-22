<template>
  <div class="resign">
    <div class="title">
      <p>人员离职记录</p>
    </div>
    <div class="filter-container" style="text-align: center">
      <div class="block">
        <el-date-picker
          v-model="value1"
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
    <el-table :data="table" border tooltip-effect="dark" style="width: 100%;font-size: 14px" height="400" stripe :header-cell-style="getRowClass" v-loading="loading">
      <el-table-column align="center" label="工号" width="100" fixed>
        <template scope="scope">{{ scope.row.uid }}</template>
      </el-table-column>
      <el-table-column align="center" prop="user.uname" fixed label="姓名" width="100"></el-table-column>
      <el-table-column align="center" prop="user.sex" label="性别" width="80"></el-table-column>
      <el-table-column align="center" prop="user.age" label="年龄" width="80"></el-table-column>
      <el-table-column align="center" prop="history" label="工龄" width="100"></el-table-column>
      <el-table-column align="center" prop="user.department.dname" label="科室" width="120"></el-table-column>
      <el-table-column align="center" prop="user.roleList[0].rname" label="职位" width="120"></el-table-column>
      <el-table-column align="center" prop="user.address" label="地址" width="180"></el-table-column>
      <el-table-column align="center" prop="user.joindate" label="入职日期" width="200"></el-table-column>
      <el-table-column align="center" prop="leavedate" label="离职日期" width="200"></el-table-column>
      <el-table-column align="center" prop="details"  label="离职原因" width="200"></el-table-column>
      <el-table-column align="center" fixed="right" label="操作" width="100">
        <template scope="scope">
          <el-button size="small" type="danger" @click="handleDelete(scope.row.uid)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
    export default {
      name: "Resign",
      loading:false,
      data() {
        return {
          //表格顶部颜色条
          getRowClass ({row, column, rowIndex, columnIndex}) {
            if (rowIndex === 0) {
              return 'background: rgb(233, 233, 235)'
            } else {
              return ''
            }
          },
          table: [],
          value1:''
        }
      },
      methods:{
        chaxun(){
          console.log(typeof this.value1[0])
          console.log(this.value1[1])
        },
        flush(begin,end){
          this.loading = true
          this.$axios.get("/api/manager/getAllInformation",{
            begin:begin,
            end:end
          }).then((response)=>{
            this.table = response.data
            this.table.forEach(function (item) {
              item.user.sex = item.user.sex==1?'男':'女'
            })
            this.loading = false
          })
        }
      },
      created() {
        this.flush();
      }
    }
</script>

<style scoped>
  .resign .title p{
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
