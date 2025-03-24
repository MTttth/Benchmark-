import Vue from 'vue'
import VueRouter, { RouteConfig } from 'vue-router'

Vue.use(VueRouter)

const routes: Array<RouteConfig> = [
  {
    path: '/',
    redirect: '/login' // 确保默认打开的是 /login
  },
  {
    path: '/admin',
    name: 'admin-home',
    //redirect: '/userManage',
    component: () =>
      import('@/views/layout/admin-index.vue'),
    children: [
          {
            // 用户管理
            path: '/userManage',
            component: () =>
                import(/* webpackChunkName: "login" */ '@/views/user/index.vue')
          },
          {
            path: '/userManage/add',
            component: () =>
              import( '@/views/user/addUser.vue'),
          },
          {
            path: '/system',
            component: () =>
              import( '@/views/system/index.vue'),
          },
          {
            path: '/data',
            component: () =>
              import( '@/views/data/index.vue'),
          },
          {
            path: '/query',
            component: () =>
              import( '@/views/query/index.vue'),
          },
          {
            path: '/analysis',
            component: () =>
              import( '@/views/analysis/index.vue'),
          },
          {
            path: '/dataExport',
            component: () =>
              import( '@/views/data/dataExport.vue'),
          },
          {
            path: '/dataUpload',
            component: () =>
              import( '@/views/data/dataUpload.vue'),
          },
    ]
  },
  {
    path: '/login',
    component: () =>
      import(/* webpackChunkName: "login" */ '@/views/login/index.vue'),
    meta: { title: '电商管理系统', hidden: true, notNeedAuth: true }
  },
  {
    path: '/user',
    name: 'user-home',
    //redirect: '/userManage',
    component: () =>
      import('@/views/layout/user-index.vue'),
    children: [
          {
            path: '/user/data',
            component: () =>
              import( '@/views/data/index.vue'),
          },
          {
            path: '/user/query',
            component: () =>
              import( '@/views/query/index.vue'),
          },
          {
            path: '/user/analysis',
            component: () =>
              import( '@/views/analysis/index.vue'),
          }

    ]
  }

]

const router = new VueRouter({
  routes
})

export default router
