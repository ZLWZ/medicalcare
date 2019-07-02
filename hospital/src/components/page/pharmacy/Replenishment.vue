<template>
    <div class="replenishment">
      <div class="title">
        <p>申请补给</p>
      </div>
      <div class="filter-container">
        <el-form label-width="120px" :model="applytable" :rules="rules" ref="applytable" class="apply-form">
          <el-form-item label="药品名称" prop="aname">
            <el-input v-model="applytable.aname" placeholder="药品名称"></el-input>
          </el-form-item>
          <el-form-item label="类别" prop="atype">
            <el-select v-model="applytable.atype" placeholder="请选择类别">
              <el-option v-for="item in atype" :key="item.tid" :label="item.tname" :value="item.tid"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="申请科室" prop="did">
            <el-select v-model="applytable.did" placeholder="请选择科室">
              <el-option v-for="item in department" :key="item.did" :label="item.dname" :value="item.did"></el-option>
            </el-select>
          </el-form-item>
          <div style="display: block"></div>
          <el-form-item label="数量（件）" prop="num">
            <el-input v-model.number="applytable.num" placeholder="药品数量"></el-input>
          </el-form-item>
          <el-form-item label="规格" prop="sid">
            <el-select v-model="applytable.sid" placeholder="请选择">
              <el-option v-for="item in specifi" :key="item.sid" :label="item.sname" :value="item.sid"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div style="width: 86.5%">
          <el-button type="primary" style="margin-left: 25px;float: right" @click="submitApply('applytable')">提交申请</el-button>
          <el-button type="danger" style="float: right;" @click="resetApply">重置申请</el-button>
          <div style="clear: both"></div>
        </div>
      </div>
      <h4 class="tips">以下药品库存不足10件了，建议申请补给！</h4>
      <el-table :data="insufficientTable" @row-dblclick="huidiao" border class="insufficientTable" height="230px" :header-cell-style="getRowClass">
        <el-table-column prop="dname" label="药品名称"></el-table-column>
        <el-table-column prop="dtype" label="类别"></el-table-column>
        <el-table-column prop="specifi.sname" label="规格"></el-table-column>
        <el-table-column prop="num" label="库存"></el-table-column>
      </el-table>
    </div>
</template>

<script>
    export default {
      name: "Replenishment",
      data(){
        return{
          applytable:{
            aname:'',
            num:'',
            atype:'',
            sid:'',
            did:''
          },
          department:[],
          specifi:[],
          insufficientTable:[],
          atype:[
            {tid:1,tname:'中药'},
            {tid:2,tname:'西药'},
          ],
          getRowClass ({row, column, rowIndex, columnIndex}) {
            if (rowIndex === 0) {
              return 'background: rgb(233, 233, 235)'
            } else {
              return ''
            }
          },
          rules: {
            aname: [
              {required: true, message: '请输入药品名', trigger: 'blur'}
            ],
            atype:[
              {required: true, message: '请选择类别', trigger: 'change'}
            ],
            did:[
              {required: true, message: '请选择科室', trigger: 'change'}
            ],
            sid:[
              {required: true, message: '请选择规格', trigger: 'change'}
            ],
            num:[
              { required: true, message: '请输入数量', trigger: 'blur' },
              {type: 'number', message: '一次最多只能申请200件', trigger: 'blur' , min:0 ,max:200},
            ]
          }
        }
      },
      methods:{
        resetApply(){
          this.$refs['applytable'].resetFields();
        },
        huidiao(row){
          console.log(row)
          this.applytable.aname = row.dname
          this.applytable.atype = row.dtype == '中药'?1:2
          this.applytable.sid = row.sid
        },
        submitApply(applytable){
          this.$refs[applytable].validate((valid) => {
              if (valid) {
                this.$axios.post("/api/pharmacy/insertApply",{
                  apply :this.applytable
                }).then((response)=>{
                  if(response.data){
                    this.$message({message: '申请成功,等待审核和配送！',type: 'success'})
                    this.resetApply();
                  }else{
                    this.$message("申请错误，出现异常，稍后重试！")
                  }
                })
              }else {
                 return false;
              }
          })
        }
      },
      created() {
        this.$axios.get("/api/pharmacy/getAllInfo").then((response)=>{
          this.department = response.data.data.department
          this.specifi = response.data.data.specifi
        })
        this.$axios.get("/api/pharmacy/getDrugDefi").then((response)=>{
          response.data.forEach(function (item) {
            item.dtype = item.dtype==1?'中药':'西药';
          })
          this.insufficientTable = response.data
        })
      }
    }
</script>

<style scoped>
  .replenishment .title p{
    font-size:24px;
    text-align:center;
    margin:25px 0;
  }
  .filter-container{
    margin-bottom: 20px;
  }
  .apply-form{
    width: 100%
  }
  .apply-form .applytable{
    display: block;
    line-height: 30px;
    font-size: 20px;
    font-weight: bold;
    margin: 0 auto;
  }
  .apply-form .el-form-item{
    display: inline-block;
    width: 30%;
    padding-left: 40px;
    padding-right: 40px;
  }
   .apply-form .el-select{
     width: 100%
   }
  .tips{
    display: block;
    padding-left: 95px;
    margin-top: 20px;
    margin-bottom: 20px;
    color: #FF4949;
  }
  .insufficientTable{
    width: 85%;
    margin: 0 auto 30px;
  }


</style>
