<template>
  <div class="infoManager">
    <div class="filter-container">
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="姓名" v-model="uname"></el-input>
      <div class="clearance"></div>
      <el-select v-model="did" @change="seldid" filterable clearable placeholder="请选择科室">
        <el-option v-for="item in department1" :key="item.did" :label="item.dname" :value="item.did"></el-option>
      </el-select>
      <div class="clearance"></div>
      <el-button class="filter-item" type="primary" icon="search" @click="flush">搜索</el-button>
      <el-button class="filter-item" type="primary" @click="dialogFormVisible = true"  icon="edit">添加</el-button>
      <el-button class="filter-item" type="primary" @click="handleDelAll"  icon="edit">全部删除</el-button>
    </div>
    <el-table ref="multipleTable" :data="table" border tooltip-effect="dark" stripe :header-cell-style="getRowClass" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column label="工号" width="70" prop="uid">
        <template scope="scope">{{ scope.row.uid }}</template>
      </el-table-column>
      <el-table-column prop="uname" label="姓名"  width="70"></el-table-column>
      <el-table-column prop="address" label="地址" show-overflow-tooltip></el-table-column>
      <el-table-column prop="sex" label="性别" show-overflow-tooltip width="50"></el-table-column>
      <el-table-column prop="age" label="年龄" sortable align="center" show-overflow-tooltip width="70"></el-table-column>
      <el-table-column prop="rname" label="职称" show-overflow-tooltip width="80"></el-table-column>
      <el-table-column prop="dname" label="科室" show-overflow-tooltip width="100"></el-table-column>
      <el-table-column prop="ename" label="学历" show-overflow-tooltip></el-table-column>
      <el-table-column prop="detials" label="专业" show-overflow-tooltip></el-table-column>
      <el-table-column prop="joindate" label="入职时间" sortable show-overflow-tooltip></el-table-column>
      <el-table-column align="center" label="操作">
        <template scope="scope">
          <el-button size="small" @click="handleEdit(scope.row.uid, scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.row.uid, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination-container">
      <el-pagination @size-change="handleSizeChange"
                     @current-change="handleCurrentChange" :current-page="current"  :page-sizes="[5,10,15,20]" :page-size="size" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>
    <!-- 添加信息功能的表单 -->
    <el-dialog title="添加信息" :visible.sync="dialogFormVisible" top="20px" width="40%">
      <el-form class="demo-ruleForm" :model="form" ref="form" label-position="left" :rules="rules" label-width="70px" style='width: 400px; margin:-15px 0 -30px 50px;'>
        <el-form-item label="姓名" prop="uname">
          <el-input v-model="form.uname"></el-input>
        </el-form-item>
        <el-form-item label="用户名" prop="acount">
          <el-input v-model="form.acount"></el-input>
        </el-form-item>
        <el-form-item label="身份证" prop="idcard">
          <el-input v-model="form.idcard"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model.number="form.phone"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address"></el-input>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model.number="form.age"></el-input>
        </el-form-item>
        <el-form-item label="专业" prop="detials">
          <el-input v-model="form.detials"></el-input>
        </el-form-item>
        <el-form-item label="职称" prop="rid">
          <el-select v-model="form.rid" @change="flush" filterable clearable placeholder="请选择职称">
            <el-option v-for="item in role" :key="item.rid" :label="item.rname" :value="item.rid"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="科室" prop="did">
          <el-select v-model="form.did" @change="flush" filterable clearable placeholder="请选择科室">
            <el-option v-for="item in department2" :key="item.did" :label="item.dname" :value="item.did"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学历" prop="eid">
          <el-select v-model="form.eid" @change="flush" filterable clearable placeholder="请选择学历">
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
    <el-dialog title="修改个人信息" :visible.sync="dialogFormEditVisible">
      <el-form class="small-space" :model="form" label-position="left" label-width="70px" style='width: 400px; margin-left:50px;'>
        <el-form-item label="姓名">
          <el-input v-model="tableEdit.uname"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="tableEdit.address"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="tableEdit.age"></el-input>
        </el-form-item>
        <el-form-item label="职称">
          <el-input v-model="tableEdit.rname"></el-input>
        </el-form-item>
        <el-form-item label="专业">
          <el-input v-model="tableEdit.detials"></el-input>
        </el-form-item>
        <el-form-item label="科室">
          <el-input v-model="tableEdit.dname"></el-input>
        </el-form-item>
        <el-form-item label="学历">
          <el-input v-model="tableEdit.ename"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="tableEdit.sex">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="入职时间">
          <el-col :span="11">
            <el-form-item prop="date">
              <el-date-picker type="date" placeholder="选择日期" v-model="tableEdit.joindate"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormEditVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleEditSubmit">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>
