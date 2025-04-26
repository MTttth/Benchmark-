<template>
  <el-container>
    <!-- 左侧侧边栏：数据库信息 + 数据库参数
    <el-aside width="600px">

    </el-aside> -->

    <!-- 右侧主要内容区：数据库用户列表 -->
    <el-main>
      <el-card class="connection">
        <h3>当前数据库连接</h3>
        <el-table :data="dbInfo.user" class="connection-tab">
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

      <div class="bottom-lay">
        <el-card class="connection-info">
        <h3>数据库连接信息</h3>
        <el-descriptions class="info">
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
        <el-button class="btn" @click="fetchDbInfo" style="margin-top: 10px">
          <span class="btn-text">刷新</span>
        </el-button>
      </el-card>

      <!-- <el-divider /> -->

      <el-card class="setting">
        <h3>数据库参数设置</h3>
        <el-form :model="dbParams" label-width="100px">
          <el-form-item label="连接时长">
            <el-input v-model="dbParams.timeout" />
          </el-form-item>
          <el-form-item label="缓冲区大小">
            <el-input v-model="dbParams.bufferSize" />
          </el-form-item>
          <el-form-item>
            <el-button class="btn" @click="saveConfig">
              <span class="btn-text">保存</span>
            </el-button>
          </el-form-item>
        </el-form>
      </el-card>
      </div>
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

/* *{
  border:1px solid #000000;
} */

.el-container {
  background-color: #6CBBB6;
  height: 90vh;
}

.connection, .connection-info, .setting {
  position: relative;

}

h3{
  color:#333;
  font-size:20px;
  font-weight:bolder;
  position:absolute;
  left:10px;
  top:0px;
}
/* .el-aside {
  padding: 20px;
  background: #6CBBB6;
  min-height: 90vh;
} */

.el-main {
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap:20px;
}

.connection{
  height:300px;
  width:1200px;
  margin:10px;  
  background-color: #BCBCBC;
  border:4px solid #A9AFB1;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.connection-tab{
  height:200px;
  width:1100px;
  border-radius: 10px;
  overflow: auto;
}

.bottom-lay{
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  width:1200px;
}

.connection-info{
  height:300px;
  width:550px;
  background-color: #BCBCBC;
  border:4px solid #A9AFB1;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  /* margin:10px;   */
}

:deep(.info.el-descriptions) {
  margin: 10px 0;
  border-radius: 10px;
  padding: 10px;
  background: #f5f7fa; /* 浅灰色背景 */
}

:deep( .el-descriptions-item__label) {
  font-size:15px;
  font-weight: bolder;
  color: #A9AFB1;
  /* text-align: right; */
}

/* 内容列样式 */
:deep(.el-descriptions-item__content) {
  font-size: 14px;
  color: #333; /* 深色文本 */
}

.btn{
  background-color: #A9AFB1;
  border-radius: 10px;
  border:0px;
}

.btn-text{
  color: #FFFFFF;
  font-weight:bolder;
  font-size: 16px;
}

.setting{
  height:300px;
  width:550px;
  background-color: #BCBCBC;
  border:4px solid #A9AFB1;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  /* margin:10px;   */
}
/* .el-card {
  margin-bottom: 20px;
} */

</style>
