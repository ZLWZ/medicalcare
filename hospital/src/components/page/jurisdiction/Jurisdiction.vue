<template>
  <!--角色管理-->
  <div class="nurse_table">
    <div class="title">
      <p>角色管理</p>
    </div>
    <div class="filter-container " >
      <el-row :gutter="20" >
        <el-col :span="4.5" :offset="4"><el-input style="width: 200px;" class="filter-item" placeholder="名称" v-model="rname">
        </el-input></el-col>
        <el-col :span="4.5" >
          <el-date-picker v-model="startTime" type="datetime" placeholder="开始时间" align="right" :picker-options="startTimeOptions"></el-date-picker>
        </el-col>
        <el-col :span="4.5" >
            <el-date-picker v-model="stopTime" type="datetime" placeholder="停止时间" align="right" :picker-options="stopTimeOpetions"></el-date-picker>
        </el-col>
        <el-col :span="1.5">
          <el-button type="primary" class="filter-item" icon="el-icon-search" @click="handelSearch">搜索</el-button>
        </el-col>
        <el-col :span="1.5" >
          <el-button class="filter-item" type="primary" @click="createRoles"  icon="edit" >添加</el-button>
        </el-col>
        <!--<el-col :span="1.5" >-->
          <!--<el-button class="filter-item" type="primary" @click="handleDelAll"  icon="edit">全部删除</el-button>-->
        <!--</el-col>-->
      </el-row>
    </div>

    <el-table ref="multipleTable" :data="table" border tooltip-effect="dark"  style="width: 100%" v-loading="loading" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column label="序号"  width="50" type="index">
        <template scope="scope">{{ scope.row.rid }}</template>
      </el-table-column>
      <el-table-column prop="rname" label="角色名称"  width="120"></el-table-column>
      <el-table-column prop="describe" label="描述" show-overflow-tooltip></el-table-column>
      <el-table-column prop="creationTime" label="创建时间" show-overflow-tooltip ></el-table-column>
      <el-table-column prop="changeTime" label="修改时间" sortable align="center" show-overflow-tooltip ></el-table-column>
      <el-table-column align="center" label="操作">
        <template scope="scope">
          <el-button type="primary" icon="el-icon-edit" circle @click="handleEdit(scope.row.rid, scope.row)"></el-button>
          <el-button type="danger" icon="el-icon-delete" circle @click="handleDelete(scope.row.rid, scope.row)"></el-button>
          <el-button icon="el-icon-search" circle @click="selectRoles(scope.row.rid)"></el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-row :gutter="20"  type="flex"  justify="space-around">
      <el-col :span="4.5" > <div class="pagination-container">
        <el-pagination
          @current-change="handleCurrentChange "
          background
          layout="total,prev, pager, next,jumper"
          :current-page.sync= "currentPage"
          :page-size="pageSize"
          :total="total">
        </el-pagination>
      </div></el-col>
    </el-row>
    <!---//分页功能--->

    <!--查看角色信息-->
    <Drawer title="查看用户信息" :closable="false" width="420" v-model="select">
      <sel-roles ref="selroles"></sel-roles>
    </Drawer>
    <!--添加角色信息-->
    <Drawer title="添加角色" v-model="create" width="500" :mask-closable="false" :styles="styles">
      <add-roles ref="addroles" v-on:addDataRole="addShowDataRole"></add-roles>
      <div class="demo-drawer-footer" >
        <Button style="margin-right: 8px" @click="resetForm()">取消</Button>
        <Button type="primary" @click="getCheckedNodes()">提交</Button>
      </div>
    </Drawer>

    <Drawer title="修改角色信息" v-model="updateRoleState" width="500" :mask-closable="false" :styles="styles">
      <update-roles ref="UpdateRoles" v-on:updateDataRole="updateShowDataRole"></update-roles>
      <div class="demo-drawer-footer">
        <Button style="margin-right: 8px" @click="updateRoleState = false">取消</Button>
        <Button type="primary" @click="submitUpdateRole()">提交</Button>
      </div>
    </Drawer>
  </div>
</template>

