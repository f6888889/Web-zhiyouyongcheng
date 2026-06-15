import { defineStore } from 'pinia'
import { getUserByOpenId, createUser, login as loginApi } from '@/api/user'

export const useUserStore = defineStore('user', {
  state: () => ({
    userInfo: null,
    isLoggedIn: false
  }),

  actions: {
    async loginByUsername(username, password) {
      try {
        const res = await loginApi(username, password)
        if (res.code === 200) {
          this.userInfo = res.data
          this.isLoggedIn = true
          localStorage.setItem('userInfo', JSON.stringify(this.userInfo))
          localStorage.setItem('token', 'logged_in')
          return this.userInfo
        } else {
          throw new Error(res.message || '登录失败')
        }
      } catch (error) {
        console.error('用户名密码登录失败:', error)
        throw error
      }
    },

    async login(userData) {
      try {
        let user = await getUserByOpenId(userData.openId)
        if (!user.data) {
          await createUser(userData)
          user = await getUserByOpenId(userData.openId)
        }
        this.userInfo = user.data
        this.isLoggedIn = true
        localStorage.setItem('userInfo', JSON.stringify(this.userInfo))
        return this.userInfo
      } catch (error) {
        console.error('登录失败:', error)
        throw error
      }
    },

    logout() {
      this.userInfo = null
      this.isLoggedIn = false
      localStorage.removeItem('userInfo')
      localStorage.removeItem('token')
    },

    loadUser() {
      const stored = localStorage.getItem('userInfo')
      const token = localStorage.getItem('token')
      if (stored && token) {
        this.userInfo = JSON.parse(stored)
        this.isLoggedIn = true
      }
    },

    getUserId() {
      return this.userInfo?.id || null
    }
  }
})
