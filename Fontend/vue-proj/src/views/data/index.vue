<template>
  <div class="container">

    <!-- 数据导入 -->
    <div class="header-container">
      <span class="card-header">
        数据导入
      </span>
      </div>
    <el-card class="import-card">
      <!-- <template #header>
        <div class="card-header">数据导入</div>
      </template> -->

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

    <!-- 数据导出 -->
    <div class="header-container">
      <span class="card-header">
         数据导出
      </span>
    </div>
    <el-card class="export-card">
      <!-- <template #header>
        <div class="card-header">数据导出</div>
      </template> -->
      <div class="export-container">
        <el-select v-model="selectedTable" placeholder="选择数据表" class="select-box">
          <el-option v-for="table in tables" :key="table" :label="table" :value="table" />
        </el-select>

        <el-radio-group v-model="fileType" class="radio-group">
          <el-radio label="xlsx" class="x-sel">xlsx</el-radio>
          <el-radio label="txt" class="t-sel">txt</el-radio>
        </el-radio-group>

        <el-button type="primary" @click="handleExport" :disabled="!selectedTable">导出数据</el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      tables: ['订单表 orders', '零部件供应表 partsupp', '订单明细表 lineitem', '供应商表 supplier', '区域表 Region', '国家表 nation', '零部件表 part', '客户表 customer'], // 选择的表
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
      const chunkSize = 1024 * 1024; 
      const file = this.file;
      let start = 0;

      while (start < file.size) {
        let end = Math.min(start + chunkSize, file.size);
        const scanStart = Math.max(start, end - 2048); 
        const scanChunk = file.slice(scanStart, end);
        const scanText = await new Response(scanChunk).text();
        let lastNewLinePos = -1;
        for (let i = scanText.length - 1; i >= 0; i--) {
          if (scanText[i] === '\n') {
            lastNewLinePos = i;
            if (i > 0 && scanText[i - 1] === '\r') {
              lastNewLinePos--;
            }
            break;
          }
        }
        if (lastNewLinePos !== -1) {
          end = scanStart + lastNewLinePos + (scanText[lastNewLinePos] === '\r' ? 2 : 1);
        }
        const chunk = file.slice(start, end);
        const formData = new FormData();
        formData.append('file', chunk);
        formData.append('tableName', this.importTable);
        const response = await axios.post('/api/data/upload', formData, {
          headers: { 'Content-Type': 'multipart/form-data' },
        });
        start = end;
        this.progress = Math.round((start / file.size) * 100);
        if (response.data.code === 1) {
          this.resultMessage = `导入成功，插入 ${response.data.data.rowsInserted} 行`;
          this.importLogs = response.data.data.importLog || [];
        } else {
          this.errorLogs.push(...response.data.data.errorLogs)
          this.$message.warning('部分数据未导入，请查看错误日志');
        }
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
/* *{
  border:1px solid red;
} */
.container {
  height:90vh;
  background-color: #6CBBB6;
  display: flex;
  flex-direction: column;
  padding:20px;
  gap:10px;
  overflow: hidden;
}

.export-card{
  background-color: #BCBCBC;
  border:4px solid #A9AFB1;
  border-radius: 10px;
  margin-left:10px;
  width:1170px;
  height:200px;
}

.header-container{
  display: flex;
  position:relative;
  left:10px;
  bottom:-10px;
}

.card-header {
  font-size: 25px;
  font-weight: bolder;
  color:#E6E5D4;
  /* margin-right: 1000px;
  margin-bottom: 20px; */
}

.box-card {
  margin-bottom: 1.5rem;
  padding: 1rem;
}

.export-container, .import-container {
  display: flex;
  flex-direction: column;
  gap: 10px;
  align-items: center;
}
.select-box {
  width: 200px;
}

.radio-group {
  display: flex;
  flex-direction: column;
  /* justify-content: space-between; */
  gap: 10px;
}

:deep(.el-radio) {
  margin-right: 20px; /* 统一间距 */
}

:deep(.el-radio__label) {
  min-width: 30px; /* 设置统一的最小宽度 */
  display: inline-block;
  text-align: center; /* 可选：文字居中 */
}

:deep(.el-radio__input.is-checked .el-radio__inner) {
  background-color: #ff6600; /* 选中时的背景色 */
  border-color: #ff6600;    /* 选中时的边框色 */
}

:deep(.el-radio__input.is-checked + .el-radio__label) {
  color: #ff6600; /* 选中时的文字颜色 */
}

.import-card{
  background-color: #BCBCBC;
  border:4px solid #A9AFB1;
  border-radius: 10px;
  width:1170px;
  margin-left:10px;
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
