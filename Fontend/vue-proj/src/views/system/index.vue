<template>
  <el-container>
    <!-- 侧边栏 -->
    <el-aside width="300px">
      <el-card>
        <h3>数据库连接信息</h3>
        <el-descriptions>
          <el-descriptions-item label="服务器地址">
            {{ dbInfo.host }}
          </el-descriptions-item>
          <el-descriptions-item label="数据库">
            {{ dbInfo.database }}
          </el-descriptions-item>
          <el-descriptions-item label="用户">
            {{ dbInfo.user }}
          </el-descriptions-item>
          <el-descriptions-item label="连接状态">
            <el-tag :type="dbInfo.connected ? 'success' : 'danger'">
              {{ dbInfo.connected ? "已连接" : "断开" }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="物理分区">
            {{ dbInfo.partition }}
          </el-descriptions-item>
        </el-descriptions>
        <el-button type="primary" @click="fetchDbInfo">
          刷新
        </el-button>
      </el-card>
    </el-aside>

    <!-- 主内容区 -->
    <el-main>
      <el-card>
        <h3>数据库参数设置</h3>
        <el-form ref="dbForm" :model="dbParams" label-width="120px">
          <el-form-item label="连接时长">
            <el-input v-model.number="dbParams.timeout" type="number" />
          </el-form-item>
          <el-form-item label="缓冲区大小">
            <el-input v-model.number="dbParams.bufferSize" type="number" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="saveConfig">
              保存
            </el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-main>
  </el-container>
</template>

<script>
import axios from 'axios';
import { Message } from 'element-ui';

export default {
  data() {
    return {
      dbInfo: {
        host: '127.0.0.1',
        database: 'test_db',
        user: 'root',
        connected: false,
        partition: '/var/lib/mysql',
      },
      dbParams: {
        timeout: 30, // 连接时长（秒）
        bufferSize: 1024, // 缓冲区大小（MB）
      },
    };
  },
  mounted() {
    this.fetchDbInfo();
  },
  methods: {
    // 获取数据库连接信息
    async fetchDbInfo() {
      try {
        const response = await axios.get('/api/db/info');
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
    async saveConfig() {
      try {
        const response = await axios.post('/api/db/update-config', this.dbParams);
        if (response.data.code === 1) {
          Message.success('数据库配置已更新！');
          this.fetchDbInfo(); // 重新获取最新数据库状态
        } else {
          Message.error(response.data.msg);
        }
      } catch (error) {
        Message.error('更新数据库配置失败！');
      }
    },
  },
};
</script>

<style scoped>
.el-aside {
  padding: 20px;
  background: #f8f9fa;
}
</style>
