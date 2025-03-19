<template>
  <el-container>
  <el-header>Header</el-header>
  <el-container>
    <el-aside width="200px">
        <router-link to="/userManage">
            用户管理
        </router-link>
        <router-link to="/data/dataUpdate">
            数据上传
        </router-link>
        <router-link to="/data/dataExport">
            数据下载
        </router-link>
    </el-aside>
    <el-main>
        <router-view></router-view>
    </el-main>
  </el-container>
</el-container>
</template>

<script>
export default {

}
</script>

<style>
.el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }
  
  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 200px;
  }
  
  .el-main {
    background-color: #E9EEF3;
    color: #333;
    text-align: center;
    line-height: 160px;
  }
  
  body > .el-container {
    margin-bottom: 40px;
  }
  
  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }
  
  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }
</style>