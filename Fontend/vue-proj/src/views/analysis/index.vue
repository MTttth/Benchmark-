<template>
    <div class="tpc-container">
        <h1 class="header">TPC-H统计分析</h1>
        
        <el-form :model="form" label-width="160px" class="query-form">
            <!-- 日期范围选择 -->
            <el-form-item label="请选择日期" class="date-label">
                <div class="date-container">
                    <el-date-picker 
                     popper-class="elDatePicker"
                     v-model="form.dateRange" 
                     type="daterange" 
                     range-separator="~" 
                     start-placeholder="开始日期"
                     end-placeholder="结束日期" 
                     format="yyyy-MM-dd" 
                     value-format="yyyy-MM-dd" />
                </div>
            </el-form-item>

            <!-- 国家/地区选择 -->
            <el-form-item label="请选择国家/地区">
                <div class="date-container">
                    <el-select 
                     class="country-select"
                     v-model="form.country" 
                     placeholder="请选择" 
                     clearable 
                     filterable 
                    >
                        <el-option 
                         v-for="item in countries" 
                         :key="item.value" 
                         :label="item.label" 
                         :value="item.value" />
                </el-select>
                </div>
            </el-form-item>
            <!-- 查询按钮 -->
            <div class="action-wrapper">
                <el-button 
                 @click="handleQuery"
                 class="btn"
                >
                    <span class="text">查询</span>
                </el-button>
            </div>
        </el-form>

        <!-- 业务执行情况 -->
        <div class="business-section">
            <h3 class="process-title">业务执行情况</h3>
            <div class="placeholder-content">
                <!-- 这里可以放置表格/图表等具体内容 -->
                <el-empty description="暂无数据" />
                <el-empty description="暂无数据" />
                <el-empty description="暂无数据" />
                <el-empty description="暂无数据" />
            </div>
        </div>

    </div>
</template>

<script>
export default {
    data() {
        return {
            form: {
                dateRange: [],
                country: ''
            },
            countries: [
                { value: 'cn', label: '中国' },
                { value: 'us', label: '美国' },
                { value: 'jp', label: '日本' }
            ]
        }
    },
    methods: {
        handleQuery() {
            console.log('查询参数：', this.form)
            // 这里添加实际查询逻辑
        },
    }
}
</script>

<style scoped>

.tpc-container {
  padding: 20px;
  margin: 0 auto;
  min-height: 90vh; /* 改为最小高度 */
  background-color: #6CBBB6;
  position: relative; /* 创建定位上下文 */
  display: flex; /* 启用 flex 布局 */
  flex-direction: column;
  overflow: hidden;
}

.header {
  text-align: left;
  margin: 10px 0 20px 50px;
  color: #E6E5D4;
  font-size: 25px;
  font-weight: bolder;
}

.query-form {
  width: 550px;
  min-height: 230px; /* 允许高度扩展 */
  margin: -7px 0 30px 50px; 
  background: #BCBCBC;
  /* padding: 20px; */
  padding-top:30px;
  padding-right:20px;
  border-radius: 10px;
  border: 4px solid #A9AFB1;
  z-index: 1; /* 保持层级 */
}

.business-section {
  position: absolute;
  left:700px; 
  top: 80px;
  width: 600px;
  height: 750px; 
  padding: 20px;
  border: 4px solid #A9AFB1;
  border-radius: 10px;
  background-color: #BCBCBC;
}

.date-container {
  background-color: #6CBBB6;
  width: 350px;
  height: 45px;
  display: flex;
  justify-content: center;
  align-items: center;    
}

.country-select {
  width: 350px;
  height: 45px;
  border-radius: 10px; 
  border:4px solid #A9AFB1;
}

::v-deep .el-date-editor {
  width: 350px;
  height: 45px;
  border-radius: 10px;
  background-color: #BCBCBC;
  border: 4px solid #A9AFB1; 
}

.process-title {
  color: #333;
  font-size: 20px;
  font-weight: bolder;
  margin: -5px 200px 5px -220px; 
}

.placeholder-content {
  border-radius: 10px;
  height: calc(100% - 40px); /* 动态高度 */
  height: 650px; 
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background: #fff;
  overflow: auto;
}

.action-wrapper {
  text-align: right;
  margin-top: -10px;
}

.btn {
  border: 0;
  border-radius: 10px;
  background-color: #A9AFB1;
  width: 80px;
  height: 40px;
  display: inline-flex; /* 改用 inline-flex */
  justify-content: center;
  align-items: center;
}

/* 新增全局样式 */
/* :global(html) {
  overflow: hidden;
  box-sizing: border-box;
} */

:global(*),
:global(*::before),
:global(*::after) {
  box-sizing: inherit;
}
</style>