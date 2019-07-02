<template>
  <div class="storehouse">
    <!--标题-->
    <div class="title">
      <p>药库管理</p>
    </div>
    <!--条件查询-->
    <el-row :gutter="20" type="flex" justify="center">
      <!--过期日期-->
      <el-col :span="9" >
        <div class="block">
          <span class="demonstration">过期日期:</span>
          <el-date-picker v-model="leavedate" type="daterange" :picker-options="pickerOptions" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" align="right">
          </el-date-picker>
          <!--<el-date-picker v-model="leaveStartDate" type="datetime" style="margin-top: 10px" placeholder="开始时间" align="right" :picker-options="pickerOptions"></el-date-picker>-->
          <!--<el-date-picker v-model="leaveStopDate" type="datetime" style="margin-top: 10px" placeholder="结束时间" align="right" :picker-options="pickerOptions"></el-date-picker>-->
        </div>
      </el-col>

      <!--剂型查询-->
      <el-col :span="5">
        剂型:
        <el-select v-model="did" multiple placeholder="请选择剂型">
          <el-option v-for="item in dosages" :key="item.did" :label="item.dname" :value="item.did"></el-option>
        </el-select>
      </el-col>
      <!--厂家查询-->
      <el-col :span="5">
        厂家:
        <el-select v-model="cid" multiple placeholder="请选择厂家">
          <el-option v-for="item in companys" :key="item.cid" :label="item.cname" :value="item.cid"></el-option>
        </el-select>
      </el-col>
    </el-row>
    <!--日期查询--->
    <el-row :gutter="20" type="flex" style="margin-top: 20px"  justify="center">
      <el-col :span="9">
        <div class="block">
          <span class="demonstration">生产日期:</span>
          <el-date-picker v-model="mkdate" type="daterange" :picker-options="pickerOptions" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" align="right">
          </el-date-picker>
          <!--<el-date-picker v-model="mkStartDate" type="datetime" style="margin-top: 10px" placeholder="开始时间" align="right" :picker-options="pickerOptions"></el-date-picker>-->
          <!--<el-date-picker v-model="mkStopDate" type="datetime" style="margin-top: 10px" placeholder="结束时间" align="right" :picker-options="pickerOptions"></el-date-picker>-->
        </div>
      </el-col>
      <!--中/西药-->
      <el-col :span="5">
        <div class="block">
          <span class="demonstration">类型:</span>
          <el-select v-model="ktype"  clearable placeholder="请选择中/西药">
            <el-option v-for="item in kTypes" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </div>
      </el-col>

      <!--规格查询-->
      <el-col :span="5">
        规格:
        <el-select v-model="sid" multiple placeholder="请选择规格">
          <el-option v-for="item in specifis" :key="item.sid" :label="item.sname" :value="item.sid"></el-option>
        </el-select>
      </el-col>


    </el-row>
    <el-row :gutter="20" type="flex" style="margin-top: 20px"  justify="center">
      <!--进货日期-->
      <el-col :span="9" >
        <div class="block">
          <span class="demonstration">进货日期:</span>
          <el-date-picker v-model="joindate" type="daterange" style="margin-top: 10px" :picker-options="pickerOptions" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" align="right">
          </el-date-picker>
          <!--<el-date-picker v-model="joinStartDate" type="datetime" style="margin-top: 10px" placeholder="开始时间" align="right" :picker-options="pickerOptions"></el-date-picker>-->
          <!--<el-date-picker v-model="joinStopDate" type="datetime" style="margin-top: 10px" placeholder="结束时间" align="right" :picker-options="pickerOptions"></el-date-picker>-->
        </div>
      </el-col>
      <!--名称-->
      <el-col :span="5" style="margin-top:10px;">
        名称:
        <el-input style="width: 200px;" placeholder="请输入名称" v-model="name" clearable></el-input>
      </el-col>
      <el-col :span="4" :offset="1">
        <el-button style="margin-top:10px;" type="primary"  @click="conditionSelect">查询</el-button>
        <el-button type="success" @click="dialogVisible = true">添加药物</el-button>
      </el-col>
    </el-row>
    <!--表格数据展示-->
    <el-table :data="tableData"  v-loading="loading" style="width: 100%; margin-top: 20px;" :border="true" :row-class-name="tableRowClassName">
      <el-table-column fixed prop="id" label="药品id" width="150"></el-table-column>
      <el-table-column prop="dname" label="药品名称" width="120"></el-table-column>
      <el-table-column prop="num" label="药库存数量" width="120"></el-table-column>
      <el-table-column prop="ktype" :formatter="kTypeIf" label="中/西药" width="120"></el-table-column>
      <el-table-column prop="company.cname" label="厂家" width="300"></el-table-column>
      <el-table-column prop="specifi.sname" label="规格" width="120"></el-table-column>
      <el-table-column prop="dosage.dname" label="剂型" width="120"></el-table-column>
      <el-table-column prop="money" label="单价" width="120"></el-table-column>
      <el-table-column prop="mkdate" label="生产日期" width="120"></el-table-column>
      <el-table-column prop="joindate" label="进药日期" width="120"></el-table-column>
      <el-table-column prop="leavedate" label="过期日期" width="120"></el-table-column>
      <el-table-column fixed="right" prop="user.acount" label="操作人" width="120"></el-table-column>
      <!--<el-table-column fixed="right" label="操作" width="120">-->
        <!--<template slot-scope="scope">-->
          <!--<el-button @click.native.prevent="deleteRow(scope.$index, tableData)" type="text" size="small">移除</el-button>-->
        <!--</template>-->
      <!--</el-table-column>-->
    </el-table>
    <!--分页-->
    <el-row :gutter="20" type="flex"  style="margin-top: 20px" justify="center">
      <el-col :span="11">
        <el-pagination
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="page"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </el-col>
    </el-row>
    <el-dialog
      title="添加药物"
      :visible.sync="dialogVisible"
      width="75%"
      :before-close="handleClose">
      <span slot="footer" class="dialog-footer">
        <add-kcdrugs ref="kcdrugs" v-on:success="success(res)"></add-kcdrugs>
  </span>
    </el-dialog>

  </div>
