<template>
  <div class="registerInfo">
    <div class="title">
      <p>挂号信息记录</p>
    </div>
    <div class="filter-container" style="text-align: center">
      <el-input style="width: 200px;" placeholder="挂号序号" v-model="rid"></el-input>
      <div class="clearance"></div>
      <el-input style="width: 200px;" placeholder="患者姓名" v-model="rname"></el-input>
      <div class="clearance"></div>
      <el-button type="primary" icon="el-icon-search" @click="flush">搜索</el-button>
      <el-button type="danger" icon="el-icon-refresh" @click="refresh">重置</el-button>
    </div>
    <el-table :data="tableData"  v-loading="loading" style="width: 100%;font-size: 14px">
    <el-table-column type="expand">
      <template slot-scope="props">
        <el-form label-position="left" inline class="demo-table-expand">
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
            <span>&nbsp;&nbsp;&nbsp;{{ props.row.dname }}</span>
          </el-form-item>
          <el-form-item label="身份证：">
            <span>&nbsp;&nbsp;&nbsp;{{ props.row.idcard }}</span>
          </el-form-item>
          <el-form-item label="医生：">
            <span>&nbsp;&nbsp;&nbsp;{{ props.row.uname }}</span>
          </el-form-item>
          <el-form-item label="状态：">
            <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{ props.row.rstatic }}</span>
          </el-form-item>
        </el-form>
      </template>
    </el-table-column>
    <el-table-column label="挂号序号" prop="rid"></el-table-column>
    <el-table-column label="患者姓名" prop="rname"></el-table-column>
    <el-table-column label="身份证号" prop="idcard"></el-table-column>
    <el-table-column label="挂号日期" prop="redate"></el-table-column>
      <el-table-column label="状态" prop="rstatic"></el-table-column>
      <el-table-column label="操作">
        <template scope="scope">
          <el-button size="small" type="primary" icon="el-icon-edit" circle @click="handleEdit(scope.row.rid)"></el-button>
          <el-button size="small" icon="el-icon-delete" circle type="danger" @click="handleDelete(scope.row.rid)"></el-button>
        </template>
      </el-table-column>
  </el-table>
    <!--修改模态框-->
    <el-dialog title="修改挂号信息" :visible.sync="dialogFormEditVisible" top="80px" width="33%">
      <el-form class="small-space" :model="tableEdit" :rules="rules" ref="tableEdit" label-position="left" label-width="70px" style='width: 400px; margin-left:50px;'>
        <el-form-item label="挂号序号" prop="rid">
          <el-input :disabled="true" style="width: 62%" v-model="tableEdit.rid"></el-input>
        </el-form-item>
        <el-form-item label="挂号时间" prop="redate">
          <el-input :disabled="true" style="width: 62%" v-model="tableEdit.redate"></el-input>
        </el-form-item>
        <el-form-item label="患者名" prop="rname">
          <el-input v-model="tableEdit.rname" style="width: 62%"></el-input>
        </el-form-item>
        <el-form-item label="身份证" prop="idcard">
          <el-input v-model="tableEdit.idcard" style="width: 62%"></el-input>
        </el-form-item>
        <el-form-item label="挂号费" prop="rmoney">
          <el-input v-model.number="tableEdit.rmoney" style="width: 62%"></el-input>
        </el-form-item>
        <el-form-item label="药方费" prop="rprice">
          <el-input :disabled="true" v-model="tableEdit.rprice" style="width: 62%"></el-input>
        </el-form-item>
        <el-form-item label="科室" prop="did">
          <el-select v-model="tableEdit.did" filterable placeholder="请选择科室" @change="depchange">
            <el-option v-for="item in department" :key="item.did" :label="item.dname" :value="item.did"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="医生" prop="uid">
          <el-select v-model="tableEdit.uid" filterable placeholder="请选择医生">
            <el-option v-for="item in user" :key="item.uid" :label="item.uname" :value="item.uid"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormEditVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleEditSubmit('tableEdit')">确 定</el-button>
      </div>
    </el-dialog>
    <!--删除值班信息-->
    <el-dialog title="删除挂号信息" :visible.sync="dialogDelVisible" top="290px" width="25%">
      <p style="margin: -20px 0;">是否删除该条挂号信息？</p>
      <div slot="footer" class="dialog-footer" style="margin-top: -20px">
        <el-button @click="quxiao">取 消</el-button>
        <el-button type="primary" @click="handelyes">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    data () {
      return {
        name:'RegisterInfo',
        dialogFormEditVisible:false,
        dialogDelVisible:false,
        rid:'',//编号
        delrid:'',
        loading:true,
        rname:'',//姓名
        tableData: [],
        department:[],
        user:[],
        tableEdit:{},
        rules: {
          rname: [
            { required: true, message: '请输入姓名', trigger: 'blur' }
          ],
          idcard: [
            { required: true, message: '请输入身份证号', trigger: 'blur' },
            {pattern: /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/, message: '请输入正确的身份证', trigger: 'blur'}
          ],
          rmoney:[
            { required: true, message: '请输入年龄', trigger: 'blur' },
            {type: 'number', message: '必须为数字值', trigger: 'blur'},
          ]
        }//校验规则
      };
    },
    methods: {
      handleEdit(rid){
        this.dialogFormEditVisible = true
        this.$axios.get("/api/cashier/getAllInfo",{params:{did:0}}).then((response) =>{
          this.department = response.data.data[0]
          this.user = response.data.data[1]
        })
        this.$axios.get("/api/cashier/getRegister",{params:{rid:rid}}).then((response)=>{
          this.tableEdit = response.data
          this.depchange(this.tableEdit.did)
        })
      },
      // 编辑表单的提交
      handleEditSubmit (tableEdit) {
        this.$refs[tableEdit].validate((valid) => {
          if (valid) {
            this.loading = true;
            this.$axios.post('/api/cashier/updateRegister',{
              rid:this.tableEdit.rid,
              redate:this.tableEdit.redate,
              rname:this.tableEdit.rname,
              idcard:this.tableEdit.idcard,
              rmoney:this.tableEdit.rmoney,
              rprice:this.tableEdit.rprice,
              did:this.tableEdit.did,
              uid:this.tableEdit.uid,
              rstatic:this.tableEdit.rstatic
            }).then(response=>{
              if(response.data){
                this.$message({message: '挂号信息修改成功！',type: 'success'});
                this.flush();
              }else{
                this.$message.error('挂号信息修改失败！');
              }
              this.dialogFormEditVisible = false;
            });
          } else {
            return false;
          }
        });
      },
      quxiao(){
        this.dialogDelVisible = false;
        this.$message('已取消操作');
      },
      handelyes(){
        this.$axios.get("/api/cashier/deleteRegister",{params:{rid:parseInt(this.delrid)}})
          .then((response)=>{
            if(response.data){
              this.$message({message: '删除成功！',type: 'success'});
              this.flush();
            }else{
              this.$message('删除失败');
            }
            this.dialogDelVisible = false
          })
      },
      depchange(did){
        this.$axios.get("/api/cashier/getAllInfo",{
          params:{
            did:did
          }
        }).then((response) =>{
          this.user = response.data.data[1]
          this.tableEdit.uid = this.user[0].uid
        })
      },
      handleDelete(rid){
        this.dialogDelVisible = true;
        this.delrid = rid;
      },
      refresh(){
        this.rid = ''
        this.rname = ''
        this.flush()
      },
      flush(){
        this.loading = true;
        this.$axios.get('/api/cashier/getAllRegister',{
          params:{
            rid:this.rid,
            rname:this.rname
          }
        }).then((response) => {             // mark
          var td =[],th = this;
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
            td.push(register)
          })
          this.tableData = td
          this.loading = false
        }, response => {
          this.$message({message: '数据请求失败',type: 'error'});
          this.loading = false
        });
      },
    },
    created () {
      this.flush();
    }
  };
</script>

<style scoped="scoped">
  .registerInfo .title p{
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
