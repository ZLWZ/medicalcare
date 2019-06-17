<template>
    <div v-loading="loading">
      <el-form ref="form" :model="form" label-width="80px">
        <el-row :gutter="20" >
          <el-col :span="15">
            <el-form-item label="名称">
              <el-input v-model="form.name" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        <el-col :span="20">
          <el-form-item label="描述">
            <el-input type="textarea" :rows="3" v-model="form.describe" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="20">
          <el-form-item label="创建时间">
            <el-date-picker
              v-model="form.creation_time"
              type="datetime"
              :disabled="true"
              placeholder="创建时间">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="20">
          <el-form-item label="修改时间">
            <el-date-picker
              v-model="form.change_time"
              type="datetime"
              :disabled="true"
              placeholder="修改时间">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="20" >
          <el-button type="primary" style="align-content: center" :disabled="true" round>对应的权限</el-button>
        </el-col>
        <el-col :span="10" style="margin-top: 20px;">
          <el-tree :data="data" :props="defaultProps" @node-click="handleNodeClick"></el-tree>
        </el-col>
      </el-row>
      </el-form>
    </div>
</template>


<script>
    export default {
        data() {
            return {
              msg: 'select',
              form:{
                name:"",
                describe:"",
                creation_time:"",
                change_time:""
              },
              data: [
                {
                cc: '一级 1',
                children: [{
                  label: '二级 1-1'
                }]
              },{
                label:"er",
                }
              ],
              defaultProps: {
                children: 'children',
                label: 'label'
              },
              loading : false
            }
        },
      methods: {
        handleNodeClick(data) {
          console.log(data);
        },
        selRole(index){
          this.loading = true;

          this.$axios.get("api/jurisdiction/selRoleId/"+index,).then((data) => {
            let role = data.data.data;
            this.form.name = role.rname
            this.form.describe = role.describe
            this.form.creation_time = role.creationTime
            this.form.change_time = role.changeTime
            console.log(role);
            let roleData = [];
            for (let m = 0 ; m < role.menuList.length;m++){
              let object = {};
              let params = [];
              for(let i = 0 ; i < role.menuList[m].menus.length;i++){
                let children = {};
                children['label'] = role.menuList[m].menus[i].mname;
                params.push(children)
              }
              object['label'] = role.menuList[m].mname;
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
        }

      }
    }
</script>


<style scoped>

</style>
