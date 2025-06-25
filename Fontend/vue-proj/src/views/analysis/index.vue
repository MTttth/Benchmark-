<template>
    <div class="tpc-container">
        <h1 class="header">TPC-H统计分析</h1>
        
        <el-form :model="form" label-width="160px" class="query-form">

            <!-- 国家/地区选择 -->
            <el-form-item label="请选择查询零件类型">
                <div class="date-container">
                    <el-select 
                     class="country-select"
                     v-model="form.parttpye" 
                     placeholder="请选择" 
                     clearable 
                     filterable 
                    >
                    <el-option 
                      v-for="item in partTpye" 
                      :key="item" 
                      :label="item" 
                      :value="item" />
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
            <div class="process-header">
                <h3 class="process-title">业务执行情况</h3>
                <span class="exec-time">执行时长：{{ execTime || '--' }} ms</span>
            </div>
            <div class="result-Order">
              <span class="result-titleOrder">查询结果</span>
              <el-table :data="profitData" class="result-table">
                <el-table-column class="table-item" prop="nation" label="国家" />
                <el-table-column class="table-item" prop="oyear" label="年份" />
                <el-table-column class="table-item" prop="sumProfit" label="总利润" />     
              </el-table>
            </div>
            <div class="result-Order">
                <div class="result-titleOrder">执行计划</div>
                <el-table :data="explain" class="result-table">
                  <el-table-column class="table-item" prop="selectType" label="操作类型" />
                  <el-table-column class="table-item" prop="table" label="表" />
                  <el-table-column class="table-item" prop="type" label="类型" />     
                  <el-table-column class="table-item" prop="key" label="键" />
                  <el-table-column class="table-item" prop="ref" label="引用" />
                  <el-table-column class="table-item" prop="rows" label="扫描行数" />      
                </el-table>
            </div>
        </div>
        <div class="padding">

        </div>
    </div>
</template>

<script>
import axios from 'axios';
export default {
    data() {
        return {
            form: {
                parttpye: ''
            },
            partTpye: [],
            execTime: 0.0,
            explain: [],
            profitData: []
        }
    },
    methods: {
        async handleQuery() {
          await axios.post('/api/data/analysisData',this.form).then(response => {
            if(response.data.code == 1){
              this.profitData = response.data.data.nationProfits
              this.execTime = response.data.data.time
              this.explain = response.data.data.explianPlans
              console.log(this.profitData)
            }
          })
        },
    },
    mounted(){
      axios.get('/api/data/getAllPartType').then(response => {
        if(response.data.code == 1){
          this.partTpye = response.data.data
        }
      })
    }
}
</script>

<style scoped>

.tpc-container {
  padding: 20px;
  margin: 0 auto;
  overflow-y: auto;  
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
  min-height: 150px; /* 允许高度扩展 */
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
  position: relative;
  /* left:700px;  */
  top: 80px;
  width: 1200px;
  margin: 0 auto;
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
  font-size: 30px;
  font-weight: bolder;
  /* margin: -5px 200px 5px -220px;  */
  margin: 0 auto;
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

.process-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 18px;
}

.exec-time {
  font-size: 18px;
  color: #666;
  font-weight: normal;
}

.result-Order{
  width: 1170px;
  height: 300px;
  position:relative;
}

.result-titleOrder {
  font-size: 25px;
  color: #000;
  font-weight: bolder;
  top:15px;
  left:5px;
}

.result-table {
  position: relative;
  top: 20px;
  left: 10px;
  width:1140px;
  height:200px;
  background-color: #FFFFFF; 
  border-radius: 10px 10px 10px 10px!important;
  overflow:auto;
  border-bottom: none!important;
  border-color: transparent!important;
}
/* .table-item{
  font-size: 15px;
} */
.plan-card {
  /* margin-top: 10px; */
  /* background: #f8f8f8; */
  border-radius: 8px;
  padding: 16px 22px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.04);
  width: 100%;
}

.plan-title {
  font-size: 20px;
  font-weight: bold;
  color: #333;
  margin-bottom: 10px;
}

.plan-content {
  font-size: 15px;
  color: #444;
  white-space: pre-wrap;
  word-break: break-all;
  margin: 0;
}
.padding{
  height: 100px;
}
</style>