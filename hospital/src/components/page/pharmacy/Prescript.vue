<template>
  <div class="prescript">
    <div class="title">
      <p>处理药方</p>
    </div>
    <div class="filter-container" style="text-align: center">
      <el-input style="width: 200px;" placeholder="挂号序号" v-model="rid"></el-input>
      <div class="clearance"></div>
      <el-input style="width: 200px;" placeholder="患者姓名" v-model="rname"></el-input>
      <div class="clearance"></div>
      <el-button type="primary" icon="el-icon-search" @click="flush">搜索</el-button>
      <el-button type="danger" icon="el-icon-refresh" @click="refresh">重置</el-button>
    </div>
    <el-table v-loading="loading" :data="table" :header-cell-style="getRowClass" style="width: 100%;font-size: 14px">
      <el-table-column type="expand">
        <template scope="props">
          <el-form label-position="left" inline class="form-expand">
            <el-form-item label="处方编号：">
              <span>{{ props.row.rid }}</span>
            </el-form-item>
            <el-form-item label="创建日期：">
              <span>{{ props.row.redate }}</span>
            </el-form-item>
            <hr class="hr">
            <el-form-item label="患者姓名：">
              <span>{{ props.row.rname }}</span>
            </el-form-item>
            <el-form-item label="医生：">
              <span>{{ props.row.user.uname }}</span>
            </el-form-item>
            <el-form-item label="科室：">
              <span>{{ props.row.department.dname }}</span>
            </el-form-item>
            <hr class="hr">
            <h5 class="prescriptionC-title">R</h5>
            <template>
              <el-table :data="props.row.pregdetils" style="width: 80%;height: 200px; margin: 0 auto;">
                <el-table-column prop="rdname" label="药品名称">
                </el-table-column>
                <el-table-column prop="dmoney" label="单价（元）">
                </el-table-column>
                <el-table-column prop="number" label="数量">
                </el-table-column>
                <el-table-column prop="zmoney" label="总价（元）">
                </el-table-column>
              </el-table>
            </template>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column align="center" label="处方编号" prop="rid"></el-table-column>
      <el-table-column align="center" label="患者姓名" prop="rname"></el-table-column>
      <el-table-column align="center" label="身份证" prop="idcard"></el-table-column>
      <el-table-column align="center" label="科室" prop="department.dname"></el-table-column>
      <el-table-column align="center" label="审核药师" prop="user.uname"></el-table-column>
      <el-table-column align="center" label="创建日期" prop="redate"></el-table-column>
      <el-table-column align="center" label="操作" width="210">
        <template scope="scope">
          <el-button type="primary" v-if="scope.row.pstate == 0" @click="outyao(scope.row,1)">出药</el-button>
          <el-button type="success" v-if="scope.row.pstate == 1" @click="outyao(scope.row,2)">详情</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :title="title" :visible.sync="dialogFormVisible" top="150px" width="55%">
      <div  v-if="showstate">
        <el-table :data="tableData" @selection-change="handleSelectionChange" :default-sort = "{prop: 'did', order: 'ascending'}" style="width: 100%" height="240px" :header-cell-style="getRowClass">
          <el-table-column type="selection" width="55">
          </el-table-column>
          <el-table-column align="center" sortable prop="did" label="编号"></el-table-column>
          <el-table-column align="center" prop="rdname" label="药名"></el-table-column>
          <el-table-column align="center" prop="dmoney" label="单价"></el-table-column>
          <el-table-column align="center" label="数量">
            <template scope="scope">
              <el-input-number v-model="scope.row.number" @change="handleChange(scope.row)" :min="1" :max="10" size="mini" style="width: 100px"></el-input-number>
            </template>
          </el-table-column>
          <el-table-column align="center" prop="zmoney" label="总价"></el-table-column>
        </el-table>
        <div style="margin-top: 20px">
          <el-button type="primary" style="float: right;margin-left: 20px" @click="submit">确定</el-button>
          <el-button type="danger" style="float: right" @click="quxiao">取消</el-button>
          <div style="clear: both"></div>
        </div>
      </div>
      <div v-if="!showstate" style="width: 100%;margin-top: -20px">
        <div style="float:left;width: 49%;">
          <h2 style="text-align: center;margin-bottom: 20px;">预处理药方</h2>
          <el-table :data="tableData"  show-summary @selection-change="handleSelectionChange" :default-sort = "{prop: 'did', order: 'ascending'}" style="width: 100%" height="248px" :header-cell-style="getRowClass">
            <el-table-column align="center" prop="rdname" label="药名" width="130px"></el-table-column>
            <el-table-column align="center" prop="dmoney" label="单价"></el-table-column>
            <el-table-column align="center" prop="number" label="数量"></el-table-column>
            <el-table-column align="center" prop="zmoney" label="总价"></el-table-column>
          </el-table>
        </div>
        <div style="float:left;width: 0.3%;background: #6f7180;height: 250px;margin: 50px 0.85%"></div>
        <div style="float:right;width: 49%;">
          <h2 style="text-align: center;margin-bottom: 20px;">实际处理药方</h2>
          <el-table :data="tableData2" show-summary @selection-change="handleSelectionChange" :default-sort = "{prop: 'did', order: 'ascending'}" style="width: 100%" height="248px" :header-cell-style="getRowClass">
            <el-table-column align="center" prop="rdname" label="药名" width="130px"></el-table-column>
            <el-table-column align="center" prop="dmoney" label="单价"></el-table-column>
            <el-table-column align="center" prop="number" label="数量"></el-table-column>
            <el-table-column align="center" prop="zmoney" label="总价"></el-table-column>
          </el-table>
        </div>
        <div>
          <el-button style="float: right;margin:20px 10px 0 0" @click="dialogFormVisible = !dialogFormVisible" type="primary">确定</el-button>
        </div>
        <div style="clear: both"></div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
    export default {
      name: "Prescript",
      data(){
        return{
          //表格顶部颜色条
          getRowClass ({row, column, rowIndex, columnIndex}) {
            if (rowIndex === 0) {
              return 'background: rgb(233, 233, 235)'
            } else {
              return ''
            }
          },
          rid:'',
          rrid:'',
          rname:'',
          title:'',
          showstate:true,
          dialogFormVisible:false,
          loading:'',
          table:[],
          tableData:[],
          tableData2:[],
          multipleSelection: []
        }
      },
      methods:{
        refresh(){
          this.rid = ''
          this.rname = ''
          this.flush()
        },
        outyao(row,state){
          this.dialogFormVisible = !this.dialogDelVisible
          this.rrid = row.rid
          if(state==1){
            this.showstate = true
            this.title = '出药'
            this.tableData = row.pregdetils
          }
          if(state==2){
            this.title = '详情'
            this.showstate = false
            this.tableData = row.pregdetils
            this.$axios.get("/api/pharmacy/selProcdetils",{params:{rid:this.rrid}}).then((response)=>{
              this.tableData2 = response.data
            })
          }
        },
        quxiao(){
          this.dialogFormVisible = !this.dialogFormVisible
          this.flush()
        },
        submit(){
          if(this.multipleSelection.length == 0){
            this.$message({message: '请选择药品',type: 'warning'});
          }else{
            this.$axios.post("/api/pharmacy/checkNum",{checknum:this.multipleSelection}).then((response)=>{
              if(response.data != ''){
                this.$message({message: response.data,type: 'warning'});
              }else{
                this.$axios.post("/api/pharmacy/addProcdetils",{
                  checknum:this.multipleSelection,
                  rid:this.rrid
                }).then((response)=>{
                  if(response.data){
                    this.$message({message: '配药成功！',type: 'success'});
                    this.quxiao();
                    this.flush();
                  }else{
                    this.$message.error("未知异常，稍后重试！！")
                  }
                })
              }
            })
          }
        },
        handleChange(row){
          row.zmoney = this.accMul(row.number,row.dmoney)
        },
        handleSelectionChange(val){
          this. multipleSelection = val
        },
        accMul(arg1, arg2) {
          var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
          try {
            m += s1.split(".")[1].length;
          }
          catch (e) {
          }
          try {
            m += s2.split(".")[1].length;
          }
          catch (e) {
          }
          return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
        },
        flush(){
          this.$axios.get('/api/cashier/getAllRegister',{
            params:{
              rid:this.rid,
              rname:this.rname,
              current:-1,
              size:-1,
              end:2
            }
          }).then((response) => {             // mark
            response.data.rows.forEach(function (item) {
              item.rstatic = item.rstatic == 1?'未处理':'已处理'
            })
            this.table = response.data.rows
            console.log(this.table)
            this.loading = false;
          })
        }
      },
      created() {
        this.flush()
      }
    }
</script>

<style scoped>
  .prescript .title p{
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
  .el-input{
    width: 200px
  }
  .form-expand{
    /* background: #99FF66 */
    width:80%;
    margin: 0 auto;
  }
  .el-form-item{
    width: 29%;
    display: inline-block;
    margin-left: 20px;
  }
  .symptoms{
    width: 100%
  }
  .Rp{
    width: 80%;
    margin-left: 10%;
  }
  .hr{
    margin-top: -25px;
    border-top: 1px solid #4A7C32
  }
  .prescriptionC-title{
    font-size: 26px;
    margin-left: 20px
  }
  .form-expand .el-form-item span{
    display: block;
    font-size: 15px;
    font-weight: bold;
  }

</style>
