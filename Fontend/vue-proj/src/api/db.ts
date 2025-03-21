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
