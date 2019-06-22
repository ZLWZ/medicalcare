<template>
  <div class="resign">
    <div class="title">
      <p>人员离职记录</p>
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
    <!--删除信息-->
    <el-dialog title="警告" :visible.sync="dialogDelVisible" top="290px" width="25%">
      <p style="margin: -20px 0;">是否清空此人员的所有信息,删除不可恢复！！</p>
      <div slot="footer" class="dialog-footer" style="margin-top: -20px">
        <el-button @click="quxiao">取 消</el-button>
        <el-button type="primary" @click="handelyes">确 定</el-button>
      </div>
    </el-dialog>
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
          value:'',
          uid:'',
          dialogDelVisible:false
        }
      },
      methods:{
        chaxun(){
           this.loading = true
          var val = JSON.stringify(this.value)
          if(val!=""&&val!="null"){
            val = JSON.parse(val)
            this.flush(val[0],val[1])
          }else{
            this.flush('','')
          }
        },
        handleDelete(uid){
          this.uid = uid
          this.dialogDelVisible = true
        },
        handelyes(){
          this.$axios.get("/api/manager/deleteInformation",{params:{uid:this.uid}})
            .then((response)=>{
              if(response.data){
                this.$message({message: '删除成功！',type: 'success'});
                this.loading = true;
                this.flush('','');
              }else{
                this.$message('删除失败');
              }
              this.dialogDelVisible = false
            })
        },
        quxiao(){
          this.dialogDelVisible = false;
          this.$message('已取消操作');
        },
        flush(begin,end){
          this.loading = true
          this.$axios.get("/api/manager/getAllInformation",{params:{
              begin:begin,
              end:end
          }}).then((response)=>{
            this.table = response.data
            this.table.forEach(function (item) {
              item.user.sex = item.user.sex==1?'男':'女'
            })
            this.loading = false
          }), response => {
            this.$message({message: '数据请求失败',type: 'error'});
            this.loading = false
          }
        }
      },
      created() {
        this.flush('','');
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
