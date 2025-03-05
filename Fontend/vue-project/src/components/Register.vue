<template>
	<div class="register-container">
	  <el-card class="register-card">
		<h2>用户注册</h2>
		<el-form :model="form" label-width="80px">
		  <el-form-item label="用户名">
			<el-input v-model="form.username" />
		  </el-form-item>
		  <el-form-item label="密码">
			<el-input v-model="form.password" type="password" show-password />
		  </el-form-item>
		  <el-form-item>
			<el-button type="primary" @click="register">注册</el-button>
			<el-button @click="backToLogin">返回</el-button>
		  </el-form-item>
		</el-form>
	  </el-card>
	</div>
  </template>
  
  <script setup>
  import { reactive } from 'vue'
  import { useRouter } from 'vue-router'
  import { ElMessage } from 'element-plus'
  
  const router = useRouter()
  const form = reactive({
	username: '',
	password: ''
  })
  
  const register = () => {
	ElMessage.success('注册成功，请登录！')
	router.push('/')
  }
  
  const backToLogin = () => {
	router.push('/')
  }
  </script>
  
  <style scoped>
  .register-container {
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	background-color: #f5f5f5;
  }
  .register-card {
	width: 400px;
	padding: 20px;
  }
  </style>
  