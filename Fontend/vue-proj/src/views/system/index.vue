<template>
  <el-container>
    <!-- 左侧侧边栏：数据库信息 + 数据库参数 -->
    <el-aside width="600px">
      <el-card>
        <h3>数据库连接信息</h3>
        <el-descriptions>
          <el-descriptions-item label="服务器地址">
            {{ dbInfo.host }}
          </el-descriptions-item>
          <el-descriptions-item label="物理分区">
            {{ dbInfo.partition }}
          </el-descriptions-item>
          <el-descriptions-item label="当前连接数">
            {{ dbInfo.user.length }}
          </el-descriptions-item>
        </el-descriptions>
        <el-button type="primary" @click="fetchDbInfo" style="margin-top: 10px">刷新</el-button>
      </el-card>

      <el-divider />

      <el-card>
        <h3>数据库参数设置</h3>
        <el-form :model="dbParams" label-width="100px">
          <el-form-item label="连接时长">
            <el-input v-model="dbParams.timeout" />
          </el-form-item>
          <el-form-item label="缓冲区大小">
            <el-input v-model="dbParams.bufferSize" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="saveConfig">保存</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-aside>

    <!-- 右侧主要内容区：数据库用户列表 -->
    <el-main>
      <el-card>
        <h3>当前数据库连接</h3>
        <el-table :data="dbInfo.user" border style="width: 100%">
          <el-table-column prop="db" label="数据库" width="200">
            <template v-slot="scope">
              {{ scope.row.db || '未指定' }}
            </template>
          </el-table-column>
          <el-table-column prop="host" label="主机" width="200" />
          <el-table-column prop="time" label="已连接时长（秒）" width="150" />
          <el-table-column prop="user" label="用户" width="150" />
          <el-table-column prop="command" label="执行的命令" width="300" />
        </el-table>
      </el-card>
    </el-main>
  </el-container>
</template>

<script>
import axios from 'axios';
import { Message } from 'element-ui';
import { fetchDbInfo, saveDbConfig } from '@/api/db';

export default {
  data() {
    return {
      dbInfo: {
        host: '127.0.0.1',
        partition: '/var/lib/mysql',
        user: [] // 存储数据库连接列表
      },
      dbParams: {
        timeout: 30, // 连接时长（秒）
        bufferSize: 1024 // 缓冲区大小（MB）
      }
    };
  },
  mounted() {
    this.loadDbInfo();
  },
  methods: {
    // 获取数据库连接信息
    async loadDbInfo() {
      try {
        const response = await fetchDbInfo();
        if (response.data.code === 1) {
          this.dbInfo = response.data.data;
        } else {
          Message.error(response.data.msg);
        }
      } catch (error) {
        Message.error('无法获取数据库信息！');
      }
    },

    // 保存数据库参数
    async updateConfig() {
      try {
        const response = await saveDbConfig(this.dbParams);
        if (response.data.code === 1) {
          Message.success('数据库配置已更新！');
          this.loadDbInfo(); // 重新获取数据库状态
        } else {
          Message.error(response.data.msg);
        }
      } catch (error) {
        Message.error('更新数据库配置失败！');
      }
    }
  }
};
</script>

<style scoped>
.el-aside {
  padding: 20px;
  background: #f8f9fa;
  min-height: 100vh;
}

.el-main {
  padding: 20px;
}

.el-card {
  margin-bottom: 20px;
}
</style>
