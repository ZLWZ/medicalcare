<template>
  <div class="registerVisi">
    <div class="title">
      <p>挂号预约</p>
    </div>
    <el-form :model="register" :rules="rules" ref="registerForm" label-width="100px" class="register-ruleForm">
      <el-form-item label="患者姓名" prop="rname" class="register-name" style="width: 304px">
        <el-input v-model="register.rname"></el-input>
      </el-form-item>
      <el-form-item label="办理凭证" prop="vouchers" class="register-certificate">
        <el-select v-model="register.vouchers" placeholder="请选择办理凭证">
          <el-option label="身份证" value="idCard"></el-option>
          <el-option label="健康证" value="healthCard"></el-option>
        </el-select>
      </el-form-item>
      <p class="register-isshow" v-if="register.vouchers === 'idCard'">
        <el-form-item label="身份证号" prop="idcard" class="register-name" style="width: 304px">
          <el-input v-model="register.idcard"></el-input>
        </el-form-item>
      </p>
      <p class="register-isshow" v-else-if="register.vouchers === 'healthCard'">
        <el-form-item label="健康证号" prop="idcard" class="register-name" style="width: 304px">
          <el-input v-model="register.idcard"></el-input>
        </el-form-item>
      </p>
      <p v-else></p>
      <el-form-item label="科室" prop="did">
        <el-select v-model="register.did" placeholder="请选择科室" @change="depchange">
          <el-option v-for="items in department" :key="items.did" :label="items.dname " :value="items.did">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="就诊医生" prop="uid" class="register-certificate">
        <el-select v-model="register.uid" placeholder="请选择就诊医生">
          <el-option v-for="items in user" :key="items.uid" :label="items.uname " :value="items.uid">
          </el-option>
        </el-select>
      </el-form-item>
      <p v-if="register.uid">
        <el-form-item label="挂号日期：" class="register-name">
          <span>{{ visit_date }}</span>
        </el-form-item>
      </p>
      <p v-if="register.uid">
        <el-form-item label="药方费：" class="register-name">
          <span>待计算</span>
        </el-form-item>
      </p>
      <p v-if="register.uid">
        <el-form-item label="挂号费：" class="register-name">
          <span>{{ register.rmoney }}元</span>
        </el-form-item>
      </p>
      <el-button type="primary" class="submitBtn" @click="submitForm('registerForm')">提交挂号</el-button>
      <el-button type="reset" @click="resetForm('registerForm')">重置</el-button>
    </el-form>
  </div>
</template>

<script>
    export default {
      name: "RegisterVisi",
      data () {
        return {
          department:[],
          user:[],
          register: {
            rname: '',
            vouchers: '',
            idcard: '',
            did:'',
            uid:'',
            rmoney: 0,
            visit_date: '',
          },
          rules: {
            rname: [
              { required: true, message: '请输入患者姓名', trigger: 'blur' }
            ],
            vouchers: [
              { required: true, message: '请选择办理凭证', trigger: 'change' }
            ],
            idcard: [
              { required: true, message: '请输入身份证号', trigger: 'blur' },
              {pattern: /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/, message: '请输入正确的证件号', trigger: 'blur'}
            ],
            did: [
              { required: true, message: '请至少选择一个科室', trigger: 'change' }
            ],
            uid: [
              { required: true, message: '请选择医生', trigger: 'change' }
            ]
          }
        };
      },
      created () {
        this.visit_date = new Date().toLocaleDateString();
        this.$axios.get("/api/cashier/getAllInfo",{
          params:{
            did:this.register.did==''?0:parseInt(this.register.did)
          }
        }).then((response) =>{
          this.department = response.data.data[0]
          this.user = response.data.data[1]
        })
      },
      methods: {
        submitForm (formName) {
          this.$refs.registerForm.validate((valid) => {
            if (valid) {
              this.$axios.post("/api/cashier/addRegister",{
                rname:this.register.rname,
                idcard:this.register.idcard,
                did:parseInt(this.register.did),
                uid:this.register.uid,
                rmoney:parseInt(this.register.rmoney)
              }).then((response)=>{
                  if(response.data){
                    this.$notify({message: '提交成功',type: 'success'});
                    this.resetForm('registerForm')
                  }else {
                    this.$notify.error({message: '提交失败'});
                  }
              })
            } else {
              this.$notify.error({message: '提交失败'});
              return false;
            }
          });
        },
        resetForm (formName) {
          this.$refs.registerForm.resetFields();
        },
        depchange(did){
          this.$axios.get("/api/cashier/getAllInfo",{
            params:{
              did:did
            }
          }).then((response) =>{
            this.user = response.data.data[1]
            this.register.uid = this.user[0].uid
            this.register.rmoney = 20 + 5*did;
          });
        },
      }
    };
</script>

<style scoped="scoped">
  .registerVisi .title p{
    font-size:24px;
    text-align:center;
    margin:25px 0;
  }
  .register-ruleForm{
    width: 30%;
    margin: 0 auto;
    padding-left: 20px;
  }
  .register-name{
    width: 315px;
  }
  .submitBtn{
    margin-left: 30px;
  }
</style>
