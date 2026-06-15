import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('@/views/home/index.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index.vue')
  },
  {
    path: '/spots',
    name: 'Spots',
    component: () => import('@/views/spot/list.vue')
  },
  {
    path: '/spot/:id',
    name: 'SpotDetail',
    component: () => import('@/views/spot/detail.vue')
  },
  {
    path: '/ticket/:spotId',
    name: 'TicketBook',
    component: () => import('@/views/ticket/book.vue')
  },
  {
    path: '/orders',
    name: 'Orders',
    component: () => import('@/views/order/list.vue')
  },
  {
    path: '/order/:orderNo',
    name: 'OrderDetail',
    component: () => import('@/views/order/detail.vue')
  },
  {
    path: '/user',
    name: 'User',
    component: () => import('@/views/user/index.vue')
  },
  {
    path: '/favorites',
    name: 'Favorites',
    component: () => import('@/views/favorite/list.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
