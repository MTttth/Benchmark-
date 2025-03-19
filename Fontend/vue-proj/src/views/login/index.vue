<template>
  <div class="login-container">
    <!-- 背景图片 -->
    <div class="login-form">
      <form>
        <!-- 用户名输入框 -->
        <div class="form-group">
          <label for="username">Username</label>
          <el-input v-model="loginForm.username" placeholder="请输入账户" type="text" />
        </div>
        
        <!-- 密码输入框 -->
        <div class="form-group">
          <label for="password">Password</label>
          <el-input v-model="loginForm.password" placeholder="请输入密码" show-password type="password" />
        </div>

        <!-- 角色选择 -->
        <div class="form-group">
          <el-radio-group v-model="loginForm.type" style="display: flex;justify-content: center;">
            <el-radio :label="0">
              管理员
            </el-radio>
            <el-radio :label="1">
              用户
            </el-radio>
          </el-radio-group>
        </div>

        <!-- 登录按钮 -->
        <button type="submit" class="submit-btn" @click="handleLogin">
          Sign in
        </button>
      </form>
    </div>
  </div>
</template>

<script lang="ts">
import { Form as ElForm, Input } from 'element-ui'
import { Route } from 'vue-router'
import { Component, Vue, Watch } from 'vue-property-decorator'
import { UserModule } from '@/store/modules/user'

@Component({
  components: {
    'el-input': Input
  }
})
export default class extends Vue {
  private loginForm = {
    username: 'admin',
    password: '123456',
    type: 1,
  } as {
    username: String
    password: String
    type: Number
  }

  private redirect?: string

  @Watch('$route', { immediate: true })
  private onRouteChange(route: Route) {}

  // 登录方法
  private async handleLogin() {
  await UserModule.Login(this.loginForm as any)
    .then((res: any) => {
      if (String(res.code) === '1') {
        const userType = res.data.type // 获取用户类型
        if (userType === 0) {
          this.$router.push('/admin') // 管理员页面
        } else {
          this.$router.push('/user') // 普通用户页面
        }
      } else {
        this.$message.error(res.msg)
      }
    })
    .catch(() => {
      this.$message.error('用户名或密码错误！')
    })
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