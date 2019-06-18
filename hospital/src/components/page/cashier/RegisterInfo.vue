<template>
  <div class="turnOver">
    <div class="title">
      <p>挂号信息记录</p>
    </div>
    <div class="filter-container" style="text-align: center">
      <el-input style="width: 200px;" placeholder="挂号序号" v-model="rid"></el-input>
      <div class="clearance"></div>
      <el-input style="width: 200px;" placeholder="患者姓名" v-model="rname"></el-input>
      <div class="clearance"></div>
      <el-button type="primary" icon="search" @click="flush">搜索</el-button>
    </div>
    <el-table :data="tableData" border="true" style="width: 100%;font-size: 14px">
    <el-table-column type="expand">
      <template slot-scope="props">
        <el-form inline  label-width="150px" class="demo-table-expand">
          <el-form-item label="挂号序号：">
            <span>{{ props.row.rid }}</span>
          </el-form-item>
          <el-form-item label="挂号费：">
            <span>{{ props.row.rmoney }}</span>
          </el-form-item>
          <el-form-item label="挂号时间：">
            <span>{{ props.row.redate }}</span>
          </el-form-item>
          <el-form-item label="药方费：">
            <span>{{ props.row.rprice }}</span>
          </el-form-item>
          <el-form-item label="患者姓名：">
            <span>{{ props.row.rname }}</span>
          </el-form-item>
          <el-form-item label="科室：">
            <span>{{ props.row.dname }}</span>
          </el-form-item>
          <el-form-item label="身份证：">
            <span>{{ props.row.idcard }}</span>
          </el-form-item>
          <el-form-item label="医生：">
            <span>{{ props.row.uname }}</span>
          </el-form-item>
          <el-form-item label="状态：">
            <span>{{ props.row.rstatic }}</span>
          </el-form-item>
        </el-form>
      </template>
    </el-table-column>
    <el-table-column label="挂号序号" prop="rid"></el-table-column>
    <el-table-column label="患者姓名" prop="rname"></el-table-column>
    <el-table-column label="身份证号" prop="idcard"></el-table-column>
    <el-table-column label="挂号日期" prop="redate"></el-table-column>
      <el-table-column align="center" label="操作">
        <template scope="scope">
          <el-button size="small" type="primary" icon="el-icon-edit" circle @click="handleEdit(scope.row.uid)"></el-button>
          <el-button size="small" icon="el-icon-delete" circle type="danger" @click="handleDelete(scope.row.uid)"></el-button>
        </template>
      </el-table-column>
  </el-table>
  </div>
</template>

<script type="text/ecmascript-6">
  //import {api} from '../../../global/api.js';
  export default {
    data () {
      return {
        name:'RegisterInfo',
        rid:'',//编号
        rname:'',//姓名
        tableData: []
      };
    },
    methods: {
      formClear () {
        this.form = {
          name: '',
          age: '',
          sex: '',
          department: '',
          address: '',
          seniority: '',
          number: '',
          position: '',
          processor: '',
          hiredate: '',
          resignation_time: '',
          resignation_reason: ''
        };
      },
      handleDelete (index, row) {
        let vm = this;
        console.log('单个删除选择的row: ', index, '-----', row);
        vm.table.splice(index, 1);
        this.$message({
          message: '删除成功！',
          type: 'success'
        });
      },
      flush(){
        this.$axios.get('/api/cashier/getAllRegister',{
          params:{
            rid:this.rid,
            rname:this.rname
          }
        }).then((response) => {             // mark
          var td =this.tableData;
          response.data.forEach(function (item) {
            var register = {
              rid:item.rid,
              rname:item.rname,
              idcard:item.idcard,
              dname:item.department.dname,
              uname:item.user.uname,
              redate:item.redate,
              rmoney:item.rmoney,
              rprice:item.rprice,
              rstatic:item.rstatic == 1?'未处理':'已处理'
            }
            let date = new Date(register.redate);
            let y = date.getFullYear();
            let MM = date.getMonth() + 1;
            MM = MM < 10 ? ('0' + MM) : MM;
            let d = date.getDate();
            d = d < 10 ? ('0' + d) : d;
            let h = date.getHours();
            h = h < 10 ? ('0' + h) : h;
            let m = date.getMinutes();
            m = m < 10 ? ('0' + m) : m;
            let s = date.getSeconds();
            s = s < 10 ? ('0' + s) : s;
            register.redate=y + '-' + MM + '-' + d + ' ' + h + ':' + m + ':' + s;
            td.push(register)
          })
        }, response => {
          this.$message({message: '数据请求失败',type: 'error'});
        });
      }
    },
    created () {
      this.flush();
    }
  };
</script>

<style scoped="scoped">
  .turnOver .title p{
    font-size:24px;
    text-align:center;
    margin:25px 0;
  }
  .clearance{
    display: inline-block;
    width: 25px;
  }
  .demo-table-expand{
    font-size:0;
  }
  .demo-table-expand label{
    width:90px;
    color:#99a9bf;
  }
  .demo-table-expand .el-form-item{
    margin-right:0;
    margin-bottom:0;
    width: 50%;
  }
  .filter-container{
    margin-bottom: 20px;
  }
</style>
