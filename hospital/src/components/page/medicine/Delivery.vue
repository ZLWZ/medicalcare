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
      <el-table-column align="center" prop="xnum" label="需求数量"></el-table-column>
      <el-table-column align="center" prop="num" label="未处理数量"></el-table-column>
      <el-table-column align="center" prop="department.dname" label="申请科室"></el-table-column>
      <el-table-column align="center" prop="mkdate" label="申请时间" width="200px"></el-table-column>
      <el-table-column align="center" prop="outdate" label="处理时间" width="200px"></el-table-column>
      <el-table-column align="center" label="操作">
        <template scope="scope">
          <el-button size="small" v-if="scope.row.state == 1" type="success" @click="handleOut(scope.row)">出药</el-button>
          <el-button size="small" v-if="scope.row.state == 2" type="info" disabled>完成</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
    export default {
      name: "Delivery",
      data(){
        return{
          value:'',
          loading:true,
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
          this.loading = true
          var val = JSON.stringify(this.value)
          if(val!=""&&val!="null"){
            val = JSON.parse(val)
            this.flush(val[0],val[1])
          }else{
            this.flush('','')
          }
        },
        handleOut(row){
          console.log(row)
          this.$axios.post("/api/medicine/outYao",{
            id:row.aid,
            dname:row.aname,
              ktype:row.atype == '中药'?1:2,
              num:row.num,
              sid:row.sid
          }).then((response)=>{
            if(response.data){
              this.$message({message: '出药成功！',type: 'success'});
              this.flush('','')
            }else{
              this.$confirm('药库数量不足,是否按照实际数量出药?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'})
                .then(() => {
                  this.$axios.post("/api/medicine/chuYao",{
                    id:row.aid,
                    dname:row.aname,
                    ktype:row.atype == '中药'?1:2,
                    num:row.num,
                    sid:row.sid
                  }).then((response)=>{
                    if(response.data){
                      this.$message({type: 'success',message: '出药成功!'});
                      this.flush('','');
                    }else{
                      this.$message({type: 'info',message: '药库不足或者没有此药品'});
                    }
                  })
                })
                .catch(() => {this.$message({type: 'info',message: '出药失败'});})
            }
          })
        },
        flush(begin,end){
          this.loading = true
          this.$axios.get("/api/medicine/getAllApply",{params:{
              begin:begin,
              end:end
            }}).then((response)=>{
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
        this.flush('','');
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
