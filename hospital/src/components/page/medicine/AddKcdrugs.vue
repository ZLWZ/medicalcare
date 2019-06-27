<template>
  <div class="addKcdrugs">
    <div class="title">
      <p>添加药品</p>
    </div>
    <el-table :data="tableData" height="230px" v-loading="loading" style="width: 100%;font-size: 14px">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="挂号序号：">
              <span>{{  }}</span>
            </el-form-item>
            <el-form-item label="挂号费：">
              <span>{{  }}</span>
            </el-form-item>
            <el-form-item label="挂号时间：">
              <span>{{  }}</span>
            </el-form-item>
            <el-form-item label="药方费：">
              <span>{{  }}</span>
            </el-form-item>
            <el-form-item label="患者姓名：">
              <span>{{  }}</span>
            </el-form-item>
            <el-form-item label="科室：">
              <span>&nbsp;&nbsp;&nbsp;{{ }}</span>
            </el-form-item>
            <el-form-item label="身份证：">
              <span>&nbsp;&nbsp;&nbsp;{{ }}</span>
            </el-form-item>
            <el-form-item label="医生：">
              <span>&nbsp;&nbsp;&nbsp;{{  }}</span>
            </el-form-item>
            <el-form-item label="状态：">
              <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{  }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="挂号序号" prop=""></el-table-column>
      <el-table-column label="患者姓名" prop=""></el-table-column>
      <el-table-column label="身份证号" prop=""></el-table-column>
      <el-table-column label="挂号日期" prop=""></el-table-column>
      <el-table-column align="center" label="状态" prop=""></el-table-column>
      <el-table-column align="center" label="操作">
        <template scope="scope">
          <el-button size="small" icon="el-icon-delete" circle type="danger" @click=""></el-button>
        </template>
      </el-table-column>
    </el-table>
    <br>
    <div style="margin-top: -10px;padding-bottom: 10px;margin-bottom:-15px">
      <el-button type="primary" style="float: right" plain>提交</el-button>
      <el-button type="danger" style="float: right;margin-right: 20px" plain>清空</el-button>
      <div style="clear: both"></div>
    </div>
    <el-divider></el-divider>
    <div style="width: 100%">
      <el-form class="demo-ruleForm" :model="form" ref="form" :inline="true" label-position="left" label-width="70px" style='width: 100%;'>
        <el-form-item label="药品名" prop="uname">
          <el-input v-model="form.uname" placeholder="药品名" style="width: 203px"></el-input>
        </el-form-item>
        <el-form-item label="数量" prop="age">
          <el-input v-model.number="form.age"  placeholder="数量" style="width: 203px"></el-input>
        </el-form-item>
        <el-form-item label="单价" prop="age">
          <el-input v-model.number="form.age"  placeholder="单价" style="width: 203px"></el-input>
        </el-form-item><br>
        <el-form-item label="类型" prop="did">
          <el-select v-model="form.did" filterable clearable placeholder="请选择类型">
            <el-option v-for="item in leixing" :key="item.did" :label="item.dname" :value="item.did"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="厂家" prop="did">
          <el-select v-model="form.did" filterable clearable placeholder="请选择厂家">
            <el-option v-for="item in leixing" :key="item.did" :label="item.dname" :value="item.did"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="规格" prop="did">
          <el-select v-model="form.did" filterable clearable placeholder="请选择规格">
            <el-option v-for="item in leixing" :key="item.did" :label="item.dname" :value="item.did"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="剂型" prop="did">
          <el-select v-model="form.did" filterable clearable placeholder="请选择剂型">
            <el-option v-for="item in leixing" :key="item.did" :label="item.dname" :value="item.did"></el-option>
          </el-select>
        </el-form-item><br>
        <el-form-item label="生产日期" prop="">
          <el-date-picker v-model="sdate" value-format="yyyy-MM-dd" style="width: 203px" type="date" placeholder="选择日期"></el-date-picker>
        </el-form-item>
        <el-form-item label="过期日期" prop="sdate">
          <el-date-picker v-model="sdate" value-format="yyyy-MM-dd" style="width: 203px" type="date" placeholder="选择日期"></el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button type="primary" style="float: right" plain>确定</el-button>
        <el-button type="danger" style="float: right;margin-right: 20px" plain>重置</el-button>
      </div>
    </div>
  </div>
</template>

<script>
    export default {
      name: "AddKcdrugs",
      data(){
        return{
          loading:false,
          tableData:[],
          form:[],
          rid:'',
          rname:''
        }
      },
      created() {

      }
    }
</script>

<style scoped>
  .addKcdrugs .title p{
    font-size:24px;
    text-align:center;
    margin:20px 0;
  }
  .demo-table-expand{
    font-size:0;
  }
  .demo-table-expand label{
    width:90px;
    color:#99a9bf;
  }
  .demo-table-expand .el-form-item{
    margin-right:0;
    margin-bottom:0;
    width: 50%;
  }
</style>
