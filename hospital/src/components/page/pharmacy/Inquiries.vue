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
        <el-button type="primary" icon="el-icon-search" @click="">搜索</el-button>
        <el-button type="danger" icon="el-icon-refresh" @click="resetForm">重置</el-button>
      </div>
    </div>
    <el-table v-loading="loading" ref="multipleTable" :data="table" border tooltip-effect="dark" stripe :header-cell-style="getRowClass" style="width: 100%;font-size: 14px" @selection-change="handleSelectionChange">
      <el-table-column align="center" type="selection" width="40"></el-table-column>
      <el-table-column align="center" label="工号" width="70" prop="uid">
        <template scope="scope">{{ scope.row.uid }}</template>
      </el-table-column>
      <el-table-column align="center" prop="uname" label="姓名"  width="80"></el-table-column>
      <el-table-column align="center" prop="address" label="地址" show-overflow-tooltip></el-table-column>
      <el-table-column align="center" prop="sex" label="性别" show-overflow-tooltip width="50"></el-table-column>
      <el-table-column prop="age" label="年龄" align="center" show-overflow-tooltip width="80"></el-table-column>
      <el-table-column align="center" prop="rname" label="职称" show-overflow-tooltip width="80"></el-table-column>
      <el-table-column align="center" prop="dname" label="科室" show-overflow-tooltip width="100"></el-table-column>
      <el-table-column align="center" prop="ename" label="学历" show-overflow-tooltip></el-table-column>
      <el-table-column align="center" prop="detials" label="专业" show-overflow-tooltip></el-table-column>
      <el-table-column align="center" prop="joindate" label="入职时间" show-overflow-tooltip></el-table-column>
      <el-table-column align="center" label="操作">
        <!--<template scope="scope">
          <el-button :type="scope.row.type" :icon="scope.row.icon" @click="enable(scope.row.uid,scope.row.state)" circle></el-button>
          <el-button size="small" type="primary" icon="el-icon-edit" circle @click="handleEdit(scope.row.uid)"></el-button>
          <el-button size="small" icon="el-icon-document-delete" circle type="danger" @click="handleDelete(scope.row.uid)"></el-button>
        </template>-->
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
        flush(){
          this.loading = true;
          this.$axios.post("/api/pharmacy/getAllDrugs",{
            dname:this.dname,
            dtype:parseInt(this.dtype),
            cid:parseInt(this.cid),
            did:parseInt(this.did),
            sid:parseInt(this.sid),
            current:this.current,
            size:this.size
          }).then((response)=>{
            this.table = response.data.row
            this.total = response.data.total
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
