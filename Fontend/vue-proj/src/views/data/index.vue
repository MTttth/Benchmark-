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
      const chunkSize = 1024 * 1024; // 1MB
      const file = this.file;
      let start = 0;

      while (start < file.size) {
        // 步骤1：计算初始切割点
        let end = Math.min(start + chunkSize, file.size);
        
        // 步骤2：读取切割点附近的2KB内容（覆盖可能的换行符）
        const scanStart = Math.max(start, end - 2048); // 向后扫描2KB
        const scanChunk = file.slice(scanStart, end);
        const scanText = await new Response(scanChunk).text();
        
        // 步骤3：逆向查找最后一个换行符
        let lastNewLinePos = -1;
        // 遍历所有可能的换行符位置（支持 \n 和 \r\n）
        for (let i = scanText.length - 1; i >= 0; i--) {
          if (scanText[i] === '\n') {
            lastNewLinePos = i;
            // 检查是否是 \r\n 的情况
            if (i > 0 && scanText[i - 1] === '\r') {
              lastNewLinePos--; // 包含 \r
            }
            break;
          }
        }

        // 步骤4：调整切割点至行尾
        if (lastNewLinePos !== -1) {
          end = scanStart + lastNewLinePos + (scanText[lastNewLinePos] === '\r' ? 2 : 1);
        }

        // 步骤5：生成最终文件块
        const chunk = file.slice(start, end);
        
        // 上传逻辑（保持原有代码）
        const formData = new FormData();
        formData.append('file', chunk);
        formData.append('tableName', this.importTable);
        
        const response = await axios.post('/api/data/upload', formData, {
          headers: { 'Content-Type': 'multipart/form-data' },
        });

        // 更新进度（精确到实际切割位置）
        start = end;
        this.progress = Math.round((start / file.size) * 100);

        // 响应处理（保持不变）
        if (response.data.code === 1) {
          this.resultMessage = `导入成功，插入 ${response.data.data.rowsInserted} 行`;
          this.importLogs = response.data.data.importLog || [];
        } else {
          this.errorLogs.push(...response.data.data.errorLogs)
          this.$message.warning('部分数据未导入，请查看错误日志');
        }
      }
      // const chunkSize = 1024 * 1024; // 1MB
      // const file = this.file // 获取文件对象
      // for (let start = 0; start < file.size; start += chunkSize) {
      //   const chunk = file.slice(start, start + chunkSize);
      //   const formData = new FormData();
      //   formData.append('file', chunk);
      //   formData.append('tableName', this.importTable);
      //   console.log(start)
      //   const response = await axios.post('/api/data/upload', formData, {
      //     headers: { 'Content-Type': 'multipart/form-data' },
      //   });
      //   this.progress = Math.round((start / file.size) * 100);
      //   if (response.data.code === 1){
      //     this.resultMessage = `导入成功，插入 ${response.data.data.rowsInserted} 行`;
      //     // 处理导入日志
      //     this.importLogs = response.data.data.importLog || [];
      //   }else{
      //     this.errorLogs.push(...response.data.data.errorLogs)
      //     this.$message.warning('部分数据未导入，请查看错误日志');
      //   }
      // }
      // *******************************************************************************************************
      // const formData = new FormData();
      // formData.append('file', this.file);
      // formData.append('tableName', this.importTable);
      // try {
      //   const response = await axios.post('/api/data/upload', formData, {
      //     headers: { 'Content-Type': 'multipart/form-data' },
      //     onUploadProgress: (progressEvent) => {
      //       this.progress = Math.round((progressEvent.loaded / progressEvent.total) * 100);
      //     }
      //   });
      //   if (response.data.code === 1){
      //     this.resultMessage = `导入成功，插入 ${response.data.data.rowsInserted} 行`;
      //     // 处理导入日志
      //     this.importLogs = response.data.data.importLog || [];
      //   }else{
      //     this.errorLogs = this.errorLogs + response.data.data.errorLogs;
      //     this.$message.warning('部分数据未导入，请查看错误日志');
      //   }
      // } catch (error) { //error.response?.data?.msg
      //   this.resultMessage = `导入失败: ${error.data.msg || '未知错误'}`;
      // }
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
