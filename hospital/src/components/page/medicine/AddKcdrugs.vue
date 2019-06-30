<template>
  <div>
  <el-table
    :data="tableDatas"
    style="width: 100%">
    <el-table-column type="expand">
      <template slot-scope="props">
        <el-form label-position="left" inline class="demo-table-expand">
          <el-form-item label="商品名称:"><span>{{ props.row.dname }}</span></el-form-item>
          <el-form-item label="厂家:"><span>{{ props.row.company.cname }}</span></el-form-item>
          <el-form-item label="生产日期:"><span>{{ props.row.mkdate }}</span></el-form-item>
          <el-form-item label="过期日期:"><span>{{ props.row.leavedate }}</span></el-form-item>
          <el-form-item label="数量:"><span>{{ props.row.num }}</span></el-form-item>
          <el-form-item label="单价:"><span>{{ props.row.money }}</span></el-form-item>
          <el-form-item label="总价:"><span>{{ props.row.totalPrice }}</span></el-form-item>
          <el-form-item label="规格:"><span>{{ props.row.specifi.sname }}</span></el-form-item>
          <el-form-item label="剂型:"><span>{{ props.row.dosage.dname }}</span></el-form-item>
          <el-form-item label="类型:"><span>{{ obtainKname(props.row.ktype) }}</span></el-form-item>
        </el-form>
      </template>
    </el-table-column>
    <el-table-column label="商品名称" prop="dname"></el-table-column>
    <el-table-column label="数量" prop="num"></el-table-column>
    <el-table-column label="单价" prop="money"></el-table-column>
    <el-table-column label="总价" prop="totalPrice"></el-table-column>
    <el-table-column label="厂家" prop="company.cname"></el-table-column>
    <el-table-column label="操作">
      <template slot-scope="scope">
        <el-button size="mini" @click="deleteTableData(scope.row,scope.$index)">删除</el-button>
        <!--<el-button size="mini" type="danger">修改</el-button>-->
      </template>
    </el-table-column>
  </el-table>
    <el-row style="margin-top: 10px">
      <el-col :span="3" :offset="21">
        <el-button type="danger" @click="cancel()">取消</el-button>
        <el-button type="success" @click="subimtFcdrugs()">提交</el-button>
      </el-col>
    </el-row>
  <el-divider></el-divider>
    <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80">
    <el-row>
      <el-col :span="6" >
        <FormItem label="名称:" prop="name">
          <el-input style="width: 200px;" placeholder="请输入名称" v-model="formValidate.name" clearable></el-input>
        </FormItem>
      </el-col>
      <el-col :span="6">
          <FormItem label="生产日期:" prop="mkdate">
          <el-date-picker v-model="formValidate.mkdate" type="date" placeholder="选择生产日期" align="right" style="width: 200px;" :picker-options="pickerOptions"></el-date-picker>
          </FormItem>
      </el-col>
      <el-col :span="6">
        <FormItem label="过期日期:" prop="leavedate">
          <el-date-picker v-model="formValidate.leavedate" type="date" placeholder="选择过期日期" align="right" style="width: 200px;" :picker-options="leavedateOptions">
          </el-date-picker>
        </FormItem>
      </el-col>
      <!--中/西药-->
      <el-col :span="6" >
        <div class="block">
          <FormItem label="类型:" prop="ktype">
            <el-select v-model="formValidate.ktype"  clearable placeholder="请选择中/西药">
              <el-option v-for="item in kTypes" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </FormItem>
        </div>
      </el-col>
    </el-row>
    <el-row>
      <!--剂型查询-->
      <el-col :span="6">
        <FormItem label="剂型:" prop="did">
        <el-select v-model="formValidate.did"  placeholder="请选择剂型">
          <el-option v-for="item in dosages" :key="item.did" :label="item.dname" :value="item.did"></el-option>
        </el-select>
        </FormItem>
      </el-col>
      <!--厂家查询-->
      <el-col :span="6">
        <FormItem label="厂家:" prop="cid">
        <el-select v-model="formValidate.cid"  placeholder="请选择厂家">
          <el-option v-for="item in companys" :key="item.cid" :label="item.cname" :value="item.cid"></el-option>
        </el-select>
        </FormItem>
      </el-col>
      <!--规格查询-->
      <el-col :span="6">
        <FormItem label="规格:" prop="sid">
        <el-select v-model="formValidate.sid"  placeholder="请选择规格">
          <el-option v-for="item in specifis" :key="item.sid" :label="item.sname" :value="item.sid"></el-option>
        </el-select>
        </FormItem>
      </el-col>
      <el-col :span="6">
        <FormItem label="数量:" prop="num">
        <el-input-number v-model="num" style="width: 200px"  :min="1"  label="描述文字"></el-input-number>
        </FormItem>
      </el-col>
    </el-row>
      <el-row>
        <el-col :span="6">
          <FormItem label="单价:" prop="money">
            <el-input-number v-model="money"  style="width: 200px"  :precision="2" :step="0.1" ></el-input-number>
          </FormItem>
        </el-col>
        <el-col :span="2">
          <FormItem label="总价:" prop="money">
          {{CalculationTotalPrice}}
          </FormItem>
        </el-col>
        <el-col :span="3" :offset="13">
          <el-button type="danger" @click="handleReset()">清除</el-button>
          <el-button type="success" @click="handleSubmit()">添加</el-button></el-col>
        <el-button type="success" @click="c()">c</el-button>
      </el-row>
    </Form>
