import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)



//公共路由  静态
export const constantRouterMap = [
  {path:'/404',component: () => import('./components/common/404.vue')},
  {
    path:'/login',
    name:'Login',
    component:()=>import('./components/Login.vue')
  }
  ,
  {
    path: '/',
    name:'/',
    component: () => import('./components/common/Home'),
    meta: { title: '自述文件' },
    children:[
      {
        path: '/duty',
        component: () => import('./components/page/manager/Duty'),
        meta: { title: '值班管理' }
      },
      {
        path: '/doctor',
        component: () => import('./components/page/doctor/Doctor'),
        meta: { title: '医生诊断' }
      },
      {
        path: '/infoManager',
        component: () => import('./components/page/manager/InfoManager'),
        meta: { title: '人员信息管理' }
      },
      {
        path: '/jurisdiction',
        component: () => import('./components/page/jurisdiction/Jurisdiction.vue'),
        meta: { title: '角色管理' }
      }
    ]
  },
  {
    path: '/403',
    component: () => import('./components/common/403.vue'),
    meta: { title: '没有权限' }
  }
]




export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: constantRouterMap
})
