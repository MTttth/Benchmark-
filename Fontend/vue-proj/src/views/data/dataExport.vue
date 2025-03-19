<template>
    <div class="export-container">
      <el-select v-model="selectedTable" placeholder="选择数据表">
        <el-option
          v-for="table in tables"
          :key="table"
          :label="table"
          :value="table"
        />
      </el-select>
  
      <el-radio-group v-model="fileType">
        <el-radio label="xlsx">xlsx</el-radio>
        <el-radio label="txt">TXT</el-radio>
      </el-radio-group>
  
      <el-button 
        type="primary"
        @click="handleExport"
        :disabled="!selectedTable"
      >
        导出数据
      </el-button>
    </div>
  </template>
  
  <script>
import axios from 'axios';
  export default {
    data() {
      return {
        tables: ['orders', 'customers', 'products'], // 静态数据
        selectedTable: 'region',
        fileType: 'xlsx'
      }
    },
    methods: {
      async handleExport() {
        try {
          const response = await axios.post(
            '/api/data/export',
            {
              tableName: this.selectedTable,
              fileType: this.fileType
            },
            { responseType: 'blob' }
          )
  
          const url = window.URL.createObjectURL(new Blob([response.data]))
          const link = document.createElement('a')
          link.href = url
          link.setAttribute(
            'download',
            `${this.selectedTable}.${this.fileType}`
          )
          document.body.appendChild(link)
          link.click()
          link.remove()
        } catch (error) {
          console.error('导出失败:', error)
          this.$message.error('导出失败，请重试')
        }
      }
    }
  }
  </script>
  
  <style scoped>
  .export-container {
    max-width: 600px;
    margin: 2rem auto;
    display: flex;
    gap: 1rem;
    align-items: center;
  }
  </style>