</div>

</template>

<script>
  export default {
    name:'AddKcdrugs',
    data(){
      const validatemkDate = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('填写生产日期'));
        } else {
          if (value > new Date()) {
            // 对第二个密码框单独验证
            callback(new Error('日期错误'));
          }
          callback();
        }
      };
      const validateleaveDate = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('填写过期日期'));
        } else {
          if (value <= this.formValidate.mkdate || value <= new Date()) {
            // 对第二个密码框单独验证
            callback(new Error('日期错误'));
          }
          callback();
        }
      };
      return {
        formValidate:{
          name:'', //名称
          mkdate:'', //生产日期
          leavedate:'', //过期日期
          did:'', //剂型
          cid:'', //厂家
          sid:'', //规格
          ktype:'',//中/西药
        },
        num:1, //数量
        money:1.00, //单价
        totalPrice:1.00, //总价
        dosages:[], //剂型
        companys:[], //厂家
        specifis:[], //规格
        kTypes:[
          {value:1,label:'中药'},
          {value:2,label:'西药'}
        ],//中/西药
        ruleValidate:{
          name: [
            { required: true, message: '名称不可为空', trigger: 'blur' }
          ],
          mkdate: [
            { validator: validatemkDate, trigger: 'blur' }
          ],
          leavedate: [
            { validator: validateleaveDate, trigger: 'blur' }
          ],
          did: [
            { required: true, message: '选择剂型'}
          ],
          cid: [
            { required: true, message: '选择厂家'}
          ],
          sid: [
            { required: true, message: '选择规格' }
          ],
          ktype: [
            { required: true, message: '选择类型'}
          ],
        },
        pickerOptions: {
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
        leavedateOptions: {
          shortcuts: [{
            text: '一月后',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() + 3600 * 1000 * 24 * 30);
              picker.$emit('pick', date);
            }
          }, {
            text: '一年后',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() + 3600 * 1000 * 24 * 30 * 12);
              picker.$emit('pick', date);
            }
          },
            {
              text: '两年后',
              onClick(picker) {
                const date = new Date();
                date.setTime(date.getTime() + 3600 * 1000 * 24 * 30 * 12 * 2);
                picker.$emit('pick', date);
              }
            }]
        },
        tableData:{},
        tableDatas: [
          {
            dname:'cc',
            mkdate:'11',
            leavedate:'1',
            specifi:{sname:'1',sid:1},
            company:{cname:'1',cid:1},
            dosage:{dname:'1',did:1},
            ktype:1,
            cid:1,
            sid:1,
            did:1,
            num:1,
            money:1,
            totalPrice:1,
          }
        ]
      }
    },
    methods:{
      c(){
        this.tableDatas[0].num += 1;
      },
      subimtFcdrugs(){
        console.log(this.tableDatas)
        if(this.tableDatas.length > 0){
          this.$axios.post("api/Storehouse/addKcdrugs",{
            kcdrugss:this.tableDatas
          }).then(data => {
            if(data.data.code == 10000){
              this.$emit('success',false)
              this.cancel();
              this.$message({
                message: '添加成功',
                type: 'success'
              });

            }
          }).catch(data => {

          })
        }else{
          this.$message.error('提交的信息不可为空');
        }

      },
      deleteTableData(row,index){
        this.tableDatas.splice(index,1)
      },
      handleReset(){
        this.num=1, //数量
        this.money=1.00, //单
        this.$refs.formValidate.resetFields();
      },
      handleSubmit () {
        this.$refs.formValidate.validate((valid) => {
          if (valid) {
            this.tableData.dname = this.formValidate.name;
            this.tableData.mkdate = this.dateFtt("yyyy-MM-dd",this.formValidate.mkdate);
            this.tableData.leavedate = this.dateFtt("yyyy-MM-dd",this.formValidate.leavedate);
            this.tableData.specifi = this.obtainSname(this.formValidate.sid);
            this.tableData.company = this.obtainCname(this.formValidate.cid);
            this.tableData.dosage = this.obtainDname(this.formValidate.did);
            this.tableData.ktype = this.formValidate.ktype
            this.tableData.cid=this.formValidate.cid
            this.tableData.sid=this.formValidate.sid
            this.tableData.did=this.formValidate.did
            this.tableData.num = this.num;
            this.tableData.money = this.money;
            this.tableData.totalPrice = this.totalPrice;

            console.log(this.tableDatas.length)
            let f = true;
            for(let i = 0 ; i < this.tableDatas.length;i++){
              console.log(this.tableDatas[i].dname == this.tableData.dname && this.tableDatas[i].mkdate == this.tableData.mkdate
                && this.tableDatas[i].leavedate == this.tableData.leavedate && this.tableDatas[i].sid == this.tableData.sid
                && this.tableDatas[i].cid == this.tableData.cid && this.tableDatas[i].did == this.tableData.did
                && this.tableDatas[i].ktype == this.tableData.ktype && this.tableDatas[i].money == this.tableData.money)
              if(this.tableDatas[i].dname == this.tableData.dname && this.tableDatas[i].mkdate == this.tableData.mkdate
              && this.tableDatas[i].leavedate == this.tableData.leavedate && this.tableDatas[i].sid == this.tableData.sid
              && this.tableDatas[i].cid == this.tableData.cid && this.tableDatas[i].did == this.tableData.did
                && this.tableDatas[i].ktype == this.tableData.ktype && this.tableDatas[i].money == this.tableData.money){
                f = false;
                this.tableDatas[i].totalPrice +=this.tableData.totalPrice;
                this.tableDatas[i].num += this.tableData.num;
              }
            }
            if(f){
              this.tableDatas.push(this.tableData);
            }
            this.tableData = {};
            this.handleReset();
          } else {
            this.$Message.error('数据不完整!');
          }

        })
      },
      obtainSname(sid){
        for(let i = 0 ; i < this.specifis.length ; i++){
          if(this.specifis[i].sid === sid){
            return this.specifis[i];
          }
        }
        return '';
      },
      obtainDname(did){
        for(let i = 0 ; i < this.dosages.length ; i++){
          if(this.dosages[i].did === did){
            return this.dosages[i];
          }
        }
        return '';
      },
      obtainCname(cid){
        for(let i = 0 ; i < this.companys.length ; i++){
          if(this.companys[i].cid === cid){
            return this.companys[i];
          }
        }
        return '';
      },
      obtainKname(kid){
        for(let i = 0 ; i < this.kTypes.length ; i++){
          console.log(this.kTypes[i].value )
          if(this.kTypes[i].value === kid){
            return this.kTypes[i].label;
          }
        }
        return '';
      },
      formatNum (f, digit) {
        var m = Math.pow(10, digit);
        return parseInt(f * m, 10) / m;
      },
      dateFtt(fmt,date)
      { //author: meizz
        var o = {
          "M+" : date.getMonth()+1,                 //月份
          "d+" : date.getDate(),                    //日
          "h+" : date.getHours(),                   //小时
          "m+" : date.getMinutes(),                 //分
          "s+" : date.getSeconds(),                 //秒
          "q+" : Math.floor((date.getMonth()+3)/3), //季度
          "S"  : date.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt))
          fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)
          if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        return fmt;
      },
      cancel(){
        this.tableData = {};
        this.tableDatas = [];
      }
    },
    computed:{
      CalculationTotalPrice(){
        this.totalPrice = this.formatNum(this.num * this.money,2)
        return this.totalPrice
      }

    },
    created() {
      this.$axios.get("/api/Storehouse/selList").then(data => {
        const map = data.data.data;
        this.companys = map.company;
        this.specifis = map.specifi;
        this.dosages = map.dosage;
      }).catch(data => {

      })
    }
  }
</script>

<style scoped >
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
</style>
