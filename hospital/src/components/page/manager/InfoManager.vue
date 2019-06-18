<template>
  <div class="infoManager">
    <div class="title">
      <p>医院人员信息记录</p>
    </div>
    <div class="filter-container" style="text-align: center">
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="姓名" v-model="uname"></el-input>
      <div class="clearance"></div>
      <el-select v-model="did" @change="seldid" filterable clearable placeholder="请选择科室">
        <el-option v-for="item in department1" :key="item.did" :label="item.dname" :value="item.did"></el-option>
      </el-select>
      <div class="clearance"></div>
      <el-button class="filter-item" type="warning" icon="el-icon-search" @click="chaxun">搜索</el-button>
      <el-button class="filter-item" type="primary" @click="dialogFormVisible = true"  icon="el-icon-edit">添加</el-button>
      <!--<el-button class="filter-item" type="danger" @click="handleDelAll"  icon="el-icon-delete">全部删除</el-button>-->
    </div>
    <el-table v-loading="loading" ref="multipleTable" :data="table" border tooltip-effect="dark" stripe :header-cell-style="getRowClass" style="width: 100%;font-size: 14px" @selection-change="handleSelectionChange">>
      <el-table-column align="center" type="selection" width="40"></el-table-column>
      <el-table-column align="center" label="工号" width="70" prop="uid">
        <template scope="scope">{{ scope.row.uid }}</template>
      </el-table-column>
      <el-table-column align="center" prop="uname" label="姓名"  width="80"></el-table-column>
      <el-table-column align="center" prop="address" label="地址" show-overflow-tooltip></el-table-column>
      <el-table-column align="center" prop="sex" label="性别" show-overflow-tooltip width="50"></el-table-column>
      <el-table-column prop="age" label="年龄" sortable align="center" show-overflow-tooltip width="80"></el-table-column>
      <el-table-column align="center" prop="rname" label="职称" show-overflow-tooltip width="80"></el-table-column>
      <el-table-column align="center" prop="dname" label="科室" show-overflow-tooltip width="100"></el-table-column>
      <el-table-column align="center" prop="ename" label="学历" show-overflow-tooltip></el-table-column>
      <el-table-column align="center" prop="detials" label="专业" show-overflow-tooltip></el-table-column>
      <el-table-column align="center" prop="joindate" label="入职时间" sortable show-overflow-tooltip></el-table-column>
      <el-table-column align="center" label="操作">
        <template scope="scope">
          <el-button :type="scope.row.type" :icon="scope.row.icon" @click="enable(scope.row.uid,scope.row.state)" circle></el-button>
          <el-button size="small" type="primary" icon="el-icon-edit" circle @click="handleEdit(scope.row.uid)"></el-button>
          <el-button size="small" icon="el-icon-document-delete" circle type="danger" @click="handleDelete(scope.row.uid)"></el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination-container">
      <el-pagination @size-change="handleSizeChange"
                     @current-change="handleCurrentChange" :current-page="current" :page-sizes="[5,10,15,20]" :page-size="size" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>
    <!-- 添加信息功能的表单 -->
    <el-dialog title="添加信息" :visible.sync="dialogFormVisible" top="20px" width="40%">
      <el-form class="demo-ruleForm" :model="form" ref="form" label-position="left" :rules="rules" label-width="70px" style='width: 400px; margin:-15px 0 -30px 50px;'>
        <el-form-item label="姓名" prop="uname">
          <el-input v-model="form.uname" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item label="用户名" prop="acount">
          <el-input v-model="form.acount" @blur="checkAcount(form.acount,'')" placeholder="用户名4-10位"></el-input>
        </el-form-item>
        <el-form-item label="身份证" prop="idcard">
          <el-input v-model="form.idcard" @blur="checkID(form.idcard)" placeholder="身份证"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model.number="form.phone" @blur="checkPhone(form.phone,'')" placeholder="手机号"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address" placeholder="地址"></el-input>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model.number="form.age" :disabled="true" placeholder="年龄"></el-input>
        </el-form-item>
        <el-form-item label="专业" prop="detials">
          <el-input v-model="form.detials" placeholder="专业"></el-input>
        </el-form-item>
        <el-form-item label="职称" prop="rid">
          <el-select v-model="form.rid" clearable placeholder="请选择职称">
            <el-option v-for="item in role" :key="item.rid" :label="item.rname" :value="item.rid"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="科室" prop="did">
          <el-select v-model="form.did" filterable clearable placeholder="请选择科室">
            <el-option v-for="item in department2" :key="item.did" :label="item.dname" :value="item.did"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学历" prop="eid">
          <el-select v-model="form.eid" filterable clearable placeholder="请选择学历">
            <el-option v-for="item in education" :key="item.eid" :label="item.ename" :value="item.eid"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="form.sex">
            <el-radio label="1">男</el-radio>
            <el-radio label="2">女</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handelNo">取 消</el-button>
        <el-button type="primary" @click="submitForm('form')">确 定</el-button>
        <el-button type="primary" @click="resetForm">重置</el-button>
      </div>
    </el-dialog>
    <!-- 编辑信息的表单 -->
    <el-dialog title="修改个人信息" :visible.sync="dialogFormEditVisible" top="20px" width="40%">
      <el-form class="small-space" :model="tableEdit" :rules="rules" ref="tableEdit" label-position="left" label-width="70px" style='width: 400px; margin-left:50px;'>
        <el-form-item label="工号" prop="uid">
          <el-input :disabled="true" v-model="tableEdit.uid"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="uname">
          <el-input v-model="tableEdit.uname"></el-input>
        </el-form-item>
        <el-form-item label="用户名" prop="acount">
          <el-input v-model="tableEdit.acount" @blur="checkAcount(tableEdit.acount,tableEdit.uid)"></el-input>
        </el-form-item>
        <el-form-item label="身份证" prop="idcard">
          <el-input :disabled="true" v-model="tableEdit.idcard"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model.number="tableEdit.phone" @blur="checkPhone(tableEdit.phone,tableEdit.uid)"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="tableEdit.address"></el-input>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model.number="tableEdit.age"></el-input>
        </el-form-item>
        <el-form-item label="专业" prop="detials">
          <el-input v-model="tableEdit.detials"></el-input>
        </el-form-item>
        <el-form-item label="职称" prop="rid">
          <el-select v-model="tableEdit.rid" clearable placeholder="请选择职称">
            <el-option v-for="item in role" :key="item.rid" :label="item.rname" :value="item.rid"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="科室" prop="did">
          <el-select v-model="tableEdit.did" filterable clearable placeholder="请选择科室">
            <el-option v-for="item in department2" :key="item.did" :label="item.dname" :value="item.did"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学历" prop="eid">
          <el-select v-model="tableEdit.eid" filterable clearable placeholder="请选择学历">
            <el-option v-for="item in education" :key="item.eid" :label="item.ename" :value="item.eid"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="tableEdit.sex">
            <el-radio label="1">男</el-radio>
            <el-radio label="2">女</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormEditVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleEditSubmit('tableEdit')">确 定</el-button>
      </div>
    </el-dialog>
    <!--办理离职-->
    <el-dialog title="办理离职" :visible.sync="dialogleave" top="255px" width="27%">
      <p style="margin-top: -25px">请输入离职原因：</p>
      <el-input v-model="details" style="margin-top: 20px"></el-input>
      <div slot="footer" class="dialog-footer" style="margin-top: -10px">
        <el-button @click="closeleave">取 消</el-button>
        <el-button type="primary" @click="leave">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>
