import request from '@/utils/request'

export function getUserList() {
  return request.get('/user/list')
}

export function getUserById(id) {
  return request.get(`/user/${id}`)
}

export function getUserByOpenId(openId) {
  return request.get(`/user/openid/${openId}`)
}

export function createUser(data) {
  return request.post('/user', data)
}

export function updateUser(data) {
  return request.put('/user', data)
}

export function addPoints(userId, points) {
  return request.post('/user/points', null, { params: { userId, points } })
}

export function login(username, password) {
  return request.post('/user/login', null, { params: { username, password } })
}

export function checkUser(username) {
  return request.get('/user/check', { params: { username } })
}
