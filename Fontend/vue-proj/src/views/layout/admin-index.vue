<template>
  <div class="main-wrapper">
    <div class="top-bar">
      <img 
        class="header-logo"
        src="@/assets/logo.png"
        :style="{ width: '76px', height: '75px' }"
      >
      <div class="header-title">
        TPC Benchmark<br>
        电商数据管理系统
      </div>
    </div>
    <el-container class="layout-container">
    <el-aside class="sidebar-container">
      <!-- <div class="sidebar-header">
        <span class="sidebar-title">Menu</span>
      </div> -->
      <el-menu
        class="custom-menu"
        :default-active="activeMenu"
        router
        unique-opened
      >
        <el-sub-menu index="1">
          <template #title>
            <span class="menu-title">系统管理</span>
          </template>
          <el-menu-item index="/system">
            系统管理
          </el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="2">
          <template #title>
            <span class="menu-title">用户管理</span>
          </template>
          <el-menu-item index="/userManage">
            用户管理
          </el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="3">
          <template #title>
            <span class="menu-title">数据管理</span>
          </template>
          <el-menu-item index="/data">
            数据管理
          </el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="4">
          <template #title>
            <span class="menu-title">业务查询</span>
          </template>
          <el-menu-item index="/query">
            业务查询
          </el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="5">
          <template #title>
            <span class="menu-title">业务分析</span>
          </template>
          <el-menu-item index="/analysis">
            业务分析
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>

    <el-container class="content-container">
      <!-- 主要内容区域 -->
      <el-main class="main-container">
        <router-view />
      </el-main>
    </el-container>
      </el-container>
  </div>
</template>

<style scoped>
.main-wrapper {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

.layout-container {
  flex: 1;
  display: flex;
  overflow: hidden;
}

.sidebar-container {
  display: flex;
  flex-direction: column;
  height: 100%;
}
</style>

<script setup>
import { ref } from 'vue'
const activeMenu = ref('/home')
</script>

<style scoped>
.top-bar {
  height: 100px;
  background: #F4F3EA;
  display: flex;
  align-items: center;
  padding: 0 30px;
}

.header-logo {
  margin-right: 20px;
  border-radius: 50%;
  overflow: hidden;
}

.header-title {
  color: #A4A8A9;
  font-size: 1.8rem;
  font-weight: 600;
  display: flex;
  flex-direction: column;
  line-height: 1.2;
  font-weight: bold;
}

.layout-container {
  height: calc(100vh - 80px);
  display: flex;
}

/* 移除原有的.header-container样式 */
</style>

<style scoped>
/* 主体布局 */
.layout-container {
  height: 100vh;
  display: flex; /* 新增 */
}

/* 侧边栏整体样式 */
.el-aside.sidebar-container {
  width: 350px !important;
  height: 100vh; /* 修改为全屏高度 */
  margin-top: 0; /* 移除顶部间距 */
  background-image: url('@/assets/Sidebar_Background.jpeg');
  background-size: cover;
  background-repeat: no-repeat;
  display: flex;
  flex-direction: column;
  padding-top: 0px;
}

/* .sidebar-header {
  display: flex;
  align-items: center;
  padding: 1rem;
  background-color: rgba(255, 255, 255, 0.1);
  border-bottom: 1px solid rgba(255, 255, 255, 0.2);
} */

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
  color: #E6E5D4;
}

/* 菜单项 */
.el-menu-item {
  color: #F4F3EA !important;
  font-size: 1.5rem!important;
  font-weight: bold;
}

/* 菜单项悬停样式 */
.el-menu-item:hover {
  background: rgba(255, 255, 255, 0.2) !important;
}

/* 激活状态 */
.el-menu-item.is-active {
  background: #6CBBB6 !important;
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

/* 主要内容区域父容器 */
.el-container > .el-container {
  flex: 1;
  height: 100vh;
  display: flex;
  flex-direction: column;
}

/* 主内容区域 */
.main-container {
  padding: 0px;
  background: #f4f4f4;
  height: calc(100vh - 60px);
  overflow-y: auto;
  /* 新增以下两行 */
  flex: 1;
  min-height: 0; /* 修复flex布局下的高度计算问题 */
}

/* 调整父容器 */
.el-container > .el-container {
  height: calc(100vh - 60px);
  flex-direction: column;
  /* 新增 */
  display: flex;
  flex: 1;
}
</style>