<script type="text/ecmascript-6">
  export default {
    data () {
      return {
        name:'infoManager',
        loading:true,
        //表格顶部颜色条
        getRowClass ({row, column, rowIndex, columnIndex}) {
          if (rowIndex === 0) {
            return 'background: rgb(233, 233, 235)'
          } else {
            return ''
          }
        },
        table: [],//展示表数据
        size:5,//每页条数
        total: 0,//总条数
        current:1,//当前页数
        did: '',//科室
        uid:'',//离职用户id
        details:'',//离职原因
        uname: '',//姓名
        tableEdit: {
          uid:'',
          uname: '',
          acount:'',
          address: '',
          idcard:'',
          phone:'',
          age: '',
          rid: '',
          did: '',//科室
          eid: '',//学历
          detials: '',
          sex: '',
        },//修改表数据
        dialogFormVisible: false,//添加模态框显示状态
        dialogFormEditVisible: false,//修改模态框显示状态
        dialogleave:false,//离职模态框
        form: {
          uname: '',
          acount:'',
          address: '',
          idcard:'',
          phone:'',
          age: '',
          rid: '',
          did: '',//科室
          eid: '',//学历
          detials: '',
          sex: ''
        },//添加表数据
        rules: {
          uname: [
            { required: true, message: '请输入姓名', trigger: 'blur' }
          ],
          acount: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            {pattern: /^[A-Za-z0-9]{4,10}$/, message: '请输入4-10位字母', trigger: 'blur'}
          ],
          idcard: [
            { required: true, message: '请输入身份证号', trigger: 'blur' },
            {pattern: /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/, message: '请输入正确的身份证', trigger: 'blur'}
          ],
          phone: [
            { required: true, message: '请输入手机号', trigger: 'blur' },
            {pattern: /^1[34578]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur'}
          ],
          address:[
            { required: true, message: '请输入地址', trigger: 'blur' }
          ],
          detials:[
            { required: true, message: '请输入专业', trigger: 'blur' }
          ],
          rid: [
            { required: true, message: '请选择职称', trigger: 'change' }
          ],
          did: [
            { required: true, message: '请选择科室', trigger: 'change' }
          ],
          eid: [
            { required: true, message: '请选择学历', trigger: 'change' }
          ],
          sex: [
            { required: true, message: '请选择性别', trigger: 'change' }
          ],
          age:[
            { required: true, message: '请输入年龄', trigger: 'blur' },
            {type: 'number', message: '必须为数字值', trigger: 'blur'},
            {pattern: /^(1[89]|[2-8][0-9]|90)$/, message: '年龄在18-90之间', trigger: 'blur'}
          ]
        },//校验规则
        multipleSelection: [],//选中行数据
        //顶部科室选择
        department1: [],//科室1
        department2: [],//科室2
        education:[],//学历
        role:[],//职称
      };
    },
    methods: {
      //选中触发事件
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      //检验身份证
      checkID(val){
        this.$axios.get("/api/manager/checkID",{params:{idcard:val}})
          .then(response =>{
            if(response.data){
              this.$message.error('身份证重复,请重新输入');
              this.form.idcard = ''
            }else {
              var card = this.form.idcard;
              if(card.length == 18){
                var re_eighteen = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X|x)$/;
                var arr_data = card.match(re_eighteen);
                var year = arr_data[2];
                var month = arr_data[3];
                var day = arr_data[4];
                var str = year+"-"+month+"-"+day;
                var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
                var d = new Date(r[1],r[3]-1,r[4]);
                if(d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]){
                  var y = new Date().getFullYear();
                  this.form.age = y-r[1]
                }
              }
            }
        })
      },
      //检验用户名
      checkAcount(val,uid){
        this.$axios.get("/api/manager/checkAcount",{params:{acount:val,uid:uid}})
          .then(response =>{
            if(response.data){
              this.$message.error('此用户名已存在,请重新输入');
              this.form.acount = ''
              this.tableEdit.acount = ''
            }
          })
      },
      //检验手机号
      checkPhone(val,uid){
        this.$axios.get("/api/manager/checkPhone",{params:{phone:val,uid:uid}})
          .then(response =>{
            if(response.data){
              this.$message.error('手机号已存在,请重新输入');
              this.form.phone = ''
              alert()
              this.tableEdit.phone = ''
            }
          })
      },
      // 编辑
      handleEdit (uid) {
        this.dialogFormEditVisible = true;
        this.$axios.get("/api/manager/getAllUser",{
          params:{
            uid:uid,
            current:1,
            size:1,
            uname:'',
            did:0
          }})
          .then((response) => {
              this.tableEdit.uid=response.data.rows[0].uid;
              this.tableEdit.uname=response.data.rows[0].uname;
              this.tableEdit.acount=response.data.rows[0].acount;
              this.tableEdit.address=response.data.rows[0].address;
              this.tableEdit.idcard=response.data.rows[0].idcard;
              this.tableEdit.phone=response.data.rows[0].phone;
              this.tableEdit.age=response.data.rows[0].age;
              this.tableEdit.detials=response.data.rows[0].detials;
              this.tableEdit.sex=response.data.rows[0].sex+"";
              this.tableEdit.rid=response.data.rows[0].roleList[0].rid;
              this.tableEdit.did=response.data.rows[0].did;
              this.tableEdit.eid=response.data.rows[0].eid;
          });
      },
      // 编辑表单的提交
      handleEditSubmit (tableEdit) {
        this.$refs[tableEdit].validate((valid) => {
          if (valid) {
            this.loading = true;
            this.$axios.post('/api/manager/updateUser',{
              uid: this.tableEdit.uid,
              uname:this.tableEdit.uname,
              acount:this.tableEdit.acount,
              address:this.tableEdit.address,
              idcard:this.tableEdit.idcard,
              phone:this.tableEdit.phone,
              age:this.tableEdit.age,
              rid:this.tableEdit.rid,
              did:this.tableEdit.did,
              eid:this.tableEdit.eid,
              detials:this.tableEdit.detials,
              sex:this.tableEdit.sex
            }).then(response=>{
              if(response.data){
                this.dialogFormEditVisible = false;
                this.$message({message: '个人信息修改成功！',type: 'success'});
                this.flush(0);
              }else{
                this.$message.error('个人信息修改失败！');
              }

            });
          } else {
            return false;
          }
        });
      },
      // 离职按钮
      handleDelete (index) {
        this.dialogleave = true;
        this.uid = index;
      },
      //确定离职
      leave(){
        var reg = /\D{1,}/
        if(reg.test(this.details)){
          this.$axios.get("/api/manager/leave",{params:{uid:this.uid,details:this.details}})
            .then(response=>{
              if(response.data){
                this.$message({type: 'success',message: '办理成功！！'});
                this.dialogleave = false
                this.loading = true;
                this.flush(0)
              }else{
                this.$message({type: 'success',message: '办理失败！！'});
                this.dialogleave = false
              }
            })
        }else{
          this.$message.error('离职原因不能为空');
        }
      },
      //关闭离职模块
      closeleave(){
        this.dialogleave = false
        this.details = ''
      },
      // 全部删除
      handleDelAll () {
        this.$confirm('此操作将删除所有用户, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'info'
        }).then(() => {
          var data = this.multipleSelection;
          var uids = "";
          data.forEach(function (item,index) {
            if(index == data.length-1){
              uids = uids + item.uid;
            }else{
              uids = uids + item.uid + ";";
            }
          })
          this.$axios.get('/api/manager/deleteAllUser',{params:{uids:uids}}).then(response=>{
            if(response.data){
              this.$message({type: 'success',message: '删除成功!'});
              this.loading = true;
              this.flush(0)
            }else{
              this.$message.error('删除失败!');
            }
          });
        }).catch(() => {
          this.$message({type: 'info',message: '已取消删除'});
        });
      },
      //取消
      handelNo () {
        this.dialogFormVisible = false;
        this.$refs['form'].resetFields();
      },
      //查询
      chaxun(){
        this.loading = true
        this.flush(0)
      },
      //总条数切换
      handleSizeChange (val) {
        this.loading = true;
        this.size = val;
        this.current = 1;
        this.flush(0);
      },
      //科室查询
      seldid(){
        this.loading = true;
        this.current = 1;
        this.flush(0);
      },
      //当前页切换
      handleCurrentChange (val) {
        this.loading = true;
        this.current = val;
        this.flush(0);
      },
      //添加用户
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$axios.post('/api/manager/insertUser',{
              uname:this.form.uname,
              acount:this.form.acount,
              address:this.form.address,
              idcard:this.form.idcard,
              phone:this.form.phone,
              age:this.form.age,
              rid:this.form.rid,
              did:this.form.did,
              eid:this.form.eid,
              detials:this.form.detials,
              sex:this.form.sex
            }).then(response=>{
              if(response.data){
                this.dialogFormVisible = false;
                this.loading = true;
                this.$message({message: '个人信息添加成功！',type: 'success'});
                this.$refs['form'].resetFields();
                this.flush(0);
              }else{
                this.$message.error('个人信息添加失败！');
              }
            });
          } else {
            return false;
          }
        });
      },
      // 重置
      resetForm () {
        this.$refs['form'].resetFields();
      },
      //是否启用
      enable(uid,state){
        if(state == 1){
          this.$confirm('是否禁止该用户登录后台?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'})
            .then(() => {
              this.$axios.get("/api/manager/updateUserState",{
                params:{uid:uid,state:2}
              }).then((response)=>{
                if(response.data){
                  this.loading = true;
                  this.$message({type: 'success',message: '禁用成功!'});
                  this.flush(0);
                }else{
                  this.$message({type: 'info',message: '禁用失败'});
                }
              })
            })
              .catch(() => {this.$message({type: 'info',message: '禁用失败'});})
        }else if(state == 2){
          this.$confirm('是否启用该用户登录后台?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'})
            .then(() => {
              this.$axios.get("/api/manager/updateUserState",{
                params:{uid:uid,state:1}
              }).then((response)=>{
                if(response.data){
                  this.loading = true;
                  this.$message({type: 'success',message: '启用成功!'});
                  this.flush(0);
                }else{
                  this.$message({type: 'info',message: '启用失败'});
                }
              })
            })
            .catch(() => {this.$message({type: 'info',message: '启用失败'});});
        }
      },
      //加载数据
      flush(uid){
        this.$axios.get("/api/manager/getAllUser",{params:{
            uid:uid,
            current:this.current,
            size:this.size,
            uname:this.uname,
            did:this.did
          }}).then((response) => {             // mark
          var table = [];
          response.data.rows.forEach(function (item) {
            var data = {
              uid:item.uid,
              uname:item.uname,
              address:item.address,
              age:item.age,
              rname: item.roleList[0].rname,
              dname: item.department.dname,
              ename: item.education.ename,
              detials: item.detials,
              state:item.state,
              type:item.state == 1?'success':'danger',
              icon:item.state == 1?'el-icon-check':'el-icon-close',
              sex: item.sex==1?"男":"女",
              joindate: item.joindate
            }
            let date = new Date(data.joindate);
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
            data.joindate=y + '-' + MM + '-' + d + ' ' + h + ':' + m + ':' + s;
            table.push(data)
          });
          this.table = table;
          this.loading = false;
          this.total = response.data.total
        }, response => {
          this.$message({
            message: '数据请求失败',
            type: 'error'
          });
          this.loading = false;
        });
      }
    },
    created () {
      //获取表单数据+分页+条件查询
      this.flush(0)
      //获取科室信息
      this.$axios.get("/api/manager/getAllInfo").then((response) =>{
        this.department1 = response.data.data[0]
        this.department2 = response.data.data[0]
        this.education = response.data.data[2]
        this.role = response.data.data[1]
      })
    }
  };
</script>

<style>
  .clearance{
    display: inline-block;
    width: 25px;
  }
  .infoManager .title p{
    font-size:24px;
    text-align:center;
    margin:25px 0;
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
