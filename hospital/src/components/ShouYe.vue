<template>
  <div class="shouYe">
    <div class="title">
      <p>邯郸市中心医院管理系统</p>
    </div>
    <el-card class="box-card">
      <div id="myChart" style="width: 250px;height:240px;float: left"></div>
      <div id="myChart1" style="width: 250px;height:240px;float: left;margin-left: 30px"></div>
    </el-card>
    <el-card class="box-card">
      <div id="myChart2" style="width: 100%;height:220px;"></div>
    </el-card>

    <el-card class="box-card" style="width: 65%">
      <div id="myChart3" style="width: 100%;height:195px;"></div>
    </el-card>
    <el-card class="box-card" style="width: 30%">
      <span v-for="role in roledata">
        <span style="text-align: left;display: block;margin-left: 13px">{{role.name}}</span>
       <el-progress :percentage="role.value" :color="role.color"></el-progress>
      </span>
    </el-card>
  </div>
</template>

<script>
    export default {
        name: "ShouYe",
      data () {
        return {
          drugsdata:[],
          drugsdatashow:[],
          kcdrugsdata:[],
          kcdrugsdatashow:[],
          roledata:[],
          data:['1','2','3','4','5','6','7'],
          datashow1:[],
          datashow2:[],
          userdata1:[],
          userdata2:[],
          userdatashow:[],
          myColor:["#42b983","#f1e05a","#f56c6c","#409EFF","#67C23A","#E6A23C","#909399"]
        }
      },
      mounted(){
        this.changeDate();
        this.$axios.get("/api/getInfo").then((response)=>{
          response.data.drugs.forEach((item)=>{
            let drugs = {value:item.num,name:item.dtype == 1?'中药':'西药'}
            this.drugsdatashow.push(drugs.name)
            this.drugsdata.push(drugs)
          })
          response.data.kcdrugs.forEach((item)=>{
            let kcdrugs = {value:item.num,name:item.ktype == 1?'中药':'西药',}
            this.kcdrugsdatashow.push(kcdrugs.name)
            this.kcdrugsdata.push(kcdrugs)
          })
          response.data.role.forEach((item,index)=>{
            let role = {name:item.rname,value:item.rid,color:this.myColor[index]}
            this.roledata.push(role)
          })
          response.data.user.forEach((item)=>{
            this.userdata1.push(item.age)
            let user = {name:item.uname,max:8}
            this.userdatashow.push(user)
          })
          for(let i = this.userdata1.length -1;i>=0;i--){
            this.userdata2.push(this.userdata1[i])
          }
          this.$axios.post("/api/getReg",{data:this.data}).then((response)=>{
            this.datashow1 = response.data.dataYu
            this.datashow2 = response.data.dataYi
            this.drawLine();
          })

        })


      },
      methods: {
        drawLine() {
          // 基于准备好的dom，初始化echarts实例
          let myChart = this.$echarts.init(document.getElementById('myChart'))
          let myChart1 = this.$echarts.init(document.getElementById('myChart1'))
          let myChart2 = this.$echarts.init(document.getElementById('myChart2'))
          let myChart3 = this.$echarts.init(document.getElementById('myChart3'))
          // 绘制图表
          myChart.setOption({
            title : {
              text: '药房药品比例',
              x:'center',
              textStyle: {
                fontSize: 20,
                fontWeight: 'bolder',
                color: '#000'
              }
            },
            legend: {
              // orient 设置布局方式，默认水平布局，可选值：'horizontal'（水平） ¦ 'vertical'（垂直）
              orient: 'vertical',
              x: 'right',
              y: 'bottom',
              itemWidth: 18,   // 设置图例图形的宽
              itemHeight: 15,  // 设置图例图形的高
              textStyle: {
                color: '#666'  // 图例文字颜色
              },
              // itemGap设置各个item之间的间隔，单位px，默认为10，横向布局时为水平间隔，纵向布局时为纵向间隔
              itemGap: 10,
              data: this.drugsdatashow
            },
            series : [
              {
                name: '药房药品',
                type: 'pie',
                radius : '60%',
                center: ['50%', '50%'],
                data:this.drugsdata,
                label:{            //饼图图形上的文本标签
                  normal:{
                    show:true,
                    position:'inner', //标签的位置
                    textStyle : {
                      fontWeight : 300 ,
                      fontSize : 16    //文字的字体大小
                    },
                    formatter:'{d}%'
                  }
                },
              }
            ]
          });
          myChart1.setOption({
            title : {
              text: '药库药品比例',
              x:'center',
              textStyle: {
                fontSize: 20,
                fontWeight: 'bolder',
                color: '#000'
              }
            },
            legend: {
              // orient 设置布局方式，默认水平布局，可选值：'horizontal'（水平） ¦ 'vertical'（垂直）
              orient: 'vertical',
              x: 'right',
              y: 'bottom',
              itemWidth: 18,   // 设置图例图形的宽
              itemHeight: 15,  // 设置图例图形的高
              textStyle: {
                color: '#666'  // 图例文字颜色
              },
              // itemGap设置各个item之间的间隔，单位px，默认为10，横向布局时为水平间隔，纵向布局时为纵向间隔
              itemGap: 10,
              data: this.kcdrugsdatashow
            },
            series : [
              {
                name: '药库药品',
                type: 'pie',
                radius : '60%',
                center: ['50%', '50%'],
                data:this.kcdrugsdata,
                label:{            //饼图图形上的文本标签
                  normal:{
                    show:true,
                    position:'inner', //标签的位置
                    textStyle : {
                      fontWeight : 300 ,
                      fontSize : 16    //文字的字体大小
                    },
                    formatter:'{d}%'
                  }
                },
              }
            ]
          });
          myChart2.setOption({
            title: {
              text: '科室人员分布图'
            },
            legend: {                        // 图例组件
              show: true,
              icon: 'rect',                   // 图例项的 icon。ECharts 提供的标记类型包括 'circle', 'rect', 'roundRect', 'triangle', 'diamond', 'pin', 'arrow'也可以通过 'image://url' 设置为图片，其中 url 为图片的链接，或者 dataURI。可以通过 'path://' 将图标设置为任意的矢量路径。
              top : '40%',                    // 图例距离顶部边距
              left : '15%',                   // 图例距离左侧边距
              itemWidth: 10,                  // 图例标记的图形宽度。[ default: 25 ]
              itemHeight: 10,                 // 图例标记的图形高度。[ default: 14 ]
              itemGap: 30,                	// 图例每项之间的间隔。[ default: 10 ]横向布局时为水平间隔，纵向布局时为纵向间隔。
              orient: 'vertical',             // 图例列表的布局朝向,'horizontal'为横向,''为纵向.
              textStyle: {                    // 图例的公用文本样式。
                fontSize: 15,
                color: '#fff'
              },
              data: [
                {
                  name: '女',
                  icon: 'rect',
                  textStyle: {
                    color: 'rgba(255,0,0,1)',
                    fontWeight: 'bold'		// 文字字体的粗细，可选'normal'，'bold'，'bolder'，'lighter'
                  }
                },{                    // 图例的数据数组。数组项通常为一个字符串，每一项代表一个系列的 name（如果是饼图，也可以是饼图单个数据的 name）。图例组件会自动根据对应系列的图形标记（symbol）来绘制自己的颜色和标记，特殊字符串 ''（空字符串）或者 '\n'（换行字符串）用于图例的换行。
                name: '男',                 // 图例项的名称，应等于某系列的name值（如果是饼图，也可以是饼图单个数据的 name）。
                icon: 'rect',               // 图例项的 icon。
                textStyle: {                // 图例项的文本样式。
                  color: 'rgba(51,0,255,1)',
                  fontWeight: 'bold'		// 文字字体的粗细，可选'normal'，'bold'，'bolder'，'lighter'
                }
              },],
            },
            radar: {
              center: ['50%', '60%'],
             /* shape: 'circle',*/
              name: {
                textStyle: {
                  color: '#fff',
                  backgroundColor: '#999',
                  borderRadius: 3,
                  padding: [3, 5]
                }
              },
              indicator: this.userdatashow
            },
            series: [{
              name: '',
              type: 'radar',
              itemStyle: {                // 折线拐点标志的样式。
                normal: {                   // 普通状态时的样式
                  lineStyle: {
                    width: 1
                  },
                  opacity: 0.2
                },
                emphasis: {                 // 高亮时的样式
                  lineStyle: {
                    width: 2
                  },
                  opacity: 1
                }
              },
              data : [
                {
                  name: '女',
                  value : this.userdata1,
                  symbol: 'circle',
                  symbolSize: 5,
                  /*label: {                    // 单个拐点文本的样式设置
                    normal: {
                      show: true,             // 单个拐点文本的样式设置。[ default: false ]
                      position: 'top',        // 标签的位置。[ default: top ]
                      distance: 10,            // 距离图形元素的距离。当 position 为字符描述值（如 'top'、'insideRight'）时候有效。[ default: 5 ]
                      color: 'auto',          // 文字的颜色。如果设置为 'auto'，则为视觉映射得到的颜色，如系列色。[ default: "#fff" ]
                      fontSize: 14,           // 文字的字体大小
                      formatter:function(params) {
                        return params.value;
                      }
                    }
                  },*/
                  areaStyle: {                // 单项区域填充样式
                    normal: {
                      color: 'rgba(255,0,0,0.6)'       // 填充的颜色。[ default: "#000" ]
                    }
                  }
                },{
                  name: '男',
                  value: this.userdata2,
                  symbol: 'circle',
                  symbolSize: 5,
                  itemStyle: {
                    normal: {
                      borderColor: 'rgba(51,0,255,1)',
                      borderWidth: 3,
                    }
                  },
                  lineStyle: {
                    normal: {
                      opacity: 0.5
                    }
                  },
                  areaStyle: {
                    normal: {
                      color: 'rgba(51,0,255,0.5)'
                    }
                  }
                }
              ]
            }]
          });
          myChart3.setOption({
            title : {
              text: '挂号统计',
            },
            tooltip : {
              trigger: 'axis'
            },
            grid:{
              height:'58%'
            },
            legend: {
              data:['挂号','已处理']
            },
            toolbox: {
              show : true,
              feature : {
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {show: true}
              }
            },
            calculable : true,
            xAxis : [
              {
                type : 'category',
                data : this.data
              }
            ],
            yAxis : [
              {
                type : 'value',
              }
            ],
            series : [
              {
                name:'挂号',
                type:'bar',
                data:this.datashow1,
                markPoint : {
                  data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                  ]
                },
                markLine : {
                  data : [
                    {type : 'average', name: '平均值'}
                  ]
                }
              },
              {
                name:'已处理',
                type:'bar',
                data:this.datashow2,
                markPoint : {
                  data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                  ]
                },
                markLine : {
                  data : [
                    {type : 'average', name : '平均值'}
                  ]
                }
              }
            ]
          })
        },
        changeDate() {
          const now = new Date().getTime();
          let newdata = [];
          this.data.forEach((item, index) => {
            const date = new Date(now - (6 - index) * 86400000);
            let year = `${date.getFullYear()}`;
            let month = `${date.getMonth() + 1}` < 10 ?"0" +`${date.getMonth() + 1}`:`${date.getMonth() + 1}`
            let day = `${date.getDate()}`<10?"0"+`${date.getDate()}`:`${date.getDate()}`
            item = year+"-"+month+"-"+day
            newdata.push(item)
          })
          this.data = newdata
        }
      }
    }
</script>

<style scoped>
  .shouYe{
    text-align: center;
  }
  .shouYe .title p{
    font-size:24px;
    text-align:center;
    margin:10px 0;
  }

  .box-card {
    width: 600px;
    display: inline-block;
    margin: 10px 10px;
  }
</style>
