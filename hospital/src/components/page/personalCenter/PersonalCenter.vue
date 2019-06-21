<template>
  <div class="personalCenter"  v-loading="loading">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>个人中心</span>
        <el-button style="float: right; padding: 3px 0" @click="updateUser()" type="text">修改</el-button>
      </div>
      <el-row >
        <el-col :span="4" :offset="2">
            <el-col :span="24">
              <div >
                <el-image style="width: 150px; height: 150px; border-radius: 50%" :src="src"></el-image>
              </div>
            </el-col>
            <el-col :span="24" :offset="2" style="margin-top: 20px">
              <Upload ref="upload" action="api/updatePortrait"
                      :headers="headers"
                      :format="['jpg','jpeg','png']"
                      :max-size="2048"
                      :on-success="handleSuccess"
                      :on-error="handleError"
                      :on-exceeded-size="handleSize"
                      :data="uploadData">
                <!--on-success文件上传成功触发-->
                <Button icon="ios-cloud-upload-outline">Upload files</Button>
              </Upload>
            </el-col>
        </el-col>
        <el-col :span="4" :offset="2" style="font-size: 14px;color: #657180; line-height:2;">
          <p>名字:{{user.uname}}</p>
          <p>账号:{{user.acount}}</p>
          <p>地址:{{user.address}}</p>
          <p>年龄:{{user.age}}</p>
          <p>性别:{{sex}}</p>
          <p>身份证号:{{user.idcard}}</p>
          <p>手机号:{{user.phone}}</p>
          <p>入职日期:{{user.joindate}}</p>
          <p>专业:{{user.detials}}</p>
        </el-col>
      </el-row>
    </el-card>
    <Modal
      v-model="updateUserModal"
      title="修改个人信息"
      :closable="closable"
      :mask-closable="maskclosable"
      width="360">
      <Form ref="form" :model="form" :rules="ruleCustom" :label-width="80">
        <FormItem label="手机号" prop="phone">
          <Input type="text" v-model="form.phone"></Input>
        </FormItem>
        <FormItem label="地址" prop="address">
          <Input type="text" v-model="form.address"></Input>
        </FormItem>
      </Form>
      <div slot="footer">
        <Button @click="handleReset()" style="margin-left: 8px">取消</Button>
        <Button type="primary" :loading="loading" @click="handleSubmit()">
          <span v-if="!loading">修改</span>
          <span v-else>Loading...</span>
        </Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  import { Base64 } from 'js-base64';
    export default {
        name: "PersonalCenter",
        data(){
          const validatePhone = (rule, value, callback) => {
            if (!value) {
              return callback(new Error('手机号不能为空'));
            } else if (!/^1[34578]\d{9}$/.test(value)) {
              callback('手机号格式不正确');
            } else {
              callback();
            }
          };
          return {
            src:null,
            loading:true,
            user:{
              uname:"",
              acount:"",
              address:"",
              age:"",
              sex:"",
              idcard:"",
              phone:"",
              joindate:"",
              detials:"",
              eid:""
            },
            headers:{
              Authoriztion : "",
            },
            uploadData:{
              uid:'',
            },
            updateUserModal:false,
            closable:false,
            maskclosable:false,
            ruleCustom: {
              phone: [
                { type: 'string',max:11, message: '输入正确的手机号', trigger: 'blur' },
                { validator: validatePhone, trigger: 'blur' },
              ],
              address: [
                { required: true, message: '输入地址', trigger: 'blur' }
              ]
            },
            form:{
              phone:'',
              address:''
            }
          }
        },
      methods:{
        updateUser(){
          this.form.phone = this.user.phone;
          this.form.address = this.user.address;
          this.updateUserModal = true
        },
        handleSuccess(res, file){
          this.$refs.upload.clearFiles();
          this.$Message.success('上传成功');
          user = sessionStorage.getItem("user");
          let user = JSON.parse(Base64.decode(user));
          user.portrait = file.name
          sessionStorage.setItem("user",Base64.encode(JSON.stringify(user)));
          this.src = require("@/assets/img/"+file.name)
          this.$store.state.user.portrait = file.name;
        },
        handleError(file, fileList){
          this.$Message.error('上传失败');
        },
        handleSize(file,fileList){
          this.$Message.warning('文件过大 最大上传2MB');
        },
        handleSubmit(user){
          this.$refs.form.validate((valid) => {
            if (valid) {
              this.$axios.post("api/updateUser",{
                phone:this.form.phone,
                address:this.form.address,
                uid:this.user.uid
              }).then((data) => {
                this.$store.state.user.phone = this.form.phone;
                this.$store.state.user.address = this.form.address;
                user = sessionStorage.getItem("user");
                let user = JSON.parse(Base64.decode(user));
                user.phone = this.form.phone;
                user.address = this.form.address;
                sessionStorage.setItem("user",Base64.encode(JSON.stringify(user)));
                this.user.phone = this.form.phone;
                this.user.address = this.form.address;
                this.updateUserModal = false;
                this.$Message.success('修改成功!');
              }).catch(data => {

              })
            } else {
              this.$Message.error('填入正确数据!');
            }
          })
        },
        handleReset(user){
          this.updateUserModal = false;
          this.$refs.form.resetFields();
        }
      },
      computed:{
        sex () {
          switch (this.user.sex) {
            case 1:
              return '男'
            case 2:
              return '女'
            default:
              return this.user.sex
          }
        }
      },
      created() {
        this.loading = true;
        this.headers.Authoriztion = this.$store.state.user.authoriztion
        this.$axios.get("api/selCurrentUser/"+this.$store.state.user.uid).then(data => {
          let user = data.data.data;
          this.src = require("@/assets/img/"+user.portrait)
          this.user = user
          this.uploadData.uid = user.uid
          this.loading = false;
        }).catch(erroe => {
          this.$message.error('加载失败');
          this.loading = false;
        })
      }
    }
</script>
<style scoped>

</style>
