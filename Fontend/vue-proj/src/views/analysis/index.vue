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
    height: 90vh;
    background-color: #6CBBB6;
}

.header {
    text-align: left;
    margin-bottom: 10px;
    color: #E6E5D4;
    font-size: 25px;
    font-weight: bolder;
    position:relative;
    left:50px;
}

.query-form {
    width:550px;
    height:230px;
    margin-bottom: 30px;
    background: #BCBCBC;
    padding: 20px;
    border-radius: 10px;
    border: 4px solid #A9AFB1;
    position:relative;
    left:50px;
}

.date-container{
    background-color: #6CBBB6;
    width:350px;
    height:45px;
    display: flex;
    justify-content: center;
    align-items: center;    
}

::v-deep .el-form-item__label {
  font-size: 20px;
  color: #333;
  font-weight: bolder;
  margin-top:5px;
}

::v-deep .el-date-editor {
  /* 输入框样式 */
  width:350px;
  height:45px;
  border-radius: 10px;
  background-color: #BCBCBC;
  border:4px,solid,#A9AFB1;
  display: flex;
  justify-content: center;
  align-items: center;
}

::v-deep.el-date-editor .el-range-input {
  /* 输入框文本样式 */
  font-size: 20px;
  font-weight:bolder;
  border-radius: 10px;
}

::v-deep .el-input__icon {
    line-height: 30px;
    color:#333;
}

.country-select {
    width: 350px; /* 设置宽度 */
    height: 45px; /* 设置高度 */
    border:3px solid #A9AFB1;
    border-radius: 10px;
}

.country-select :deep(.el-input__inner::placeholder) {
  color: #BCBCBC;
  font-size:20px;
  font-weight: bolder;
  position:relative;
  top:3px;
  left:-5px;
}

.business-section {
    margin: 20px 0;
    padding: 20px;
    border: 4px solid #A9AFB1;
    border-radius: 10px;
    background-color: #BCBCBC;
    position:absolute;
    width:600px;
    height:750px;
    top:146px;
    left:1050px;
}

.process-title{
    color:#333;
    font-size:20px;
    font-weight: bolder;
    position: relative;
    left:-215px;
    top:-5px;
}

.placeholder-content {
    border-radius: 10px;
    height:650px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    background: #fff;
    overflow: auto;
}

.action-wrapper {
    position:relative;
    top:-10px;
    left:225px;
}

.btn{
    border:0;
    border-radius: 10px;
    background-color: #A9AFB1;
    width:80px;
    height:40px;
    display: flex;
    justify-content: center;
    align-items: center;
    position: relative;
    left:205px;
}

.text{
    font-size: 20px;
    font-weight: bolder;
    color:#FFFFFF;
}
</style>