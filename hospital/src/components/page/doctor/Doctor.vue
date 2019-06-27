<template>
  <div class="doctor">
    <div class="title">
      <p>医生诊断</p>
    </div>
    <div class="filter-container" style="text-align: center">
      <el-input style="width: 200px;" placeholder="挂号序号" v-model="rid"></el-input>
      <div class="clearance"></div>
      <el-input style="width: 200px;" placeholder="患者姓名" v-model="rname"></el-input>
      <div class="clearance"></div>
      <el-button type="primary" icon="el-icon-search" @click="flush">搜索</el-button>
      <el-button type="danger" icon="el-icon-refresh" @click="refresh">重置</el-button>
    </div>
    <el-table v-loading="loading" ref="table" :data="table" border tooltip-effect="dark" stripe :header-cell-style="getRowClass" style="width: 100%;font-size: 14px">
      <el-table-column align="center" label="挂号序号" width="120" prop="rid">
        <template scope="scope">{{ scope.row.rid }}</template>
      </el-table-column>
      <el-table-column align="center" prop="rname" label="患者姓名"  width="80"></el-table-column>
      <el-table-column align="center" prop="idcard" label="身份证" show-overflow-tooltip></el-table-column>
      <el-table-column align="center" prop="department.dname" label="科室" show-overflow-tooltip width="100"></el-table-column>
      <el-table-column align="center" prop="user.uname" label="医生" show-overflow-tooltip></el-table-column>
      <el-table-column align="center" prop="redate" label="挂号日期" sortable show-overflow-tooltip></el-table-column>
      <el-table-column align="center" prop="rstatic" label="状态" show-overflow-tooltip></el-table-column>
      <el-table-column align="center" label="操作" width="200">
        <template scope="scope">
          <el-button size="small" v-if="scope.row.rstatic == '未处理'" type="primary" @click="showdialog(scope.row,1)">诊断</el-button>
          <el-button size="small" v-if="scope.row.rstatic == '已处理'" type="success" @click="showdialog(scope.row,2)">详情</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination-container">
      <el-pagination @size-change="handleSizeChange"
                     @current-change="handleCurrentChange" :current-page="current" :page-sizes="[5,10,15,20]" :page-size="size" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>
    <!--查看详情-->
    <el-dialog :title="title" :visible.sync="dialogFormVisible" top="30px" width="55%">
      <div v-if="!showstate">
        <div style="margin-top: -20px">
          <label class="lab">挂号序号：<span>{{details.rid}}</span></label>
          <label class="lab">挂号日期：<span>{{details.redate}}</span></label>
          <hr class="hr">
          <label class="lab">患者姓名：<span>{{details.rname}}</span></label>
          <label class="lab">科室：<span>{{details.department.dname}}</span></label>
          <label class="lab">医生：<span>{{details.user.uname}}</span></label>
          <hr class="hr">
          <h5 class="prescriptionC-title">R</h5>
          <el-table :data="pregdetils" height="350px" style="width: 80%; margin: 0 auto;">
            <el-table-column prop="rdname" label="药品名称">
            </el-table-column>
            <el-table-column prop="dmoney" label="单价（元）">
            </el-table-column>
            <el-table-column prop="number" label="数量">
            </el-table-column>
            <el-table-column prop="zmoney" label="总价（元）">
            </el-table-column>
          </el-table>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="dialogFormVisible = !dialogFormVisible" style="margin-right: 50px">确 定</el-button>
          </div>
        </div>
      </div>
      <div v-if="showstate">
        <div style="margin-top: -20px">
          <el-table :data="tableData2" :default-sort = "{prop: 'id', order: 'ascending'}" style="width: 100%" height="240px" :header-cell-style="getRowClass">
            <el-table-column align="center" sortable prop="id" label="编号"></el-table-column>
            <el-table-column align="center" prop="rdname" label="药名"></el-table-column>
            <el-table-column align="center" prop="dmoney" label="单价"></el-table-column>
            <el-table-column align="center" label="数量">
              <template scope="scope">
                <el-input-number v-model="scope.row.number" @change="handleChange(scope.row)" :min="1" :max="10" size="mini" style="width: 100px"></el-input-number>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="zmoney" label="总价"></el-table-column>
            <el-table-column align="center" label="操作">
              <template scope="scope">
                <el-button size="small" type="danger" icon="el-icon-minus" circle @click="deltable(scope.row)"></el-button>
              </template>
            </el-table-column>
          </el-table>
          <hr style="margin: 10px 0">
          <el-table :data="tableData1" :default-sort = "{prop: 'id', order: 'ascending'}" style="width: 100%" height="240px" :header-cell-style="getRowClass">
            <el-table-column align="center" sortable prop="id" label="编号"></el-table-column>
            <el-table-column align="center" prop="dname" label="药名"></el-table-column>
            <el-table-column align="center" prop="dtype" label="类型"></el-table-column>
            <el-table-column align="center" prop="money" label="单价"></el-table-column>
            <el-table-column align="center" label="操作">
              <template scope="scope">
                <el-button size="small" type="primary" icon="el-icon-plus" circle @click="addtable(scope.row)"></el-button>
              </template>
            </el-table-column>
          </el-table>
          <div slot="footer" class="dialog-footer">
            <el-button @click="quxiao">取 消</el-button>
            <el-button type="primary" @click="handleEditSubmit">确 定</el-button>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
    import diagnose from './Diagnose'
    export default {
      name: "doctor",
      data(){
        return {
          //表格顶部颜色条
          getRowClass ({row, column, rowIndex, columnIndex}) {
            if (rowIndex === 0) {
              return 'background: rgb(233, 233, 235)'
            } else {
              return ''
            }
          },
          showstate:true,
          dialogFormVisible:false,
          loading:true,
          table:[],
          details:{},
          pregdetils:[],
          tableData1:[],
          tableData2:[],
          rid:'',
          rrid:'',
          title:'',
          rname:'',
          size:5,//每页条数
          total: 0,//总条数
          current:1,//当前页数
        }
      },
      methods:{
        refresh(){
          this.rid = ''
          this.rname = ''
          this.flush()
        },
        //总条数切换
        handleSizeChange (val) {
          this.size = val;
          this.current = 1;
          this.flush();
        },
        //当前页切换
        handleCurrentChange (val) {
          this.current = val;
          this.flush();
        },
        showdialog(row,state){
          this.dialogFormVisible = !this.dialogFormVisible
          this.rrid = row.rid
          if(state==1){
            this.showstate = true
            this.title = '诊断'
            this.tableData2 = []
            this.getAllDrugs()
          }
          if(state==2){
            this.title = '药方单'
            this.showstate = false
            this.details = row
            this.$axios.get("/api/doctor/getAllPregdetils",{params:{rid:row.rid}}).then((response)=>{
                this.pregdetils = response.data
            })

          }
        },
        quxiao(){
          this.dialogFormVisible = false;
          this.tableData2 = []
          this.getAllDrugs()
        },
        addtable(row){
          let t1 = this.tableData1
          t1.forEach(function (item,index) {
            if(item.id == row.id){
              t1 = t1.splice(index,1)
            }
          })
          let tt = {
            id:row.id,
            rdname:row.dname,
            dmoney:row.money,
            dtype:row.dtype,
            number:1,
            zmoney:row.money
          }
          this.tableData2.push(tt)
        },
        deltable(row){
          let t2 = this.tableData2
          t2.forEach(function (item,index) {
            if(item.id == row.id){
              t2 = t2.splice(index,1)
            }
          })
          let tt = {
            id:row.id,
            dname:row.rdname,
            money:row.dmoney,
            dtype:row.dtype
          }
          this.tableData1.push(tt)
        },
        handleChange(row){
          row.zmoney = this.accMul(row.number,row.dmoney)
        },
        handleEditSubmit(){
          let td2 = this.tableData2
          if(td2.length==0){
            this.$message({message: '该药单为空,不可提交！！',type: 'warning'})
          }else{
            this.$axios.post("/api/doctor/addDrugs",{
              drugs:this.tableData2,
              rid:this.rrid
            }).then((response)=>{
              if(response.data){
                this.$message({message: '药单已开出,处理完成！',type: 'success'})
                this.flush()
                this.dialogFormVisible = !this.dialogFormVisible
              }else{
                this.$message.error('未知异常，稍后重试！')
              }
            })
          }
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
          this.loading = true;
           this.$axios.get('/api/cashier/getAllRegister',{
             params:{
               rid:this.rid,
               rname:this.rname,
               current:this.current,
               size:this.size,
             }
           }).then((response) => {             // mark
             this.total = response.data.total
             response.data.rows.forEach(function (item) {
               item.rstatic = item.rstatic == 1?'未处理':'已处理'
             })
             this.table = response.data.rows
             this.loading = false;
           })
        },
        getAllDrugs(){
          this.$axios.get("/api/doctor/getAllDrugs",{params:{dname:''}}).then((response)=>{
            response.data.forEach(function (item) {
              item.dtype = item.dtype == 1?'中药':'西药'
            })
            this.tableData1 = response.data
          })
        }
      },
      created() {
        this.flush();
        this.getAllDrugs();
      },
      components:{
        diagnose
      }
    }
</script>

<style scoped>
  .doctor .title p{
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
  .dialog-footer{
    text-align: right;
    margin-top: 20px;
  }
  .pagination-container{
    width: 700px;
    text-align: center;
    margin: 30px auto;
  }
  .hr{
    border-top: 1px solid #4A7C32;
  }
  .lab{
    display: inline-block;
    font-size: 16px;
    width: 240px;
    margin: 10px 10px 0;
    font-weight: 200;
  }
  .lab span{
    font-weight: 600;
  }
  .prescriptionC-title{
    font-size: 26px;
    margin-left: 20px;
  }
</style>
