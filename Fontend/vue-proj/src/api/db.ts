import request from '@/utils/request'

// 获取数据库连接信息
export const fetchDbInfo = (data: any) =>
  request({
    'url': '/db/info',
    'method': 'get',
    data
  })
// 更新数据库参数
export const saveDbConfig = (data: any) =>
  request({
    'url': '/db/update-config',
    'method': 'post',
    data
  })

// 获取客户列表（下拉选择）
export const getCustomerList = () =>
  request({
    'url': '/api/customers/list',
    'method': 'get'
  });

// 根据客户名或国家名查询客户信息
export const queryCustomer = (data: any) =>
  request({
    'url': '/api/customers/query',
    'method': 'post',
    data
  });

// 获取国家订单汇总数据（用于图表展示）
export const fetchOrderChart = () =>
  request({
    'url': '/api/orders/summary',
    'method': 'get'
  });
