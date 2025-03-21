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
      </div>
    </el-card>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      tables: ['orders', 'customers', 'products'],
      selectedTable: 'region',
      fileType: 'xlsx',
      file: null,
      progress: 0,
      resultMessage: ''
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
      if (!this.file) {
        this.$message.error('请选择文件');
        return;
      }

      const formData = new FormData();
      formData.append('file', this.file);
      formData.append('tableName', this.selectedTable);

      try {
        const response = await axios.post('/api/data/upload', formData, {
          headers: { 'Content-Type': 'multipart/form-data' },
          onUploadProgress: (progressEvent) => {
            this.progress = Math.round((progressEvent.loaded / progressEvent.total) * 100);
          }
        });

        this.resultMessage = `导入成功，插入 ${response.data.data.rowsInserted} 行`;
      } catch (error) {
        this.resultMessage = `导入失败: ${error.response?.data?.message || '未知错误'}`;
      }
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
</style>