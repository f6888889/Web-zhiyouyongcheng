import request from '@/utils/request'

export function getFlowList() {
  return request.get('/flow/list')
}

export function getFlowById(id) {
  return request.get(`/flow/${id}`)
}

export function getFlowBySpotId(spotId) {
  return request.get(`/flow/spot/${spotId}`)
}

export function createFlow(data) {
  return request.post('/flow', data)
}

export function updateFlow(data) {
  return request.put('/flow', data)
}
