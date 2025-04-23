<template>
  <div class="container">
    <!-- 客户信息查询 -->
    <el-card class="box-card">
      <div class="query-section">
        <!-- 标题行 -->
        <div class="query-title">
          <span class="title">客户信息查询</span>
        </div>

        <!-- 查询操作行 -->
        <div class="query-row">
          <div class="query-group">
            <!-- <el-input v-model="searchText" placeholder="请输入要查询的对象" clearable />
            <el-button type="primary" @click="handleTextQuery">查询</el-button> -->
            <el-input
             v-model="searchText"
             placeholder="请输入要查询的对象"
             clearable
             >
             <template #append>
              <el-button 
              @click="handleTextQuery"
              class="inline-btn"
              ><span class="inline-text">查询</span></el-button>
             </template>
            </el-input>
          </div>

          <div class="query-group">
            <el-select 
             v-model="selectedOption" 
             placeholder="请选择要查询的对象" 
             clearable
             class="custom-select"
             >
              <el-option 
               v-for="item in options" 
               :key="item.value" 
               :label="item.label" 
               :value="item.value" />
            </el-select>
            <div class="inline-select">
              <el-button 
               type="primary" 
               @click="handleSelectQuery"
               class="select-inline-btn"
              ><span class="inline-text">查询</span>
              </el-button>
            </div>
          </div>
        </div>
      </div>
    </el-card>
    <!-- <el-card class="box-card">
        <div class="query-section">
          <div class="query-title">
            <span class="title">客户信息查询</span>
          </div>
          
          <div class="query-group">
            <el-input v-model="searchText" placeholder="请输入要查询的对象" clearable />
            <el-button type="primary" @click="handleTextQuery">查询</el-button>
          </div>
          
          <div class="query-group">
            <el-select v-model="selectedOption" placeholder="请选择要查询的对象" clearable>
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
            <el-button type="primary" @click="handleSelectQuery">查询</el-button>
          </div>
        </div>
      </el-card> -->

    <!-- 查询结果 -->
    <div class="result-header">
      <span class="title">客户查询结果</span>
    </div>
    <div class="query-result">
      <span class="result-title">查询结果</span>
      <el-table :data="queryResults" class="result-table">
        <el-table-column prop="key" label="客户key" />
        <el-table-column prop="name" label="客户姓名" />
        <el-table-column prop="address" label="客户地址" />     
        <el-table-column prop="nationkey" lable="客户国家key" />   
        <el-table-column prop="phone" label="客户电话" />
        <el-table-column prop="acctbal" label="客户余额" />
        <el-table-column prop="mktsegment" label="客户市场领域" />
        <el-table-column prop="comment" label="备注" />
      </el-table>
      <div class="path-save">
        <span class="result-title path-title">选择保存路径</span>
        <el-button class="path-btn" @click="handleSelectPath">
          <span class="btn-path-title">路径选择</span>
        </el-button>
        <!-- 根据路径选择按钮的实现决定该按钮是否保留 -->
        <!-- 若保留，则考虑在“其他信息查询”处复制一个相同的按钮 -->
        <el-button class="inline-btn save-btn" @click="handleSave">
          <span class="inline-text">保存</span>
        </el-button>
      </div>
    </div>
    <!-- <el-card class="box-card result-card">
      <div class="result-header">
        <span class="title">客户查询结果</span>
        <div class="save-path">
          <el-input v-model="savePath" placeholder="选择保存路径" readonly>
            <template #append>
              <el-button @click="handleSelectPath">路径选择</el-button>
            </template>
          </el-input>
          <el-button type="success" @click="handleSave">保存</el-button>
        </div>
      </div>

      <el-table :data="queryResults" border style="width: 100%">
        <el-table-column prop="name" label="客户姓名" />
        <el-table-column prop="phone" label="联系电话" />
        <el-table-column prop="address" label="联系地址" />
      </el-table>
    </el-card> -->

    <!-- 其他信息查询 -->
    <el-card class="box-card">
      <div class="query-section">
        <div class="query-title">
          <span class="title">其他信息查询</span>
        </div>
        <div class="query-group">
            <el-select 
             v-model="selectedOption" 
             placeholder="请选择要查询的对象" 
             clearable
             class="custom-select"
             >
              <el-option 
               v-for="item in options" 
               :key="item.value" 
               :label="item.label" 
               :value="item.value" />
            </el-select>
            <div class="inline-select">
              <el-button 
               type="primary" 
               @click="handleSelectQuery"
               class="select-inline-btn"
              ><span class="inline-text">查询</span>
              </el-button>
            </div>
          </div>
        <!-- <div class="query-group">
          <el-select v-model="otherSelected" placeholder="请选择要查询的信息" clearable>
            <el-option v-for="item in otherOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
          <el-button type="primary" @click="handleOtherQuery">查询</el-button>
        </div> -->
        <div class="path-save other-save">
          <span class="title">选择保存路径</span>
        <el-button class="path-btn other-btn" @click="handleSelectPath">
          <span class="btn-path-title">路径选择</span>
        </el-button>
        </div>
        <!-- <div class="save-path">
          <el-input v-model="otherSavePath" placeholder="选择保存路径" readonly>
            <template #append>
              <el-button @click="handleOtherPath">路径选择</el-button>
            </template>
          </el-input>
        </div> -->
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'

