<template>
  <div class="add-user-container">
    <!-- 顶部标题 -->
    <HeadLable title="添加用户" :goback="true" />

    <div class="form-container">
      <el-card class="form-card">
        <el-form
          ref="userForm"
          :model="formData"
          :rules="rules"
          label-width="100px"
          class="user-form"
        >
          <!-- 账号输入框 -->
          <el-form-item label="账号:" prop="username">
            <el-input
              v-model="formData.username"
              placeholder="请输入账号"
              maxlength="20"
            />
          </el-form-item>

          <!-- 姓名输入框 -->
          <el-form-item label="姓名:" prop="name">
            <el-input
              v-model="formData.name"
              placeholder="请输入姓名"
              maxlength="12"
            />
          </el-form-item>

          <!-- 密码输入框 -->
          <el-form-item label="密码:" prop="password">
            <el-input
              v-model="formData.password"
              type="password"
              autocomplete="off"
              placeholder="请输入密码"
              show-password
            />
          </el-form-item>

          <!-- 提交按钮 -->
          <el-form-item>
            <el-button
              type="primary"
              class="submit-btn"
              :loading="loading"
              @click="submitForm"
            >
              添加用户
            </el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </div>
</template>

<script lang="ts">
import { addUser } from '@/api/user';
import { defineComponent, ref } from 'vue';
import { Message } from 'element-ui';
export default defineComponent({
  setup() {
    // 表单数据
    const formData = ref({
      username: '',
      name: '',
      password: '',
    });

    // 表单验证规则
    const rules = {
      username: [
        { required: true, message: '请输入账号', trigger: 'blur' },
        { min: 3, max: 20, message: '账号长度在 3 到 20 之间', trigger: 'blur' },
      ],
      name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
      password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
    };

    // 防止重复提交
    const loading = ref(false);

    // 提交表单
    const submitForm = async () => {
      try {
        loading.value = true;
        const response = await addUser(formData.value);
        if (response.data.code === 1) {
          Message.success('员工添加成功！');
          formData.value = { username: '', name: '', password: '' };
        } else {
          Message.error(response.data.msg);
        }
      } catch (error) {
        Message.error(error.data?.msg || '添加失败');
      } finally {
        loading.value = false;
      }
    };

    return {
      formData,
      rules,
      submitForm,
      loading,
    };
  },
});
</script>

<style lang="scss" scoped>
.add-user-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa, #c3cfe2);
}

.form-container {
  width: 100%;
  max-width: 400px;
  margin-top: 20px;
}

.form-card {
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  background: #fff;
}

.user-form {
  .el-form-item {
    margin-bottom: 20px;
  }

  .el-input {
    width: 100%;
  }

  .submit-btn {
    width: 100%;
    height: 40px;
    border-radius: 8px;
  }
}
</style>