<script type="text/ecmascript-6">
  import AddRoles from './AddRoles'
  import SelRoles from './SelRoles'
  import UpdateRoles from './UpdateRoles'
  export default {
    name:'Jurisdiction',
    components:{AddRoles,SelRoles,UpdateRoles},
    data () {
      return {
        table: [],
        tableEdit: [],
        searchList: [],
        rname: '',
        searchDepartment: '',
        total: 0,
        currentPage:1,
        pageSize : 5,
        listLoading: true,
        dialogFormVisible: false,
        dialogFormEditVisible: false,
        multipleSelection: [],
        //设置开始时间
        startTime:"",
        startTimeOptions: {
          shortcuts: [{
            text: '今天',
            onClick(picker) {
              picker.$emit('pick', new Date());
            }
          },
            {
            text: '昨天',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit('pick', date);
            }
          }, {
            text: '一周前',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', date);
            }
          }]
        },
        //设置停止时间
        stopTime:"",
        stopTimeOpetions: {
          shortcuts: [{
            text: '今天',
            onClick(picker) {
              picker.$emit('pick', new Date());
            }
          }, {
            text: '昨天',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit('pick', date);
            }
          }, {
            text: '一周前',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', date);
            }
          }]
        },
        //设置加载动画
        loading:true,
        //创建角色信息
        create:false,
        styles: {
          height: 'calc(100% - 55px)',
          overflow: 'auto',
          paddingBottom: '53px',
          position: 'static'
        },
        //查看角色信息
        select:false,
        //修改角色
        updateRoleState:false
      };
    },
    methods: {
      handleSelectionChange (val) {
        this.multipleSelection = val;
      },
      // 单个删除
      handleDelete (rid, index) {
        this.loading = true;
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$axios.delete("/api/jurisdiction/deleteRole/"+rid).then((data) => {
            if(data.data.code == 10000){
              if(this.table.length == 1){
                this.handleCurrentChange(this.currentPage - 1)
              }else{
                if(this.currentPage == Math.ceil(this.total / this.pageSize)){
                  for(let i = 0 ; i < this.table.length ; i++){
                    if(this.table[i].rid === rid ){
                      this.table.splice(i,1)
                    }
                  }
                }else{
                  this.handleCurrentChange(this.currentPage)
                }
              }
              this.$message({
                type: 'success',
                message: data.data.data
              });
            }else if(data.data.code == 10001){
              this.$message({
                type: 'error',
                message: data.data.data
              });
            }
            this.loading = false;
          },data => {
            this.$message({
              type: 'error',
              message: '删除失败!'
            });
            this.loading = false;
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
          this.loading = false;
        });

      },
      // 全部删除
      handleDelAll () {
        this.table = [];
      },
      // 搜索
      handelSearch () {
        this.$axios.get("api/jurisdiction/selAllRole",{
          params:{
            rname:this.rname,
            startTime:this.startTime,
            endingTime:this.stopTime,
            current:1
          }
        }).then((data) => {
          this.table = data.data.data.rows;
          this.searchList = data.data.data.rows;
          this.total = data.data.data.total;
          this.loading = false
        },data => {
          this.$message({
            message: '数据请求失败',
            type: 'error'
          });
        })

      },
      //切换分页
      handleCurrentChange(val){
        this.currentPage = val;
        this.loading = true
        this.$axios.get("api/jurisdiction/selAllRole",{
          params:{
            rname:this.rname,
            startTime:this.startTime,
            endingTime:this.stopTime,
            current:val
          }
        }).then((data) => {
          this.table = data.data.data.rows;
          this.searchList = data.data.data.rows;
          this.total = data.data.data.total;
          this.loading = false
        },data => {
          this.$message({
            message:"获取信息失败",
            type:"error"
          })
        });
      },
      //添加角色
      createRoles(){
        this.$refs.addroles.create();
        this.create = true;
      },
      getCheckedNodes(){
        this.$refs.addroles.submitRole();
      },
      resetForm(){
        this.$refs.addroles.resetForm();
        this.create = false;
      },
      addShowDataRole(data){
        this.create = false;
        if(this.currentPage == Math.ceil(this.total / this.pageSize)){
          if(this.table.length % this.pageSize == 0){
            this.handleCurrentChange(this.currentPage)
          }else{
            this.table.push(data)
          }
        } else{
          this.table.push(data)
        }
      },
      //查看角色信息
      selectRoles(index){
        this.$refs.selroles.selRole(index)
        this.select = true;
      },
      //修改角色
      handleEdit(index,scope){
        this.updateRoleState = true;
        this.$refs.UpdateRoles.create(index,scope);
      },
      submitUpdateRole(){
        this.$refs.UpdateRoles.submitUpdate();
      },
      updateShowDataRole(data){
        for(let i = 0 ; i < this.table.length ; i++){
          if(this.table[i].rid == data.rid){
            this.table[i].rname = data.rname;
            this.table[i].describe = data.describe

            this.table[i].changeTime = this.getNowFormatDate();
          }
        }
        this.updateRoleState = false;
      },
      //获取当前时间
      getNowFormatDate() {//获取当前时间
        var date = new Date();
        var seperator1 = "-";
        var seperator2 = ":";
        var month = date.getMonth() + 1<10? "0"+(date.getMonth() + 1):date.getMonth() + 1;
        var strDate = date.getDate()<10? "0" + date.getDate():date.getDate();
        var currentdate = date.getFullYear() + seperator1  + month  + seperator1  + strDate
          + " "  + date.getHours()  + seperator2  + date.getMinutes()
          + seperator2 + date.getSeconds();
        return currentdate;
      }

    },
    created () {
      //初始化设置
      this.$axios.get("api/jurisdiction/selAllRole",{
        params:{
          rname:"",
          startTime:"",
          endingTime:"",
          current:1
        }
      }).then((data) => {
        this.table = data.data.data.rows;
        this.searchList = data.data.data.rows;
        this.total = data.data.data.total;
        this.currentPage = 1;
        this.loading = false
      },data => {
        this.$message({
              message: '数据请求失败',
              type: 'error'
        });
      })
    }
  };
</script>

<style  lang="less" >

  .filter-container{
    margin-bottom:20px
  }

  .pagination-container{
    margin-top: 50px;
    margin-left:-5px;
  }
  .demo-drawer-footer{
    width: 100%;
    position: absolute;
    bottom: 0;
    left: 0;
    border-top: 1px solid #e8e8e8;
    padding: 10px 16px;
    text-align: right;
    background: #fff;
    z-index: 1000;
  }
  .nurse_table .title p{
    font-size:24px;
    text-align:center;
    margin:25px 0;
  }

</style>

