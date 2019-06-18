<template>
  <div class="duty">
    <div class="title">
      <p>医院人员值班表</p>
    </div>
    <div class="filter-container">
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="姓名" v-model="uname"></el-input>
      <div class="clearance"></div>
      <el-select v-model="sdate" @change="flush(0)" filterable clearable placeholder="请选择星期">
        <el-option v-for="item in week" :key="item.sdate" :label="item.wname" :value="item.sdate"></el-option>
      </el-select>
      <div class="clearance"></div>
      <el-select v-model="did" @change="flush(0)" filterable clearable placeholder="请选择科室">
        <el-option v-for="item in department" :key="item.did" :label="item.dname" :value="item.did"></el-option>
      </el-select>
      <div class="clearance"></div>
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="flush(0)">搜索</el-button>
      <el-button class="filter-item" type="danger" icon="el-icon-refresh" @click="refresh">重置</el-button>
   </div>
    <el-table ref="multipleTable" height="345" v-loading="loading" :data="table" border tooltip-effect="dark" stripe :header-cell-style="getRowClass" style="width: 100%;font-size: 14px">
      <el-table-column align="center" sortable prop="sid" label="序号">
        <template scope="scope">{{ scope.row.sid }}</template>
      </el-table-column>
      <el-table-column align="center" sortable prop="sdate" label="日期"></el-table-column>
      <el-table-column align="center" prop="dename" label="时段" width="150"></el-table-column>
      <el-table-column align="center" prop="uname" label="姓名"></el-table-column>
      <el-table-column align="center" prop="sex" label="性别"></el-table-column>
      <el-table-column align="center" prop="rname" label="职称"></el-table-column>
      <el-table-column align="center" prop="dname" label="科室"></el-table-column>
      <el-table-column align="center" prop="phone" label="电话"></el-table-column>
      <el-table-column align="center" prop="pname" label="出勤情况"></el-table-column>
      <el-table-column align="center" label="操作">
        <template scope="scope">
          <el-button size="small" type="primary" icon="el-icon-edit" circle @click="handleEdit(scope.row.sid)"></el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 编辑信息的表单 -->
    <el-dialog title="修改值班表" :visible.sync="dialogFormEditVisible" top="45px" width="35%">
      <el-form class="small-space" :rules="rules" :model="tableEdit" ref="tableEdit" label-position="left" label-width="70px" style='width: 400px; margin-left:50px;'>
        <el-form-item label="序号" prop="sid">
          <el-input :disabled="true" style="width: 62%" v-model="tableEdit.sid"></el-input>
        </el-form-item>
        <el-form-item label="日期" prop="sdata">
          <el-input :disabled="true" style="width: 62%" v-model="tableEdit.sdata"></el-input>
        </el-form-item>
        <el-form-item label="时段" prop="did">
          <el-select v-model="tableEdit.did" clearable placeholder="请选择时段">
            <el-option v-for="item in details" :key="item.did" :label="item.dname" :value="item.did"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="姓名" prop="uid">
          <el-select v-model="tableEdit.uid" clearable placeholder="选择轮班人" @change="seluser">
            <el-option v-for="item in user" :key="item.uid" :label="item.uname" :value="item.uid"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="职称" prop="rname">
          <el-input :disabled="true" style="width: 62%" v-model="tableEdit.user.rname"></el-input>
        </el-form-item>
        <el-form-item label="科室" prop="dname">
          <el-input :disabled="true" style="width: 62%" v-model="tableEdit.user.dname"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input :disabled="true" style="width: 62%" v-model="tableEdit.user.phone"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="tableEdit.user.sex">
            <el-radio v-if="tableEdit.user.sex == 1" label="1">男</el-radio>
            <el-radio v-if="tableEdit.user.sex == 2" label="2">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="出勤情况" prop="pid">
          <el-select v-model="tableEdit.pid" clearable placeholder="出勤状态">
            <el-option v-for="item in parti" :key="item.pid" :label="item.pname" :value="item.pid"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormEditVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleEditSubmit('tableEdit')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script type="text/ecmascript-6">
  export default {
    data () {
      return {
        name:'duty',
        //表格顶部颜色条
        getRowClass ({row, column, rowIndex, columnIndex}) {
          if (rowIndex === 0) {
            return 'background: rgb(233, 233, 235)'
          } else {
            return ''
          }
        },
        table: [],//展示表数据
        uname: '',//姓名
        loading:true,
        sdate:'',
        did:'',
        department:[],//科室
        details:[],//时间段
        parti:[],//出勤
        user:[],//用户
        week:[
          {sdate:"星期一",wname:"星期一"},
          {sdate:"星期二",wname:"星期二"},
          {sdate:"星期三",wname:"星期三"},
          {sdate:"星期四",wname:"星期四"},
          {sdate:"星期五",wname:"星期五"},
          {sdate:"星期六",wname:"星期六"},
          {sdate:"星期日",wname:"星期日"}
        ],
        tableEdit: {
          sid:'',
          sdata:'',
          did:'',
          pid:'',
          uid:'',
          user:{
            uname:'',
            sex:'',
            phone:'',
            rname:'',
            dname:''
          }
        },//修改表数据
        dialogFormEditVisible: false,//修改模态框显示状态
        rules: {
          did: [
            { required: true, message: '请选择时段', trigger: 'blur' }
          ],
          uid:[
            { required: true, message: '请选择轮班人', trigger: 'blur' }
          ]
        }
      };
    },
    methods: {
      // 编辑
      handleEdit (sid) {
        this.dialogFormEditVisible = true;
        this.$axios.get("/api/manager/getAllDuty",{
          params:{
            sid:sid,
            uname: '',
            sdate:'',
            did:''
          }
        }).then((response) => {
            console.log(response.data[0])
            this.tableEdit.sid=response.data[0].sid;
            this.tableEdit.sdata=response.data[0].sdate;
            this.tableEdit.did=response.data[0].did;
            this.tableEdit.pid=response.data[0].pid == 6 ?'':response.data[0].pid;
            this.tableEdit.uid=response.data[0].user.uid;
            this.tableEdit.user.uname=response.data[0].user.uname;
            this.tableEdit.user.sex=response.data[0].user.sex+"";
            this.tableEdit.user.phone=response.data[0].user.phone;
            this.tableEdit.user.rname=response.data[0].user.roleList[0].rname;
            this.tableEdit.user.dname=response.data[0].user.department.dname;
          });


      },
      // 编辑表单的提交
      handleEditSubmit (tableEdit) {
        this.$refs[tableEdit].validate((valid) => {
          if (valid) {
            this.$axios.post('/api/manager/updateShift',{
              sid:parseInt(this.tableEdit.sid),
              did:parseInt(this.tableEdit.did),
              uid:this.tableEdit.uid,
              pid:parseInt(this.tableEdit.pid==''?6:this.tableEdit.pid)
            }).then(response=>{
              if(response.data){
                this.dialogFormEditVisible = false;
                this.$message({message: '修改成功！',type: 'success'});
                this.flush(0);
              }else{
                this.$message.error('修改失败！');
              }
            });
          } else {
            return false;
          }
        });
      },
      //重置
      refresh(){
        this.uname = '';
        this.did = '';
        this.sdate = '';
        this.loading = true;
        this.$axios.get("")
          .then((response)=>{
            if(response.data){
              this.$message({
                message: '数据请求失败',
                type: 'error'
              });
            }
          })
        this.flush(0);
      },
      //得到对应用户信息
      seluser(uid){
        this.$axios.get("/api/manager/getAllDutyInfo",{
          params:{
            uid:uid
          }
        }).then((response) =>{
          this.tableEdit.user.rname = response.data.data[3][0].roleList[0].rname
          this.tableEdit.user.dname = response.data.data[3][0].department.dname
          this.tableEdit.user.phone = response.data.data[3][0].phone
          this.tableEdit.user.sex = response.data.data[3][0].sex+""
          this.tableEdit.user.uid = response.data.data[3][0].uid
        })
      },
      //加载数据
      flush(sid){
        this.$axios.get("/api/manager/getAllDuty",{
          params:{
            sid:sid,
            uname: this.uname,
            sdate:this.sdate,
            did:this.did
          }
        }).then((response) => {             // mark
          var table = [];
          response.data.forEach(function (item) {
            var data = {
              sid: item.sid,
              sdate: item.sdate,
              dename:item.details.dname,
              uname:item.user.uname,
              rname:item.user.roleList[0].rname,
              sex:item.user.sex==1?"男":"女",
              dname:item.user.department.dname,
              phone:item.user.phone,
              pname:item.parti.pname == 0 ? '':item.parti.pname
            }
            table.push(data)
          });
          this.table = table;
          this.loading = false;
        }, response => {
          this.$message({message: '数据请求失败',type: 'error'});
        });
      }
    },
    created () {
      //获取表单数据+条件查询
      this.flush(0)
      this.$axios.get("/api/manager/getAllDutyInfo",{
        params:{
          uid:this.tableEdit.uid
        }
      }).then((response) =>{
        this.department = response.data.data[2]
        this.details = response.data.data[1]
        this.parti = response.data.data[0]
        this.user = response.data.data[3]
      })
    }
  };
</script>
<style scoped="scoped">
  .clearance{
    display: inline-block;
    width: 25px;
  }
  .filter-container{
    text-align: center;
    margin-bottom: 20px;
  }
  .pagination-container{
    width: 700px;
    text-align: center;
    margin: 30px auto;
  }
  .duty .title{
    display:block;
    text-align: center;
    font-size:24px;
    font-weight:600;
    line-height:80px;
  }


</style>
