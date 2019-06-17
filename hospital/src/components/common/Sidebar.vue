<template>
  <div class="sidebar">
    <el-menu class="sidebar-el-menu" :default-active="onRoutes" :collapse="collapse" background-color="#324157"
             text-color="#bfcbd9" active-text-color="#20a0ff" unique-opened router>
      <template v-for="item in items">
        <template v-if="item.menus">
          <el-submenu :index="item.mid.toString()" :key="item.mid">
            <template slot="title">
              <i :class="item.icon"></i><span slot="title">{{ item.mname }}</span>
            </template>
            <template v-for="subItem in item.menus">
              <el-submenu v-if="subItem.menus" :index="subItem.mid" :key="subItem.mid">
                <template slot="title">{{ subItem.mname }}</template>
                <el-menu-item v-for="(threeItem,i) in subItem.menus" :key="i" :index="threeItem.link">
                  {{ threeItem.mname }}
                </el-menu-item>
              </el-submenu>
              <el-menu-item v-else :index="subItem.link" :key="subItem.link">
                {{ subItem.mname }}
              </el-menu-item>
            </template>
          </el-submenu>
        </template>
        <template v-else>
          <el-menu-item :index="item.link" :key="item.link">
            <i :class="item.icon"></i><span slot="title">{{ item.mname }}</span>
          </el-menu-item>
        </template>
      </template>
    </el-menu>

  </div>
</template>

<script>
  import bus from './bus';
  export default {
    data() {
      return {
        collapse: false,
        items: [
          {
            icon: 'el-icon-user-solid',
            index: '1',
            title: '人员管理',
            subs: [
              {
                index: 'infoManager',
                title: '人员信息管理'
              },
              {
                index: 'duty',
                title: '值班管理'
              }
            ]
          },
          {
            icon: 'el-icon-user',
            index: '2',
            title: '医生'
          },
          {
            icon: 'el-icon-coin',
            index: '3',
            title: '前台收银',
            subs: []
          },
          {
            icon: 'el-icon-first-aid-kit',
            index: '4',
            title: '药师',
            subs: []
          },
          {
            icon: 'el-icon-s-home',
            index: '5',
            title: '药库管理',
            subs: []
          }
        ]
      }
    },
    computed:{
      onRoutes(){
        return this.$route.path.replace('/','');
      },
      Initialization(){
        this.items = this.$store.state.user.menuList;
      }

    },
    created(){
      // 通过 Event Bus 进行组件间通信，来折叠侧边栏
      bus.$on('collapse', msg => {
        this.collapse = msg;
      })
      this.Initialization
    }
  }
</script>

<style scoped>
  .sidebar{
    display: block;
    position: absolute;
    left: 0;
    top: 70px;
    bottom:0;
    overflow-y: scroll;
  }
  .sidebar::-webkit-scrollbar{
    width: 0;
  }
  .sidebar-el-menu:not(.el-menu--collapse){
    width: 250px;
  }
  .sidebar > ul {
    height:100%;
  }
</style>
