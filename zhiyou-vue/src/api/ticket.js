import request from '@/utils/request'

export function getTicketList() {
  return request.get('/ticket/list')
}

export function getTicketById(id) {
  return request.get(`/ticket/${id}`)
}

export function getTicketsBySpotId(spotId) {
  return request.get(`/ticket/spot/${spotId}`)
}

export function createTicket(data) {
  return request.post('/ticket', data)
}

export function updateTicket(data) {
  return request.put('/ticket', data)
}

export function reduceStock(ticketId, quantity) {
  return request.post('/ticket/stock/reduce', null, { params: { ticketId, quantity } })
}

export function increaseStock(ticketId, quantity) {
  return request.post('/ticket/stock/increase', null, { params: { ticketId, quantity } })
}
