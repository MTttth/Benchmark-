import request from '@/utils/request'
/**
 *
 * 员工管理
 *
 **/
// 登录、
export const login = (data: any) =>
  request({
    'url': '/user/login',
    'method': 'post',
    data
  })
  // 退出
 export const userLogout = (params: any) =>
 request({
   'url': `/employee/logout`, // 授课老师接口
   'method': 'post',
   params
 })

export const getUserList = (params: any) => {
  return request({
    url: '/user/page',
    method: 'get',
    params
  })
}

// 新增---添加员工
export const addUser = (params: any) => {
  return request({
    url: '/user/register',
    method: 'post',
    data: { ...params }
  })
}

export const deleteUserById = (id: number) => {
  return request({
    url: `/user/delete/${id}`,
    method: 'delete',
    // data: id
  })
}
// 修改页面反查详情接口
export const queryEmployeeById = (id: string | (string | null)[]) => {
  return request({
    url: `/employee/${id}`,
    method: 'get'
  })
}