<script type="text/ecmascript-6">
  export default {
    data () {
      return {
        //表格顶部颜色条
        getRowClass ({row, column, rowIndex, columnIndex}) {
          if (rowIndex === 0) {
            return 'background: rgb(233, 233, 235)'
          } else {
            return ''
          }
        },
        table: [],//表数据
        size:5,//每页条数
        total: 0,//总条数
        current:1,//当前页数
        did: '',//科室
        uname: '',//姓名
        tableEdit: [],
        dialogFormVisible: false,
        dialogFormEditVisible: false,
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
        },
        rules: {
          uname: [
            { required: true, message: '请输入姓名', trigger: 'blur' }
          ],
          acount: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            {pattern: /^[A-Za-z]{4,10}$/, message: '请输入4-10位字母', trigger: 'blur'}
          ],
          idcard: [
            { required: true, message: '请输入身份证号', trigger: 'blur' },
            {pattern: /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/, message: '请输入正确的身份证', trigger: 'blur'}
          ],
          phone: [
            { required: true, message: '请输入手机号', trigger: 'blur' },
            {type: 'number', message: '必须为数字值', trigger: 'blur'},
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
        },
        mulipleSelection: [],
        //顶部科室选择
        department1: [],
        department2: [],
        education:[],//学历
        role:[],//职称
      };
    },
    methods: {
      toggleSelection (rows) {
        if (rows) {
          rows.forEach(row => {
            this.$refs.multipleTable.toggleRowSelection(row);
          });
        } else {
          this.$refs.multipleTable.clearSelection();
        }
      },
      handleSelectionChange (val) {
        this.multipleSelection = val;
      },
      // 编辑
      handleEdit (index, row) {
        alert(index)
        this.dialogFormEditVisible = true;
        // this.Index = index;
        // this.tableEdit = this.table[index];
        // this.tableEdit = JSON.parse(JSON.Stringify(this.index));
        this.tableEdit = row;
        // this.tableEdit = JSON.parse(JSON.Stringify(this.tableEdit.row));
      },
      // 编辑表单的提交
      handleEditSubmit (index) {
        this.dialogFormEditVisible = false;
        let oldDate = this.tableEdit.date;
        let year = new Date(oldDate).getFullYear();
        let month = new Date(oldDate).getMonth() + 1;
        let day = new Date(oldDate).getDate();
        let dateFormat = year + '-' + month + '-' + day;
        this.tableEdit.date = dateFormat;
        this.table[index] = this.tableEdit;
        this.$message({
          message: '个人信息修改成功！',
          type: 'success'
        });
      },
      // 单个删除
      handleDelete (index, row) {
        let vm = this;
        console.log('单个删除选择的row: ', index, '-----', row);
        vm.table.splice(index, 1);
        this.$message({
          message: '删除成功！',
          type: 'success'
        });
      },
      // 全部删除
      handleDelAll () {
        this.table = [];
      },
      //取消
      handelNo () {
        this.dialogFormVisible = false;
        this.$refs['form'].resetFields();
      },
      //总条数切换
      handleSizeChange (val) {
        this.size = val;
        this.current = 1;
        this.flush();
      },
      //科室查询
      seldid(){
        this.current = 1;
        this.flush();
      },
      //当前页切换
      handleCurrentChange (val) {
        this.current = val;
        this.flush();
      },
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
              console.log(response)
              this.dialogFormVisible = false;
              this.$message({
                message: '个人信息添加成功！',
                type: 'success'
              });
              this.$refs['form'].resetFields();
              this.flush();
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
      //加载数据
      flush(){
        this.$axios.get("/api/manager/getAllUser",{params:{
            current:this.current,
            size:this.size,
            uname:this.uname,
            did:this.did
          }}).then((response) => {             // mark
          var table = [];
          response.data.rows.forEach(function (item, index) {
            // console.log(response)
            var data = {
              uid:item.uid,
              uname:item.uname,
              address:item.address,
              age:item.age,
              rname: item.roleList[0].rname,
              dname: item.department.dname,
              ename: item.education.ename,
              detials: item.detials,
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
          console.log();
          this.table = table;
          this.total = response.data.total
        }, response => {
          this.$message({
            message: '数据请求失败',
            type: 'error'
          });
        });
      }
    },
    created () {
      //获取表单数据+分页+条件查询
      this.flush()
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
  .infoManager{
    padding: 10px 10px;
    margin-top: 20px;
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
