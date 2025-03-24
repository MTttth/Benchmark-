<template>
  <el-container class="layout-container">
    <!-- 侧边栏 -->
    <el-aside class="sidebar-container">
      <!-- Logo & 标题 -->
      <div class="sidebar-header">
        <img
          class="sidebar-logo"
          src="@/assets/logo.png"
        >
        <span class="sidebar-title">Menu</span>
      </div>

      <!-- 侧边栏菜单 -->
      <el-menu
        class="custom-menu"
        :default-active="activeMenu"
        router
        unique-opened
      >
        <el-sub-menu index="3">
          <template #title>
            <span class="menu-title">数据管理</span>
          </template>
          <el-menu-item index="/user/data">
            数据管理
          </el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="4">
          <template #title>
            <span class="menu-title">业务查询</span>
          </template>
          <el-menu-item index="/user/query">
            业务查询
          </el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="5">
          <template #title>
            <span class="menu-title">业务分析</span>
          </template>
          <el-menu-item index="/user/analysis">
            业务分析
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>

    <!-- 主要内容区域 -->
    <el-container>
      <el-header class="header-container">
        TPC Benchmark 电商数据管理系统
      </el-header>
      <el-main class="main-container">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref } from 'vue'
const activeMenu = ref('/home')
</script>

<style scoped>
/* 主体布局 */
.layout-container {
  height: 100vh;
}

/* 侧边栏整体样式 */
.sidebar-container {
  width: 250px;
  height: 100vh;
  background: linear-gradient(135deg, #2c3e50 10%, #34495e 90%);
  display: flex;
  flex-direction: column;
  padding-top: 10px;
}

/* 顶部 Logo & 标题 */
.sidebar-header {
  display: flex;
  align-items: center;
  padding: 1rem;
  background-color: rgba(255, 255, 255, 0.1);
  border-bottom: 1px solid rgba(255, 255, 255, 0.2);
}

.sidebar-logo {
  width: 45px;
  height: 45px;
  border-radius: 50%;
}

.sidebar-title {
  font-size: 1.1rem;
  font-weight: bold;
  color: #ffffff;
  margin-left: 10px;
}

/* 菜单样式 */
.custom-menu {
  background: transparent !important;
  border-right: none;
  width: 100%;
}

/* 目录标题样式 */
.menu-title {
  font-size: 1rem;
  font-weight: bold;
  color: #ffffff;
}

/* 菜单项 */
.el-menu-item {
  color: #ffffff !important;
}

/* 菜单项悬停样式 */
.el-menu-item:hover {
  background: rgba(255, 255, 255, 0.2) !important;
}

/* 激活状态 */
.el-menu-item.is-active {
  background: rgba(255, 255, 255, 0.3) !important;
  font-weight: bold;
  border-radius: 5px;
}

/* 头部区域 */
.header-container {
  height: 60px;
  background: #2c3e50;
  color: white;
  display: flex;
  align-items: center;
  padding: 0 20px;
  font-size: 1.2rem;
  font-weight: bold;
}

/* 主内容区域 */
.main-container {
  padding: 20px;
  background: #f4f4f4;
  flex-grow: 1;
}
</style>
