<template>
  <div class="favorites-page">
    <header class="header">
      <div class="header-content container">
        <router-link to="/user" class="back-link">
          <Icon icon="mdi:arrow-left" class="back-icon" />
        </router-link>
        <h1 class="page-title">我的收藏</h1>
        <div class="header-placeholder"></div>
      </div>
    </header>

    <section class="favorites-section container">
      <div v-if="loading" class="loading-state">
        <div class="loading-spinner"></div>
      </div>

      <div v-else-if="favorites.length === 0" class="empty-state">
        <Icon icon="mdi:heart-outline" class="empty-icon" />
        <p>暂无收藏</p>
        <router-link to="/spots" class="browse-btn">去收藏景区</router-link>
      </div>

      <div v-else class="favorites-list">
        <div
          v-for="item in favorites"
          :key="item.id"
          class="favorite-card"
          @click="goToSpot(item.spotId)"
        >
          <div class="card-content">
            <img
              :src="item.spotCover || 'https://picsum.photos/200/150?random=' + item.spotId"
              :alt="item.spotName"
              class="spot-image"
            />
            <div class="spot-info">
              <h3 class="spot-name">{{ item.spotName }}</h3>
              <p class="spot-location">
                <Icon icon="mdi:map-marker-outline" />
                {{ item.spotLocation || '南宁市' }}
              </p>
              <div class="spot-meta">
                <span class="spot-level" v-if="item.spotLevel">
                  <Icon icon="mdi:star" />
                  {{ item.spotLevel }}
                </span>
                <span class="spot-price">
                  {{ item.adultTicketPrice === 0 ? '免费' : '¥' + item.adultTicketPrice }}
                </span>
              </div>
            </div>
          </div>
          <button class="remove-btn" @click.stop="handleRemove(item.spotId)">
            <Icon icon="mdi:heart" class="heart-icon filled" />
          </button>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { Icon } from '@iconify/vue'
import { getFavoritesByUserId, removeFavorite } from '@/api/favorite'
import { getSpotById } from '@/api/spot'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

const favorites = ref([])
const loading = ref(true)

const loadFavorites = async () => {
  try {
    const userId = userStore.getUserId()
    if (!userId) {
      favorites.value = []
      return
    }

    const res = await getFavoritesByUserId(userId)
    const favoriteList = res.data || []

    const spotPromises = favoriteList.map(async (fav) => {
      try {
        const spotRes = await getSpotById(fav.spotId)
        return spotRes.data ? { ...fav, ...spotRes.data } : null
      } catch {
        return null
      }
    })

    const spots = await Promise.all(spotPromises)
    favorites.value = spots.filter(s => s !== null)
  } catch (error) {
    console.error('获取收藏失败:', error)
    favorites.value = []
  }
}

const goToSpot = (spotId) => {
  router.push(`/spot/${spotId}`)
}

const handleRemove = async (spotId) => {
  if (!confirm('确定要取消收藏吗？')) return

  try {
    const userId = userStore.getUserId()
    await removeFavorite(userId, spotId)
    await loadFavorites()
  } catch (error) {
    console.error('取消收藏失败:', error)
    alert('取消失败，请重试')
  }
}

onMounted(async () => {
  try {
    await loadFavorites()
  } finally {
    loading.value = false
  }
})
</script>

<style scoped>
.favorites-page {
  min-height: 100vh;
  background: var(--color-bg-secondary);
}

.header {
  background: white;
  border-bottom: 1px solid var(--color-border);
  padding: 16px 0;
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.back-link {
  width: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.back-icon {
  font-size: 24px;
  color: var(--color-text-primary);
}

.page-title {
  font-size: 18px;
  font-weight: 600;
  color: var(--color-text-primary);
}

.header-placeholder {
  width: 40px;
}

.loading-state {
  padding: 100px 0;
  text-align: center;
}

.loading-spinner {
  width: 40px;
  height: 40px;
  border: 3px solid var(--color-border);
  border-top-color: var(--color-primary);
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin: 0 auto 16px;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.empty-state {
  padding: 100px 0;
  text-align: center;
}

.empty-icon {
  font-size: 80px;
  color: var(--color-border);
  margin-bottom: 16px;
}

.empty-state p {
  color: var(--color-text-secondary);
  margin-bottom: 24px;
}

.browse-btn {
  display: inline-block;
  padding: 12px 32px;
  background: var(--color-primary);
  color: white;
  border-radius: 24px;
  text-decoration: none;
  font-size: 14px;
}

.favorites-list {
  padding: 16px 0;
}

.favorite-card {
  background: white;
  border-radius: 12px;
  padding: 12px;
  margin-bottom: 12px;
  display: flex;
  align-items: center;
  gap: 12px;
}

.card-content {
  flex: 1;
  display: flex;
  gap: 12px;
}

.spot-image {
  width: 100px;
  height: 80px;
  border-radius: 8px;
  object-fit: cover;
}

.spot-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 4px;
}

.spot-name {
  font-size: 16px;
  font-weight: 600;
  color: var(--color-text-primary);
  margin: 0;
}

.spot-location {
  font-size: 12px;
  color: var(--color-text-secondary);
  display: flex;
  align-items: center;
  gap: 4px;
  margin: 0;
}

.spot-meta {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-top: 4px;
}

.spot-level {
  font-size: 12px;
  color: var(--color-primary);
  display: flex;
  align-items: center;
  gap: 2px;
}

.spot-price {
  font-size: 16px;
  font-weight: 600;
  color: var(--color-price);
}

.remove-btn {
  width: 40px;
  height: 40px;
  border: none;
  background: transparent;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}

.heart-icon {
  font-size: 24px;
  color: var(--color-primary);
}

.heart-icon.filled {
  color: var(--color-primary);
}
</style>