// 客户查询相关数据
const searchText = ref('')
const selectedOption = ref('')
const options = ref([
  { value: '1', label: '按客户编号查询' },
  { value: '2', label: '按客户级别查询' }
])

// 查询结果数据
const queryResults = ref([
  { name: '张三', phone: '13800138000', address: '北京市朝阳区' },
  { name: '李四', phone: '13900139000', address: '上海市浦东新区' },
  { name: '王五', phone: '13700137000', address: '广州市天河区'},
  { name: '赵六', phone: '13600136000', address: '深圳市南山区'}
])

// 保存路径相关
const savePath = ref('')
const otherSavePath = ref('')

// 其他信息查询
const otherSelected = ref('')
const otherOptions = ref([
  { value: '1', label: '交易记录' },
  { value: '2', label: '服务记录' }
])

// 事件处理
const handleTextQuery = () => {
  console.log('文本查询:', searchText.value)
}

const handleSelectQuery = () => {
  console.log('选项查询:', selectedOption.value)
}

const handleSelectPath = () => {
  // 这里需要实现路径选择逻辑，可能需要调用Electron对话框或使用第三方库
  savePath.value = '/path/to/save'
}

const handleSave = () => {
  console.log('保存到:111', savePath.value)
}

const handleOtherQuery = () => {
  console.log('其他信息查询:', otherSelected.value)
}

const handleOtherPath = () => {
  otherSavePath.value = '/other/path'
}
</script>

<style scoped>

.container {
  background-color: #6CBBB6;
  height: 90vh;
  overflow: hidden !important;
}

/* 卡片内边距调整 */
.box-card {
  padding: 20px;
  background-color: #6CBBB6;
}

.el-card{
  border:none !important;
  box-shadow: none !important;
}

.query-section {
  display: flex;
  flex-direction: column;
  gap: 10px;
  /* 标题与操作行间距 */
  padding-left: 10px;
  /* 与左边间距：20+20+10=50 */
}

.query-title {
 text-align: left; 
}

.title {
  font-size: 25px;
  color: #E6E5D4;
  font-weight: bolder;
  text-align: left;
}

.query-row {
  display: flex;
  gap: 200px;
  /* 两组查询之间的间距 */
  align-items: flex-end;
  /* 底部对齐 */
}

.query-group {
  display: flex;
  gap: 0px;
  /* 输入框与按钮间距 */
  align-items: center;
}

.el-input,
.el-select {
  width: 500px;
  /* 统一输入控件宽度 */
}

:deep(.el-input__inner) {
  height: 55px;
  border-radius: 10px 0px 0px 10px!important;
  border: 4px solid #BCBCBC !important; 
  border-right: 0 !important;
  box-sizing: border-box !important;
  padding: 0 15px!important;
  font-size: 25px!important;
  font-weight: bolder!important;
  color: #BCBCBC!important;
  /* &::placeholder{
    color:#BCBCBC;
    font-weight: bolder !important;
  } */
}

:deep(.el-input__inner::placeholder) {
  color: #BCBCBC; /* 文字颜色 */
  font-size: 25px;
  font-weight: bolder;
  /* font-style: italic; 斜体 */
  opacity: 0.8; /* 透明度 */
}

/* .el-input-group__append {
  padding: 0 !important;
  border-left: none !important;
} */

