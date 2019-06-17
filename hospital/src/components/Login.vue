<template>
  <div class="login-wrap">
    <div class="ms-login">
      <div class="ms-title">后台管理系统</div>
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="0px" class="ms-content">
        <el-form-item prop="username">
          <el-input v-model="ruleForm.username" placeholder="username">
            <el-button slot="prepend" icon="el-icon-lx-people"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" placeholder="password" v-model="ruleForm.password" @keyup.enter.native="submitForm('ruleForm')">
            <el-button slot="prepend" icon="el-icon-lx-lock"></el-button>
          </el-input>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="submitForm('ruleForm')" :loading="loadings" >登录</el-button>
        </div>
        <!--<p class="login-tips">Tips : 用户名和密码随便填。</p>-->
      </el-form>
    </div>
  </div>
</template>

<script>
  import { Base64 } from 'js-base64';
  export default {
    data: function(){
      return {
        loadings:false,
        ruleForm: {
          username: 'zhaohh',
          password: '123456'
        },
        rules: {
          username: [
            { required: true, message: '请输入用户名', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      submitForm(formName) {
        this.loadings = true;
        let th = this;
        setTimeout(function(){
          th.$refs[formName].validate((valid) => {
            if (valid) {
              th.$axios.post("/api/login",{
                  acount:th.ruleForm.username,
                  password:th.ruleForm.password
                }
              ).then((data) =>{
                if(data.data.code == 10000){
                  const user = data.data.data;
                  const baseUser = Base64.encode(JSON.stringify(user));
                  sessionStorage.setItem("user",JSON.stringify(baseUser));
                  th.open1();
                  th.$router.push({path:'/'});
                }else{
                  th.open4();
                }
              },data => {
                th.loadings = false;
                this.$message({
                  message: '请检查网络设置',
                  type: 'error'
                });
              })
            } else {
              th.loadings = false;
              console.log('error submit!!');
              return false;
            }
          });
        },10)
      },
      open4() {
        this.loadings = false;
        this.$notify.error({
          title: '错误',
          message: '用户名或密码错误'
        });
      },
      open1() {
        this.loadings = false;
        this.$notify({
          title: '成功',
          message: '登陆成功',
          type: 'success'
        });
      }
    }
  }
</script>

<style scoped>
  .login-wrap{
    position: relative;
    width:100%;
    height:100%;
    background-image: url(../assets/img/login-bg.0899ffa6.jpg);
    background-size: 100%;
  }
  .ms-title{
    width:100%;
    line-height: 50px;
    text-align: center;
    font-size:20px;
    color: #fff;
    border-bottom: 1px solid #ddd;
  }
  .ms-login{
    position: absolute;
    left:70%;
    top:50%;
    width:350px;
    margin:-190px 0 0 -175px;
    border-radius: 5px;
    background: rgba(255,255,255, 0.3);
    overflow: hidden;
  }
  .ms-content{
    padding: 30px 30px;
  }
  .login-btn{
    text-align: center;
  }
  .login-btn button{
    width:100%;
    height:36px;
    margin-bottom: 10px;
  }
  .login-tips{
    font-size:12px;
    line-height:30px;
    color:#fff;
  }
</style>
