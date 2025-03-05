<template>
  <div>
    <!-- 如果未登录，则显示登录页面 -->
    <Login v-if="!isAuthenticated" @loginSuccess="handleLoginSuccess" />

    <!-- 如果已登录，则显示管理系统 -->
    <el-container v-else class="layout">
      <el-container class="main-container">
        <!-- 侧边栏菜单 -->
        <el-aside class="sidebar">
          <Sidebar />
        </el-aside>

        <!-- 主内容区域 -->
        <el-main class="content">
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import Sidebar from './components/Sidebar.vue'
import Login from './components/Login.vue'

const router = useRouter()
const isAuthenticated = ref(false)

// 确保 localStorage 获取到正确的值
onMounted(() => {
  isAuthenticated.value = localStorage.getItem('user') === 'true'
})

const handleLoginSuccess = () => {
  localStorage.setItem('user', 'true')
  isAuthenticated.value = true
  console.log('登录成功')
  router.push('/') // 确保页面跳转
}
</script>

<style scoped>
/* 让整个页面铺满视口，避免滚动 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  overflow: hidden; /* 禁止滚动条 */
}

html, body {
  width: 100%;
  height: 100%;
}

/* 整体布局 */
.layout {
  height: 100vh; /* 确保占满整个屏幕 */
  display: flex;
  flex-direction: column;
}

/* 主体部分 */
.main-container {
  flex: 1;
  display: flex;
  width: 100%;
}

/* 侧边栏 */
.sidebar {
  width: 15vw;
  background-color: #2c3e50;
  color: white;
  height: 100%;
  flex-shrink: 0; /* 防止侧边栏缩小 */
}

/* 主内容区域 */
.content {
  flex: 1;
  padding: 20px;
  background-color: #f5f7fa;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
