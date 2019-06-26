<template>
    <div class="diagnose">
      <label class="lab">挂号序号：<span>{{table.rid}}</span></label>
      <label class="lab">挂号日期：<span>{{table.rid}}</span></label>
      <label class="lab">患者姓名：<span>{{table.rid}}</span></label>
      <hr class="hr">
      <label class="lab">科室：<span>{{table.rid}}</span></label>
      <label class="lab">医生：<span>{{table.rid}}</span></label>
      <hr class="hr">
      <h5 class="prescriptionC-title">R</h5>
    </div>
</template>

<script>
    export default {
      name: "Diagnose",
      data(){
        return{
          table:{
            rid:'101115255',
            rname:'',

          }
        }
      }
    }
</script>

<style scoped>
  .diagnose{
    margin-top: -20px;
  }
  .hr{
    border-top: 1px solid #4A7C32;
  }
  .lab{
    display: inline-block;
    font-size: 18px;
    width: 200px;
    margin: 10px 20px 0;
    font-weight: 300;
  }
  .lab span{
    font-weight: 600;
  }
  .prescriptionC-title{
    font-size: 26px;
    margin-left: 20px;
  }

</style>
