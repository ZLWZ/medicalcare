<template>
  <div class="inquiries">
    <div class="title">
      <p>药品查询</p>
    </div>
    <div class="filter-container" style="text-align: center">
      <el-input style="width: 200px;" placeholder="药品名" v-model="dname"></el-input>
      <div class="clearance"></div>
      <el-select v-model="dtype" @change="seldrug" filterable clearable placeholder="请选择类型">
        <el-option v-for="item in type" :key="item.dtype" :label="item.dname" :value="item.dtype"></el-option>
      </el-select>
      <div class="clearance"></div>
      <el-select v-model="sid" @change="seldrug" filterable clearable placeholder="请选择规格">
        <el-option v-for="item in specifi" :key="item.sid" :label="item.sname" :value="item.sid"></el-option>
      </el-select>
      <div class="clearance" style="display: block;margin-bottom: 10px"></div>
      <el-select v-model="did" @change="seldrug" filterable clearable placeholder="请选择剂型">
        <el-option v-for="item in dosage" :key="item.did" :label="item.dname" :value="item.did"></el-option>
      </el-select>
      <div class="clearance"></div>
      <el-select v-model="cid" @change="seldrug" filterable clearable placeholder="请选择厂家">
        <el-option v-for="item in company" :key="item.cid" :label="item.cname" :value="item.cid"></el-option>
      </el-select>
      <div class="clearance"></div>
      <div style="width: 203px;display: inline-block;text-align: left">
        <el-button type="primary" icon="el-icon-search" @click="seldrug">搜索</el-button>
        <el-button type="danger" icon="el-icon-refresh" @click="resetForm">重置</el-button>
      </div>
    </div>
    <el-table v-loading="loading" ref="multipleTable" :data="table" border tooltip-effect="dark" stripe :header-cell-style="getRowClass" style="width: 100%;font-size: 14px" @selection-change="handleSelectionChange">
      <el-table-column align="center" type="selection" width="40"></el-table-column>
      <el-table-column align="center" label="编号" width="70" prop="id">
        <template scope="scope">{{ scope.row.id }}</template>
      </el-table-column>
      <el-table-column align="center" prop="dname" label="药品名" width="150"></el-table-column>
      <el-table-column align="center" prop="dtype" label="类型" width="80px"></el-table-column>
      <el-table-column align="center" prop="money" label="单价"></el-table-column>
      <el-table-column align="center" prop="specifi.sname" label="规格"></el-table-column>
      <el-table-column align="center" prop="dosage.dname" label="剂型"></el-table-column>
      <el-table-column align="center" prop="company.cname" label="厂家" width="200px"></el-table-column>
      <el-table-column align="center" prop="num" label="库存"></el-table-column>
      <el-table-column align="center" label="操作">
        <template scope="scope">
          <el-button :type="scope.row.type" :icon="scope.row.icon" @click="enable(scope.row.id,scope.row.kstate)" circle></el-button>
          <!--<el-button size="small" type="primary" icon="el-icon-edit" circle @click="handleEdit(scope.row.uid)"></el-button>-->
          <el-button size="small" icon="el-icon-delete" circle type="danger" @click="handleDelete(scope.row.id)"></el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination-container">
      <el-pagination @size-change="handleSizeChange"
                     @current-change="handleCurrentChange" :current-page="current" :page-sizes="[5,10,15,20]" :page-size="size" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
    export default {
        name: "Inquiries",
        data(){
          return{
            dname:'',
            dtype:'',
            sid:'',
            did:'',
            cid:'',
            table:[],
            size:5,//每页条数
            total: 0,//总条数
            current:1,//当前页数
            specifi:[],
            dosage:[],
            company:[],
            loading:true,
            type:[
              {dtype:1,dname:'中药'},
              {dtype:2,dname:'西药'},
            ],
            multipleSelection:[],
            //表格顶部颜色条
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
        //选中触发事件
        handleSelectionChange(val) {
          this.multipleSelection = val;
        },
        seldrug(){
          this.current = 1
          this.flush()
        },
        handleDelete(id){
          this.$confirm('是否移除该药物,此操作不可恢复?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'})
            .then(() => {
              this.$axios.get("/api/pharmacy/updateDrugState",{
                params:{id:id,kstate:3}
              }).then((response)=>{
                if(response.data){
                  this.loading = true;
                  this.$message({type: 'success',message: '移除成功!'});
                  this.flush();
                }else{
                  this.$message({type: 'info',message: '移除失败'});
                }
              })
            })
            .catch(() => {this.$message({type: 'info',message: '移除失败'});})
        },
        enable(id,kstate){
          id = parseInt(id);
          if(kstate == 1){
            this.$confirm('是否禁止销售该药物?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'})
              .then(() => {
                this.$axios.get("/api/pharmacy/updateDrugState",{
                  params:{id:id,kstate:2}
                }).then((response)=>{
                  if(response.data){
                    this.loading = true;
                    this.$message({type: 'success',message: '禁用成功!'});
                    this.flush();
                  }else{
                    this.$message({type: 'info',message: '禁用失败'});
                  }
                })
              })
              .catch(() => {this.$message({type: 'info',message: '禁用失败'});})
          }else if(kstate == 2){
            this.$confirm('是否启用销售该药物?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'})
              .then(() => {
                this.$axios.get("/api/pharmacy/updateDrugState",{
                  params:{id:id,kstate:1}
                }).then((response)=>{
                  if(response.data){
                    this.loading = true;
                    this.$message({type: 'success',message: '启用成功!'});
                    this.flush();
                  }else{
                    this.$message({type: 'info',message: '启用失败'});
                  }
                })
              })
              .catch(() => {this.$message({type: 'info',message: '启用失败'});});
          }
        },
        flush(){
          this.loading = true;
          this.$axios.post("/api/pharmacy/getAllDrugs",{
            dname:this.dname,
            dtype:this.dtype == ""?0:this.dtype,
            cid:this.cid == ""?0:this.cid,
            did:this.did == ""?0:this.did,
            sid:this.sid == ""?0:this.sid,
            current:this.current,
            size:this.size
          }).then((response)=>{
            response.data.rows.forEach((item) => {
              item.dtype = item.dtype == 1?'中药':'西药'
              item.type=item.kstate == 1?'success':'danger'
              item.icon=item.kstate == 1?'el-icon-check':'el-icon-close'
            })
            this.table = response.data.rows
            this.total = response.data.total
            this.loading = false;
            console.log(this.table)
          })
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
        resetForm(){
          this.dname = '';
          this.dtype = '';
          this.cid = '';
          this.did = '';
          this.sid = '';
          this.flush();
        }
      },
      created() {
        this.flush()
        this.$axios.get("/api/Storehouse/selList").then((response)=>{
          this.company = response.data.data.company
          this.dosage = response.data.data.dosage
          this.specifi = response.data.data.specifi
        })
      }
    }
</script>

<style scoped>
  .inquiries .title p{
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
  .pagination-container{
    width: 700px;
    text-align: center;
    margin: 30px auto;
  }
</style>
