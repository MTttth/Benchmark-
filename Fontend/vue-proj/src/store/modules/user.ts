import { VuexModule, Module, Action, Mutation, getModule } from 'vuex-module-decorators'
import { login, userLogout } from '@/api/user'
import { getToken, setToken, removeToken, getStoreId, setStoreId, removeStoreId, setUserInfo, getUserInfo, removeUserInfo } from '@/utils/cookies'
import store from '@/store'
import Cookies from 'js-cookie'
import { Message } from 'element-ui'
export interface IUserState {
  token: string
  name: string
  avatar: string
  storeId: string
  introduction: string
  userInfo: any
  roles: string[]
  username: string
}

@Module({ 'dynamic': true, store, 'name': 'user' })
class User extends VuexModule implements IUserState {
  public token = getToken() || ''
  public name = ''
  public avatar = ''
  // @ts-ignore
  public storeId: string = getStoreId() || ''
  public introduction = ''
  public userInfo = {}
  public roles: string[] = []
  public username = Cookies.get('username') || ''
  public type: number = -1 // 默认值，-1 代表未选择

  @Mutation
  private SET_TOKEN(token: string) {
    this.token = token
  }

  @Mutation
  private SET_NAME(name: string) {
    this.name = name
  }

  @Mutation
  private SET_TYPE(type: number) {
    this.type = type
  }
  
  @Mutation
  private SET_USERINFO(userInfo: any) {
    this.userInfo = { ...userInfo }
  }

  @Mutation
  private SET_AVATAR(avatar: string) {
    this.avatar = avatar
  }

  @Mutation
  private SET_INTRODUCTION(introduction: string) {
    this.introduction = introduction
  }

  @Mutation
  private SET_ROLES(roles: string[]) {
    this.roles = roles
  }

  @Mutation
  private SET_STOREID(storeId: string) {
    this.storeId = storeId
  }
  @Mutation
  private SET_USERNAME(name: string) {
    this.username = name
  }

  @Action
  public async Login(userInfo: { username: string, password: string, type: number }) {
    let { username, password, type } = userInfo
    username = username.trim() // 去除用户名前后空格
    this.SET_USERNAME(username) // 存储用户名到 Vuex
    this.SET_TYPE(type) // 存储用户类型到 Vuex
    Cookies.set('username', username) // 存储用户名到 Cookie
    Cookies.set('type', type.toString()) // 存储到 Cookie
    
    const { data } = await login({ username, password, type }) // 调用后端 API 进行登录

    if (String(data.code) === '1') { // 判断登录是否成功
      this.SET_TOKEN(data.data.token) // 存储 token 到 Vuex
      setToken(data.data.token) // 存储 token 到 Cookie 或 LocalStorage
      this.SET_USERINFO(data.data) // 存储用户信息到 Vuex
      Cookies.set('user_info', data.data) // 存储用户信息到 Cookie
      return data // 返回后端返回的数据
    } else {
      return Message.error(data.msg) // 登录失败，弹出错误信息
    }
  }

  @Action
  public ResetToken() {
    removeToken()
    this.SET_TOKEN('')
    this.SET_ROLES([])
  }

  @Action
  public async changeStore(data: any) {
    this.SET_STOREID = data.data
    this.SET_TOKEN(data.authorization)
    setStoreId(data.data)
    setToken(data.authorization)
  }

  @Action
  public async GetUserInfo() {
    if (this.token === '') {
      throw Error('GetUserInfo: token is undefined!')
    }

    const data = JSON.parse(<string>getUserInfo()) //  { roles: ['admin'], name: 'zhangsan', avatar: '/login', introduction: '' }
    if (!data) {
      throw Error('Verification failed, please Login again.')
    }

    const { roles, name, avatar, introduction, applicant, storeManagerName, storeId = '' } = data // data.user
    // roles must be a non-empty array
    if (!roles || roles.length <= 0) {
      throw Error('GetUserInfo: roles must be a non-null array!')
    }

    this.SET_ROLES(roles)
    this.SET_USERINFO(data)
    this.SET_NAME(name || applicant || storeManagerName)
    this.SET_AVATAR(avatar)
    this.SET_INTRODUCTION(introduction)
  }

  @Action
  public async LogOut() {
    const { data } = await userLogout({})
    removeToken()
    this.SET_TOKEN('')
    this.SET_ROLES([])
    Cookies.remove('username')
    Cookies.remove('user_info')
    removeUserInfo()
  }
}

export const UserModule = getModule(User)
