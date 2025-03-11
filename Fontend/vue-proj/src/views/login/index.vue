<template>
  <div class="login-container">
    <!-- <img src="@/assets/logo.png" alt="Logo" class="logo"> -->

    <div class="login-form">
      <h2 class="title">
        Sign in
      </h2>
      <form @submit.prevent="handleSubmit">
        <div class="form-group">
          <label for="username">Username</label>
          <input
            id="username"
            v-model="form.username"
            type="text"
            placeholder="Enter your username"
          >
        </div>

        <div class="form-group">
          <label for="password">Password</label>
          <input
            id="password"
            v-model="form.password"
            type="password"
            placeholder="Enter your password"
          >
        </div>

        <button type="submit" class="submit-btn">
          Sign in
        </button>
      </form>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        form: {
          username: '',
          password: ''
        }
      }
    },
    methods: {
      handleSubmit() {
        // 这里添加登录逻辑
        console.log('登录信息：', this.form)
      }
    }
  }
  </script>

  <style>
  * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
  }

  .login-container {
    height: 100vh;
    background: url('@/assets/background.png') no-repeat center center fixed;
    background-size: cover;
    display: flex;
    justify-content: flex-end;
    align-items: center;
    padding-right: 15%;
  }

  .logo {
    position: absolute;
    top: 30px;
    left: 30px;
    height: 40px;
  }

  .login-form {
    background: #F4F3EA;
    padding: 40px;
    border-radius: 10px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    width: 655px;
    height: 375px;
  }

  .title {
    text-align: center;
    color: #61ABAD;
    margin-bottom: 30px;
    font-size: 24px;
  }

  .form-group {
    margin-bottom: 20px;
  }

  label {
    display: block;
    margin-bottom: 8px;
    color: #666;
    font-size: 14px;
  }

  input {
    width: 100%;
    padding: 12px;
    border: 1px solid #ddd;
    border-radius: 6px;
    font-size: 14px;
    transition: border-color 0.3s;
  }

  input:focus {
    border-color: #409eff;
    outline: none;
  }

  .submit-btn {
    width: 100%;
    padding: 12px;
    background-color: #409eff;
    color: white;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    font-size: 16px;
    transition: background-color 0.3s;
  }

  .submit-btn:hover {
    background-color: #66b1ff;
  }
  </style>