</template>

<script>
    import AddKcdrugs from './AddKcdrugs'
    export default {
      name: "Storehouse",
      components:{AddKcdrugs},
      data() {
        return {
          dialogVisible: false, //添加弹框
          name:'', //商品名称
          sid:'', //规格id
          did:'', //剂型id
          cid:'', //厂家id
          mkdate:[],//生产日期
          leavedate:[], //过期日期
          joindate:[], //进药日期
          mkStartDate:'', //生产开始日期
          mkStopDate:'', //生产结束日期
          leaveStartDate:'', //过期开始日期
          leaveStopDate:'', //过期结束日期
          joinStartDate:'', //进药开始日期
          joinStopDate:'', //进药结束日期
          specifis:[], //规格集合
          dosages:[], //剂型集合
          companys:[], //厂家集合
          ktype:'', //中/西药
          tableData: [],
          page: 1,
          total:1, //总条数
          pageSize:10,
          loading: true,//数据加载前动画
          kTypes:[
            {value:1,label:'中药'},
            {value:2,label:'西药'}
          ],
          options: [{
            value: '选项1',
            label: '黄金糕'
          }, {
            value: '选项2',
            label: '双皮奶'
          }, {
            value: '选项3',
            label: '蚵仔煎'
          }, {
            value: '选项4',
            label: '龙须面'
          }, {
            value: '选项5',
            label: '北京烤鸭'
          }],
          value1: [],
          input: '',
          value2:"",
          pickerOptions: {
            shortcuts: [{
              text: '最近一周',
              onClick(picker) {
                const end = new Date();
                const start = new Date();
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                picker.$emit('pick', [start, end]);
              }
            }, {
              text: '最近一个月',
              onClick(picker) {
                const end = new Date();
                const start = new Date();
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                picker.$emit('pick', [start, end]);
              }
            }, {
              text: '最近三个月',
              onClick(picker) {
                const end = new Date();
                const start = new Date();
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                picker.$emit('pick', [start, end]);
              }
            }]
          }
        }
      },
      methods: {
        success(res){
          this.dialogVisible  = res;
        },
        deleteRow(index, rows) {
          rows.splice(index, 1);
        },
        handleSizeChange(val) {
          this.pageSize = val;
          this.selectPage();
        },
        handleCurrentChange(val) {
          this.page = val;
          this.selectPage();
        },
        conditionSelect(){ //查询
          this.page = 1;
          this.selectPage();
        },
        tableRowClassName({row, rowIndex}) {
          if (rowIndex === 1) {
            return 'warning-row';
          } else if (rowIndex === 3) {
            return 'success-row';
          }
          return '';
        },
        kTypeIf(row, column, value){
          switch (value) {
            case 1:
              return '中药'
            case 2:
              return '西药'
            default:
              return '未定'
          }
        },
        tableRowClassName({row, rowIndex}) {
          if (row.num < 50) {
            return 'warning-row';
          } else if (row.num < 100) {
            return 'success-row';
          }
          return '';
        },
        selectPage(){
          this.loading = true;
          this.$axios.post("/api/Storehouse/selAllPageKcdrugs",{
            name : this.name, //商品名称
            sid : this.sid, //规格id
            did : this.did, //剂型id
            cid : this.cid, //厂家id
            mkdate : this.mkdate,//生产日期
            leavedate : this.leavedate, //过期日期
            joindate : this.joindate, //进药日期
            ktype:this.ktype,//中/西药
            pageSize : this.pageSize, //显示条数
            page : this.page  //当前页数
          }).then(data => {
            this.tableData = data.data.data.rows
            this.total = data.data.data.total
            this.loading = false;
          }).catch(data => {

          })
        },
        handleClose(done) { //开启弹框
          this.$confirm('确认关闭？')
            .then(_ => {
              console.log(11);
              this.$refs.kcdrugs.cancel();
              done();
            })
            .catch(_ => {});
        }
      },
      created(){
        this.loading = true;
        this.$axios.post("/api/Storehouse/selAllPageKcdrugs",{
          page:this.page,
          pageSize:this.pageSize,
        }).then(data => {
          this.tableData = data.data.data.rows
          this.total = data.data.data.total
          this.$axios.get("/api/Storehouse/selList").then(data => {
            const map = data.data.data;
            this.companys = map.company;
            this.specifis = map.specifi;
            this.dosages = map.dosage;
            this.loading = false;
          }).catch(data => {

          })
        }).catch(data => {

        })

      }

    }
</script>

<style >
  .storehouse .title p{
    font-size:24px;
    text-align:center;
    margin:25px 0;
  }
  /*.block{*/
    /*text-align: center;*/
  /*}*/
  .demonstration{
    margin-right: 3px;
  }
  .el-table .warning-row {
    background: #fef0f0;
  }

  .el-table .success-row {
    background: #fdf6ec;
  }
</style>
