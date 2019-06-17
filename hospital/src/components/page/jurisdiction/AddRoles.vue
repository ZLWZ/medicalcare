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
              :props="defaultProps">
            </el-tree>
          </el-col>
        </el-row>
      </el-form>
    </div>
</template>


<script>
    export default {
      name:'AddRoles',
        data() {
            return {
              props:["create"],
              form: {
                rname: null,
                describe: null,
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
        create(){
          this.data = [];
          this.form.rname = "";
          this.form.describe = "";
          this.loading = true;
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
            console.log(this.data)
            this.loading = false;
          },data => {
            this.$message({
              message: '数据请求失败',
              type:'error'
            });
          })
        },
        submitRole(){
          this.$refs.form.validate((valid) => {
            if (valid) {
              this.$axios.post("api/jurisdiction/addRole", {
                ids: this.$refs.tree.getCheckedKeys(),
                rname: this.form.rname,
                describe: this.form.describe
              }).then((data) => {
                this.$message({
                  message: '添加成功',
                  type: 'success'
                });
                this.$emit("addDataRole",data.data.data)
              },data => {
                this.$message.error('添加失败请检查网络设置');
              })
            }else{
              console.log('error submit!!');
              return false;
            }
          });
        },
        resetForm() {
          this.data = [];
          this.$refs.form.resetFields();
        }


      },
      created() {

      }



    }
</script>


<style scoped>

</style>
