<template>
	<div class="login-container">
	  <el-card class="login-card">
		<h2>系统登录</h2>
		<el-form :model="form" label-width="80px">
		  <el-form-item label="用户名">
			<el-input v-model="form.username" />
		  </el-form-item>
		  <el-form-item label="密码">
			<el-input v-model="form.password" type="password" show-password />
		  </el-form-item>
		  <el-form-item>
			<el-button type="primary" @click="login">登录</el-button>
			<el-button @click="register">注册</el-button>
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
  const emit = defineEmits(['loginSuccess']) // 触发事件
  const login = () => {
  if (form.username === 'admin' && form.password === '123456') {
    ElMessage.success('登录成功！')
    localStorage.setItem('user', 'true') // 存储登录状态
    emit('loginSuccess') // 通知父组件
    router.push('/home')
  } else {
    ElMessage.error('用户名或密码错误')
  }
}
  
  const register = () => {
	router.push('/register')
  }
  
  </script>
  <style scoped>
  .login-container {
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	background-color: #f5f5f5;
  }
  .login-card {
	width: 400px;
	padding: 20px;
  }
  </style>