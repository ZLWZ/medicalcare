<template>
  <div v-loading="loading">
    <el-form ref="form" :model="form" status-icon :rules="rules" label-width="80px">
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="角色名称" prop="rname">
            <el-input v-model="form.rname" placeholder="请输入名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="角色描述" prop="describe">
            <el-input v-model="form.describe" :rows="4" type="textarea" placeholder="请输入描述"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24" >
          <el-button type="primary"   :disabled="true" round>选择权限</el-button>
        </el-col>
        <el-col :span="24" style="margin-top: 20px;">
          <el-tree
            :data="data"
            show-checkbox
            node-key="id"
            ref="tree"
            :default-checked-keys="haveData"
            :props="defaultProps">
          </el-tree>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<!--@check-change="handleCheckChange"-->
<script>
  export default {
    name:'AddRoles',
    data() {
      return {
        props:["create"],
        haveData:[],
        currentData:[],
        form: {
          rname: null,
          describe: null,
          rid:null,
        },
        rules: {
          rname: [
            {required: true, message: '请输入用户名称名称', trigger: 'blur'},
            {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
          ],
          describe:[
            {min: 0, max: 100, message: '长度在 0 到 100 个字符', trigger: 'blur'}
          ]
        },
        data: [],
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        ids:[],
        loading:false,

      }
    },
    methods:{
      haveAll(){
        this.$axios.get("api/jurisdiction/selAllMenu").then((data) => {
          let menu = data.data.data;
          let roleData = [];
          for (let m = 0 ; m < menu.length;m++){
            let object = {};
            let params = [];
            for(let i = 0 ; i < menu[m].menus.length;i++){
              let children = {};
              children['label'] = menu[m].menus[i].mname;
              children['id'] = menu[m].menus[i].mid;
              params.push(children)
            }
            object['label'] = menu[m].mname;
            object['id'] = menu[m].mid;
            object['children'] = params;
            roleData.push(object);
          }
          this.data = roleData;
          this.loading = false;
        },data => {
          this.$message({
            message: '数据请求失败',
            type:'error'
          });
        })

      },
      assignment(menuList){
        for(let i = 0 ; i < menuList.length; i++){
          if(menuList[i].parend != 0 || menuList[i].menus.length == 0){
            this.haveData.push(menuList[i].mid);
            this.currentData.push(menuList[i].mid)
          }
          if(menuList[i].menus != null && menuList[i].menus.length > 0){
            this.assignment(menuList[i].menus)
          }
        }
      },
      have(id){
        this.$axios.get("api/jurisdiction/selRoleId/"+id).then((data) => {
          let menu = data.data.data.menuList;
          this.haveData = [];
          this.assignment(menu)
          this.haveAll();
        },data => {
          this.$message({
            message: '数据请求失败',
            type:'error'
          });
        })
      },
      create(id,scope){
        this.data = [];
        this.form.rname = scope.rname;
        this.form.describe = scope.describe;
        this.form.rid = scope.rid;
        this.loading = true;
        this.have(id)
      },
      resetForm() {
        this.data = [];
        this.$refs.form.resetFields();
      },
      submitUpdate(){
        this.$refs.form.validate((valid) => {
          if (valid) {
            this.$axios.put("api/jurisdiction/updRoleMenu", {
              ids: this.$refs.tree.getCheckedKeys(),
              rname: this.form.rname,
              describe: this.form.describe,
              rid : this.form.rid,
            }).then((data) => {
              this.$message({
                message: '修改成功',
                type: 'success'
              });
              this.$emit("updateDataRole",this.form)
            },data => {
              this.$message.error('修改失败请检查网络设置');
            })
          }else{
            console.log('error submit!!');
            return false;
          }
        });
      }
      // handleCheckChange: function (data, checked, indeterminate) {
      //   if(checked === true){
      //     if(this.currentData.indexOf(data.id) <= -1 && this.addIds.indexOf(data.id) <= -1){
      //       this.addIds.push(data.id)
      //     }else if(this.deleteIds.indexOf(data.id) > -1){
      //       let id = this.deleteIds.indexOf(data.id);
      //       this.deleteIds.splice(id,1)
      //     }
      //   }else{
      //     if(this.currentData.indexOf(data.id) > -1 && this.deleteIds.indexOf(data.id) <= -1) {
      //       this.deleteIds.push(data.id)
      //     }else if(this.addIds.indexOf(data.id) > -1){
      //       let id = this.addIds.indexOf(data.id);
      //       this.addIds.splice(id,1)
      //     }
      //   }
      //   console.log("++++++++++++++++++++++++++++>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
      //   console.log(this.addIds)
      //   console.log("---------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
      //   console.log(this.deleteIds)
      // }


    },
    created() {

    }



  }
</script>


<style scoped>

</style>
