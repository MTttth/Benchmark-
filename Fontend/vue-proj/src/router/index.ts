import Vue from 'vue'
import VueRouter, { RouteConfig } from 'vue-router'

Vue.use(VueRouter)

const routes: Array<RouteConfig> = [
  {
    path: '/',
    name: 'home',
    // redirect: '/userManage',
    component: () =>
      import('@/views/layout/index.vue'),
    children: [
          {
            path: '/userManage',
            component: () =>
                import(/* webpackChunkName: "login" */ '@/views/user/index.vue')
          },
          {
            path: '/user/add',
            component: () =>
              import( '@/views/user/addUser.vue'),
          }
    ]
  },
  {
    path: '/login',
    component: () =>
      import(/* webpackChunkName: "login" */ '@/views/login/index.vue'),
    meta: { title: '电商管理系统', hidden: true, notNeedAuth: true }
  },

]

const router = new VueRouter({
  routes
})

export default router
