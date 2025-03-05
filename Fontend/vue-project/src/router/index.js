import { createRouter, createWebHistory } from 'vue-router'
import Login from '@/components/Login.vue'
import Register from '@/components/Register.vue'
import Home from '@/components/Home.vue'
import SystemManage from '@/components/SystemManage.vue'
import UserManage from '@/components/UserManage.vue'
import DataManage from '@/components/DataManage.vue'
import BusinessQuery from '@/components/BusinessQuery.vue'
import BusinessAnalysis from '@/components/BusinessAnalysis.vue'

const routes = [
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  { path: '/home', component: Home },
  { path: '/system', component: SystemManage },
  { path: '/user', component: UserManage },
  { path: '/data', component: DataManage },
  { path: '/query', component: BusinessQuery },
  { path: '/analysis', component: BusinessAnalysis }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
