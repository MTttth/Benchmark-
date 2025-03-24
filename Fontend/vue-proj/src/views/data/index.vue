<template>
  <div class="container">
    <!-- 数据导出 -->
    <el-card class="box-card">
      <template #header>
        <div class="card-header">数据导出</div>
      </template>
      <div class="export-container">
        <el-select v-model="selectedTable" placeholder="选择数据表" class="select-box">
          <el-option v-for="table in tables" :key="table" :label="table" :value="table" />
        </el-select>

        <el-radio-group v-model="fileType" class="radio-group">
          <el-radio label="xlsx">xlsx</el-radio>
          <el-radio label="txt">TXT</el-radio>
        </el-radio-group>

        <el-button type="primary" @click="handleExport" :disabled="!selectedTable">导出数据</el-button>
      </div>
    </el-card>

    <!-- 数据导入 -->
    <el-card class="box-card">
      <template #header>
        <div class="card-header">数据导入</div>
      </template>
      <div class="import-container">
        <el-select v-model="importTable" placeholder="选择导入的表" class="select-box">
          <el-option v-for="table in tables" :key="table" :label="table" :value="table" />
        </el-select>

        <el-upload
          class="upload-box"
          action=""
          :show-file-list="false"
          :before-upload="handleFileUpload"
        >
          <el-button type="primary">选择文件</el-button>
        </el-upload>

        <el-button type="success" @click="uploadFile" :disabled="!file">上传并导入数据库</el-button>

        <el-progress v-if="progress > 0" :percentage="progress" class="progress-bar" />

        <el-alert v-if="resultMessage" :title="resultMessage" type="success" show-icon />

        <!-- 错误日志 -->
        <el-card v-if="errorLogs.length" class="box-card">
          <template #header>
            <div class="card-header">错误日志</div>
          </template>
          <el-table :data="errorLogs" border class="error-table">
            <el-table-column prop="line" label="行号" width="100" />
            <el-table-column prop="field" label="字段" />
            <el-table-column prop="error" label="错误信息" />
          </el-table>
          <el-button type="danger" @click="downloadErrorLog">下载错误日志</el-button>
        </el-card>

        <!-- 导入日志 -->
        <el-card class="box-card">
          <template #header>
            <div class="card-header">导入日志</div>
          </template>
          <el-table :data="importLogs" border class="log-table">
            <el-table-column prop="rowNumber" label="行号" width="200" />
            <el-table-column prop="status" label="状态" width="200" />
            <el-table-column prop="message" label="日志详情" />
          </el-table>
        </el-card>
      </div>
    </el-card>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      tables: ['订单表 orders', '零部件供应表 partsupp', '订单明细表 lineitem', '供应商表 supplier', '区域表 region', '国家表 nation', '零部件表 part', '客户表 customer'], // 选择的表
      selectedTable: '',
      importTable: '',
      fileType: 'xlsx',
      file: null,
      progress: 0,
      resultMessage: '',
      errorLogs: [], // 存储错误日志
      importLogs: [] // 存储导入日志
    };
  },
  methods: {
    async handleExport() {
      try {
        const response = await axios.post('/api/data/export', {
          tableName: this.selectedTable,
          fileType: this.fileType
        }, { responseType: 'blob' });

        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', `${this.selectedTable}.${this.fileType}`);
        document.body.appendChild(link);
        link.click();
        link.remove();
      } catch (error) {
        console.error('导出失败:', error);
        this.$message.error('导出失败，请重试');
      }
    },
    handleFileUpload(file) {
      this.file = file;
      return false;
    },
    async uploadFile() {
      if (!this.file || !this.importTable) {
        this.$message.error('请选择文件和数据表');
        return;
      }

      const formData = new FormData();
      formData.append('file', this.file);
      formData.append('tableName', this.importTable);

      try {
        const response = await axios.post('/api/data/upload', formData, {
          headers: { 'Content-Type': 'multipart/form-data' },
          onUploadProgress: (progressEvent) => {
            this.progress = Math.round((progressEvent.loaded / progressEvent.total) * 100);
          }
        });

        this.resultMessage = `导入成功，插入 ${response.data.data.rowsInserted} 行`;

        // 处理错误日志
        if (response.data.errors.length) {
          this.errorLogs = response.data.errors;
          this.$message.warning('部分数据未导入，请查看错误日志');
        }

        // 处理导入日志
        this.importLogs = response.data.data.logs || [];

      } catch (error) {
        this.resultMessage = `导入失败: ${error.response?.data?.message || '未知错误'}`;
      }
    },
    downloadErrorLog() {
      const blob = new Blob([JSON.stringify(this.errorLogs, null, 2)], { type: 'application/json' });
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', 'import_error_log.json');
      document.body.appendChild(link);
      link.click();
      link.remove();
    }
  }
};
</script>

<style scoped>
.container {
  max-width: 800px;
  margin: 2rem auto;
}
.box-card {
  margin-bottom: 1.5rem;
  padding: 1rem;
}
.card-header {
  font-size: 1.2rem;
  font-weight: bold;
}
.export-container, .import-container {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  align-items: center;
}
.select-box {
  width: 200px;
}
.radio-group {
  display: flex;
  gap: 1rem;
}
.upload-box {
  display: flex;
}
.progress-bar {
  width: 100%;
  margin-top: 1rem;
}
.error-table, .log-table {
  margin-top: 1rem;
  width: 100%;
}
</style>
