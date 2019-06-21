<template>
  <div class="header">
    <!-- 折叠按钮 -->
    <div class="collapse-btn" @click="collapseChage">
      <i class="el-icon-s-tools"></i>
    </div>
    <div class="logo">医院管理系统</div>
    <div class="header-right">
      <div class="header-user-con">
        <!-- 用户头像 -->
        <div class="user-avator"><img :src="imgPath"></div>
        <!-- 用户名下拉菜单 -->
        <el-dropdown class="user-name" trigger="click" @command="handleCommand">
                    <span class="el-dropdown-link">
                        {{username}} <i class="el-icon-caret-bottom"></i>
                    </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item  command="personalCenter">个人中心</el-dropdown-item>
            <el-dropdown-item command="openUpdatePassword">修改密码</el-dropdown-item>
            <el-dropdown-item divided  command="loginout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <Modal
          v-model="updatePassword"
          title="修改密码"
          :closable="closable"
          :mask-closable="maskclosable"
          width="360">
          <Form ref="formCustom" :model="formCustom" :rules="ruleCustom" :label-width="80">
            <FormItem label="旧密码" prop="password">
              <Input type="text" v-model="formCustom.password"></Input>
            </FormItem>
            <FormItem label="新密码" prop="passwd">
              <Input type="password" v-model="formCustom.passwd"></Input>
            </FormItem>
            <FormItem label="确认密码" prop="passwdCheck">
              <Input type="password" v-model="formCustom.passwdCheck"></Input>
            </FormItem>
          </Form>
          <div slot="footer">
              <Button @click="handleReset('formCustom')" style="margin-left: 8px">取消</Button>
            <Button type="primary" :loading="loading" @click="handleSubmit('formCustom')">
              <span v-if="!loading">修改</span>
              <span v-else>Loading...</span>
            </Button>
          </div>
        </Modal>
      </div>
    </div>
  </div>
</template>
<script>
  import bus from './bus';
  export default {
    data() {
      const validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入新密码'));
        } else {
          if (this.formCustom.passwdCheck !== '') {
            // 对第二个密码框单独验证
            this.$refs.formCustom.validateField('passwdCheck');
          }
          callback();
        }
      };
      const validatePassCheck = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请确认密码'));
        } else if (value !== this.formCustom.passwd) {
          callback(new Error('两次密码不一致'));
        } else {
          callback();
        }
      };
      const validatePassword = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('请输入密码'));
        }
        this.$axios.post("api/verifyPassword",{
            uid:this.$store.state.user.uid,
            password:this.formCustom.password
        }).then((data) => {
          if(data.data.data == "ok"){
            callback();
          }else{
            callback(new Error(data.data.data));
          }
        },data => {

        })
      };
      return {
        collapse: false,
        fullscreen: false,
        name: 'root',
        message: 2,
        updatePassword:false,
        modal_loading:true,
        loading:false,
        closable:false,
        maskclosable:false,
        img:require('@/assets/img/下载.png'),
        formCustom: {
          passwd: '',
          passwdCheck: '',
          password: ''
        },
        ruleCustom: {
          passwd: [
            { validator: validatePass, trigger: 'blur' },
            { type: 'string', min: 6,max:16, message: '长度不可小于六位或大于十六位', trigger: 'blur' }
          ],
          passwdCheck: [
            { validator: validatePassCheck, trigger: 'blur' },
            { type: 'string', min: 6,max:16, message: '长度不可小于六位或大于十六位', trigger: 'blur' }
          ],
          password: [
            { validator: validatePassword, trigger: 'blur' }
          ]
        }
      }
    },
    computed:{
      imgPath(){
        let imgSrc = this.$store.state.user.portrait;
        console.log(imgSrc)
        return require('@/assets/img/'+imgSrc)
      },
      username(){
        return this.$store.state.user.uname;
      }
    },
    methods:{
      // 用户名下拉菜单选择事件
      handleCommand(command) {
        if(command == 'loginout'){
          sessionStorage.removeItem('user')
          this.$axios.get("/api/logout")
          this.$store.state.user = null;
          this.$router.push('/login');
        }else if(command == 'openUpdatePassword'){
          this.updatePassword = true;
        }else if(command == 'personalCenter'){
          this.$router.push('/personalCenter')
        }
      },
      // 侧边栏折叠
      collapseChage(){
        this.collapse = !this.collapse;
        bus.$emit('collapse', this.collapse);
      },
      handleSubmit (name) {
        this.$refs[name].validate((valid) => {
          if (valid) {
            this.loading = true;
            this.$axios.post("api/updatePassword",{
              uid:this.$store.state.user.uid,
              password:this.formCustom.passwd
            }).then((data) => {
              if(data.data.data == "ok"){
                this.$notify({
                  title: '成功',
                  message: '修改成功 请重新登录',
                  type: 'success'
                });
                this.handleCommand("loginout");
              }else{
                this.$Message.error(data.data.data);
              }
            },data => {
              this.$Message.error('请检查网络设置');
            })
          } else {
            this.$Message.error('输入有误');
          }
        })
      },
      handleReset (name) {
        this.$refs[name].resetFields();
        this.updatePassword = false;
      }
    },
    mounted(){
      if(document.body.clientWidth < 1500){
        this.collapseChage();
      }
    }
  }
</script>
<style scoped>
  .header {
    position: relative;
    box-sizing: border-box;
    width: 100%;
    height: 70px;
    font-size: 22px;
    color: #fff;
  }
  .collapse-btn{
    float: left;
    padding: 0 21px;
    cursor: pointer;
    line-height: 70px;
  }
  .header .logo{
    float: left;
    width:250px;
    line-height: 70px;
  }
  .header-right{
    float: right;
    padding-right: 50px;
  }
  .header-user-con{
    display: flex;
    height: 70px;
    align-items: center;
  }
  .btn-bell-badge{
    position: absolute;
    right: 0;
    top: -2px;
    width: 8px;
    height: 8px;
    border-radius: 4px;
    background: #f56c6c;
    color: #fff;
  }
  .btn-bell .el-icon-bell{
    color: #fff;
  }
  .user-name{
    margin-left: 10px;
  }
  .user-avator{
    margin-left: 20px;
  }
  .user-avator img{
    display: block;
    width:40px;
    height:40px;
    border-radius: 50%;
  }
  .el-dropdown-link{
    color: #fff;
    cursor: pointer;
  }
  .el-dropdown-menu__item{
    text-align: center;
  }
</style>
