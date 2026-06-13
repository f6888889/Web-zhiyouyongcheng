import request from '@/utils/request'

export function getFavoritesByUserId(userId) {
  return request.get(`/favorite/user/${userId}`)
}

export function checkFavorite(userId, spotId) {
  return request.get('/favorite/check', { params: { userId, spotId } })
}

export function addFavorite(userId, spotId) {
  return request.post('/favorite/add', null, { params: { userId, spotId } })
}

export function removeFavorite(userId, spotId) {
  return request.delete('/favorite/remove', { params: { userId, spotId } })
}

export function removeAllFavorites(userId) {
  return request.delete(`/favorite/user/${userId}`)
}
