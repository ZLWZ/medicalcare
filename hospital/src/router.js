import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)



//公共路由  静态
export const constantRouterMap = [
  {
    path:'/login',
    name:'Login',
    component:()=>import('./components/Login.vue')
  },
  {
    path: '/',
    name:'/',
    component: () => import('./components/common/Home'),
    meta: { title: '自述文件' },
    children:[
      {
        path: '/doctor',
        component: () => import('./components/page/doctor/Doctor'),
        meta: { title: '医生诊断' }
      },
      {
        path: '/duty',
        component: () => import('./components/page/manager/Duty'),
        meta: { title: '值班管理' }
      },
      {
        path: '/infoManager',
        component: () => import('./components/page/manager/InfoManager'),
        meta: { title: '员工管理' }
      },
      {
        path:'/resign',
        component:()=> import('./components/page/manager/Resign'),
        meta: { title: '离职记录' }
      },
      {
        path: '/jurisdiction',
        component: () => import('./components/page/jurisdiction/Jurisdiction.vue'),
        meta: { title: '角色管理' }
      },{
        path:'/registerVisi',
        component:()=> import('./components/page/cashier/RegisterVisi'),
        meta: { title: '挂号预约' }
      },
      {
        path:'/registerInfo',
        component:()=> import('./components/page/cashier/RegisterInfo'),
        meta: { title: '挂号查询' }
      },
      {
        path:'/prescript',
        component:()=> import('./components/page/pharmacy/Prescript'),
        meta: { title: '处理药方' }
      },
      {
        path:'/inquiries',
        component:()=> import('./components/page/pharmacy/Inquiries'),
        meta: { title: '药品查询' }
      },
      {
        path:'/replenishment',
        component:()=> import('./components/page/pharmacy/Replenishment'),
        meta: { title: '申请补给' }
      },
      {
        path:'/waste',
        component:()=> import('./components/page/medicine/Waste'),
        meta: { title: '浪费药物' }
      },
      {
        path:'/storehouse',
        component:()=> import('./components/page/medicine/Storehouse'),
        meta: { title: '药库查询' }
      },
      {
        path:'/delivery',
        component:()=> import('./components/page/medicine/Delivery'),
        meta: { title: '药物出药' }
      },
      {
        path:'/personalCenter',
        component:() => import('./components/page/personalCenter/PersonalCenter'),
        meta: {title:'个人中心'}
      }
    ]
  },
  {
    path: '/403',
    component: () => import('./components/common/403.vue'),
    meta: { title: '没有权限' }
  },
  {path:'/**',component: () => import('./components/common/404.vue')},
]




export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: constantRouterMap
})
