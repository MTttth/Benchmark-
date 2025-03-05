<template>
	<div class="sidebar-container">
	  <!-- 顶部 Logo & 标题 -->
	  <div class="sidebar-header">
		<img
		  class="sidebar-logo"
		  src="https://ide.code.fun/api/image?token=67c5ae554ae84d001227f8a0&name=692469702e76c783e07de02e48568ca8.png"
		/>
		<span class="sidebar-title">Benchmark 电商管理系统</span>
	  </div>
  
	  <!-- 侧边栏菜单 -->
	  <el-menu
		class="custom-menu"
		:default-active="activeMenu"
		router
	  >
		<el-sub-menu index="1">
		  <template #title><span class="menu-title">系统管理</span></template>
		  <el-menu-item index="/system">系统设置</el-menu-item>
		</el-sub-menu>
  
		<el-sub-menu index="2">
		  <template #title><span class="menu-title">用户管理</span></template>
		  <el-menu-item index="/user">用户列表</el-menu-item>
		</el-sub-menu>
  
		<el-sub-menu index="3">
		  <template #title><span class="menu-title">数据管理</span></template>
		  <el-menu-item index="/data">数据导入</el-menu-item>
		</el-sub-menu>
  
		<el-sub-menu index="4">
		  <template #title><span class="menu-title">业务查询</span></template>
		  <el-menu-item index="/query">查询功能</el-menu-item>
		</el-sub-menu>
  
		<el-sub-menu index="5">
		  <template #title><span class="menu-title">业务分析</span></template>
		  <el-menu-item index="/analysis">数据分析</el-menu-item>
		</el-sub-menu>
	  </el-menu>
	</div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  const activeMenu = ref('/home')
  </script>
  
  <style scoped>
  /* 侧边栏容器 */
  .sidebar-container {
	width: 260px;
	height: 100vh;
	background-image: url(https://codefun-proj-user-res-1256085488.cos.ap-guangzhou.myqcloud.com/67c311d8d54496f19faa52f7/67c312e64ae84d001227c1a4/17410084447802624261.png);
	background-size: cover;
	background-repeat: no-repeat;
	display: flex;
	flex-direction: column;
	padding-top: 10px;
  }
  
  /* 顶部 Logo & 标题 */
  .sidebar-header {
	display: flex;
	align-items: center;
	padding: 1rem;
	background-color: #f4f3e9;
  }
  
  .sidebar-logo {
	width: 50px;
	height: 50px;
	border-radius: 50%;
  }
  
  .sidebar-title {
	font-size: 1.2rem;
	font-weight: bold;
	color: #a4a8a9;
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
	color: #e6e5d4;
  }
  </style>
  