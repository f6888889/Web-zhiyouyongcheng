import request from '@/utils/request'

export function getSpotList() {
  return request.get('/spot/list')
}

export function getSpotById(id) {
  return request.get(`/spot/${id}`)
}

export function searchSpots(keyword) {
  return request.get('/spot/search', { params: { keyword } })
}

export function getEnabledSpots() {
  return request.get('/spot/enabled')
}

export function createSpot(data) {
  return request.post('/spot', data)
}

export function updateSpot(data) {
  return request.put('/spot', data)
}

export function deleteSpot(id) {
  return request.delete(`/spot/${id}`)
}
