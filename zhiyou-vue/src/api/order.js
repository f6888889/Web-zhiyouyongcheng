import request from '@/utils/request'

export function getOrderList() {
  return request.get('/order/list')
}

export function getOrderById(id) {
  return request.get(`/order/${id}`)
}

export function getOrderByNo(orderNo) {
  return request.get(`/order/no/${orderNo}`)
}

export function getOrdersByUserId(userId) {
  return request.get(`/order/user/${userId}`)
}

export function createOrder(data) {
  return request.post('/order', data)
}

export function updateOrder(data) {
  return request.put('/order', data)
}

export function payOrder(orderNo) {
  return request.post(`/order/pay/${orderNo}`)
}

export function cancelOrder(orderNo) {
  return request.post(`/order/cancel/${orderNo}`)
}

export function useOrder(orderNo) {
  return request.post(`/order/use/${orderNo}`)
}
