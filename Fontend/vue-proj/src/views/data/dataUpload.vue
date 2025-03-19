<template>
    <div>
      <input type="file" @change="handleFileUpload" />
      <button @click="uploadFile">上传并导入数据库</button>
      <div v-if="progress > 0">上传进度: {{ progress }}%</div>
      <div v-if="resultMessage">{{ resultMessage }}</div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        file: null,
        tableName: 'region', // 根据选择的表动态调整
        progress: 0,
        resultMessage: ''
      };
    },
    methods: {
      handleFileUpload(event) {
        this.file = event.target.files[0];
      },
      async uploadFile() {
        if (!this.file || !this.tableName) {
          alert('请选择文件和表名');
          return;
        }
  
        const formData = new FormData();
        formData.append('file', this.file);
        formData.append('tableName', this.tableName);
  
        try {
          const response = await axios.post('/api/data/upload', formData, {
            headers: { 'Content-Type': 'multipart/form-data' },
            onUploadProgress: (progressEvent) => {
              this.progress = Math.round(
                (progressEvent.loaded / progressEvent.total) * 100
              );
            }
          });
  
          this.resultMessage = `导入成功，插入 ${response.data.data.rowsInserted} 行`;
        } catch (error) {
          this.resultMessage = `导入失败: ${error.response.data.message}`;
        }
      }
    }
  };
  </script>