:deep(.el-input-group__append) {
  background: #FFFFFF;
  border-color: #BCBCBC;
  border: 4px solid #BCBCBC !important;
  border-left: none !important; 
  border-radius: 0px 10px 10px 0px!important;
}

:deep(.inline-btn) {
  display: inline-flex!important;
  justify-content: center!important;
  align-items: center!important;
  background-color: #A9AFB1 !important;
  border-radius: 10px 10px 10px 10px !important;
  box-shadow: none!important;
  width: 65px;
  height: 40px !important;
  position: relative;
  left: -5px !important;
}

.inline-text {
  color: #FFFFFF !important;
  font-weight: bolder; 
  font-size: 20px;
}

.custom-select {
  width: 430px !important;  /* select下拉框无法内嵌按钮，只能将按钮嵌入div容器，
                               将div容器样式设置为select的样式拼接在一起，宽度即为
                               340+60（div宽度） */
  border-right: none!important;
}

.inline-select {
  box-sizing: border-box;;
  position:relative;
  height:55px;
  width:70px;
  background-color: #FFFFFF;
  border: 4px solid #BCBCBC;
  border-radius: 0px 10px 10px 0px;
  border-left: none;  
  padding: 0; 
  justify-content:left;
  align-items: center;
  display: flex;
}

.inline-select .select-inline-btn {
  display: inline-flex;
  justify-content: center!important;
  align-items: center!important;
  background-color: #A9AFB1 !important;
  border-radius: 10px 10px 10px 10px !important;
  border:1px;
  box-shadow: none!important;
  width: 65px;
  height: 40px;
}

:deep(.custom-select .el-select__caret) {
  /* 修改颜色 */
  color: #BCBCBC !important;

  /* 修改大小 */
  font-size: 16px !important;
  font-weight: bolder!important;
  /* 修改旋转动画（展开时） */
  transition: transform 0.3s !important;
}
/* 调整 Element下拉组件样式 */

/* .box-card {
    margin-bottom: 20px;
  }
  
  .query-section {
    display: flex;
    }
  .title {
   color: #E6E5D4; 
  }
  .query-group {
    display: flex;
    gap: 10px;
    align-items: center;
  }
   */

/* 查询结果样式 */
.result-header {
  position:absolute;
  left:400px;
  bottom:645px; 
}

.query-result {
  box-sizing: border-box;;
  margin-left:50px;
  margin-top: 20px;
  height:285px;
  width:1200px;
  background-color: #BCBCBC;
  padding-top:50px;
  display: flex;
  flex-direction: column;
  position:relative;
  padding:10px;
  gap:10px;
  border: 4px solid #A9AFB1!important;
  border-radius: 10px 10px 10px 10px!important;
}

.result-title {
  font-size: 25px;
  color: #000;
  font-weight: bolder;
  position:absolute;
  top:5px;
  left:5px;
}

.result-table {
  position: absolute;
  top: 50px;
  left: 10px;
  width:1170px;
  height:165px;
  background-color: #FFFFFF; 
  border-radius: 10px 10px 10px 10px!important;
  overflow:auto;
  border-bottom: none!important;
  border-color: transparent!important;
}

/* .result-card {
  background-color: #FFFFFF;
  .result-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 15px;
  }
} */

.path-save {
  background-color: transparent;
  display:flex;
  flex-direction: row;
  width: 1000px;
  position:absolute;
  bottom: 15px;
  left:10px;
}

.path-title {
  width:150px;
  position:relative;
  left:0px;
  bottom:0px;
  font-size: 25px; 
}

.path-btn {
  width:100px;
  height:40px;
  background-color: #A9AFB1 !important;
  border-radius: 10px 10px 10px 10px!important;
  border:none!important;
  position:relative;
  left:10px;
  top:5px;
  display: flex!important;
  align-items: center!important;
  justify-content: center!important;
}

.btn-path-title {
  font-weight:boler;
  font-size:16px;
  color:#000; 
}

.save-btn{
  position:relative;
  left:845px !important;
  top:5px;
  border:0px;
}

.ohter-title {
  font-size: 25px;
  color: #E6E5D4;
  font-weight: bolder; 
}

.other-save {
  position:relative;
  left:0px;
  top:5px; 
}

.other-btn {
  position:relative;
  left:10px;
  top:0px; 
}
/* .save-path {
  display: flex;
  gap: 10px;
  align-items: center;
  width: 400px;
} */
</style>