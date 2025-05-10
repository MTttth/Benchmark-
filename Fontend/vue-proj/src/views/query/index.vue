<template>
  <el-container>
    <el-aside width="500px">
      <el-card>
        <h3>客户信息查询</h3>
        <el-input placeholder="请输入客户名或国家名" v-model="searchInput" @keyup.enter="searchCustomer" />
        <el-button type="primary" @click="searchCustomer" style="margin-top:10px">查询</el-button>

        <el-divider>或</el-divider>
        <el-select v-model="selectedCustomer" placeholder="选择客户" filterable @change="selectCustomer">
          <el-option v-for="item in customerList" :key="item.c_custkey" :label="item.c_name" :value="item.c_custkey" />
        </el-select>

        <el-table :data="customerResults" style="margin-top: 20px" v-if="customerResults.length">
          <el-table-column prop="c_name" label="客户名" />
          <el-table-column prop="nation_name" label="国家" />
          <el-table-column prop="c_address" label="地址" />
        </el-table>
      </el-card>
    </el-aside>

    <el-main>
      <el-card>
        <h3>国家订单总额统计</h3>
        <el-button type="primary" @click="fetchChartData">刷新图表</el-button>
        <div id="chart" style="height: 400px; margin-top: 20px"></div>
      </el-card>
    </el-main>
  </el-container>
</template>

<script>
import * as echarts from 'echarts';
import { getCustomerList, queryCustomer, fetchOrderChart } from '@/api/db';

export default {
  data() {
    return {
      searchInput: '', // 用户输入的客户名或国家名
      selectedCustomer: null, // 下拉列表选中的客户ID
      customerList: [], // 客户下拉选项数据
      customerResults: [], // 客户查询结果列表
      chart: null, // ECharts 图表实例
    };
  },

  mounted() {
    this.loadCustomerList();
    this.initChart();
  },
  methods: {
    async loadCustomerList() {
      const res = await getCustomerList(); // 调用后端API获取客户列表
      this.customerList = res.data;
    },
    async searchCustomer() {
      const res = await queryCustomer({ keyword: this.searchInput });
      this.customerResults = res.data;
    },
    async selectCustomer() {
      const res = await queryCustomer({ custkey: this.selectedCustomer });
      this.customerResults = res.data;
    },
    async fetchChartData() {
      const res = await fetchOrderChart();
      const chartData = res.data;
      this.chart.setOption({
        title: { text: '国家订单总额' },
        tooltip: {},
        xAxis: {
          type: 'category',
          data: chartData.map(item => item.country)
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          name: '订单总额',
          type: 'bar',
          data: chartData.map(item => item.total_order_value)
        }]
      });
    },
    initChart() {
      this.chart = echarts.init(document.getElementById('chart'));
    }
  }
};
</script>

<style scoped>
.el-aside {
  padding: 20px;
  background: #f0f2f5;
}

.el-main {
  padding: 20px;
}
</style>
