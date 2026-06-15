<template>
  <div class="user-page">
    <header class="header">
      <div class="header-content container">
        <router-link to="/" class="back-link">
          <Icon icon="mdi:arrow-left" class="back-icon" />
        </router-link>
        <h1 class="page-title">个人中心</h1>
        <div class="header-placeholder"></div>
      </div>
    </header>

    <section class="profile-section">
      <div class="profile-bg"></div>
      <div class="profile-content container">
        <div class="avatar-box">
          <img
            :src="userInfo?.avatar || 'https://picsum.photos/100/100?random=avatar'"
            alt="头像"
            class="avatar"
          />
        </div>
        <h2 class="username">{{ userInfo?.nickname || '游客用户' }}</h2>
        <div class="user-level">
          <Icon icon="mdi:crown" class="level-icon" />
          {{ userInfo?.level || 'V1' }} 会员
        </div>
        <div class="user-stats">
          <div class="stat-item">
            <span class="stat-num">{{ userInfo?.points || 0 }}</span>
            <span class="stat-label">积分</span>
          </div>
        </div>
      </div>
    </section>

    <section class="quick-actions container">
      <div class="actions-grid">
        <router-link to="/orders" class="action-item">
          <Icon icon="mdi:clipboard-text-outline" class="action-icon" />
          <span>我的订单</span>
        </router-link>
        <div class="action-item" @click="showDeveloping">
          <Icon icon="mdi:ticket-outline" class="action-icon" />
          <span>优惠券</span>
        </div>
        <router-link to="/favorites" class="action-item">
          <Icon icon="mdi:heart-outline" class="action-icon" />
          <span>我的收藏</span>
        </router-link>
        <div class="action-item" @click="showDeveloping">
          <Icon icon="mdi:history" class="action-icon" />
          <span>浏览历史</span>
        </div>
      </div>
    </section>

    <section class="menu-section container">
      <div class="menu-list">
        <div class="menu-item" @click="showDeveloping">
          <Icon icon="mdi:bell-outline" class="menu-icon" />
          <span class="menu-text">消息通知</span>
          <Icon icon="mdi:chevron-right" class="menu-arrow" />
        </div>
        <div class="menu-item" @click="showDeveloping">
          <Icon icon="mdi:map-marker-outline" class="menu-icon" />
          <span class="menu-text">常用地址</span>
          <Icon icon="mdi:chevron-right" class="menu-arrow" />
        </div>
        <div class="menu-item" @click="showDeveloping">
          <Icon icon="mdi:credit-card-outline" class="menu-icon" />
          <span class="menu-text">支付方式</span>
          <Icon icon="mdi:chevron-right" class="menu-arrow" />
        </div>
        <div class="menu-item" @click="showDeveloping">
          <Icon icon="mdi:shield-outline" class="menu-icon" />
          <span class="menu-text">账号安全</span>
          <Icon icon="mdi:chevron-right" class="menu-arrow" />
        </div>
      </div>

      <div class="menu-list">
        <div class="menu-item" @click="showDeveloping">
          <Icon icon="mdi:help-circle-outline" class="menu-icon" />
          <span class="menu-text">帮助中心</span>
          <Icon icon="mdi:chevron-right" class="menu-arrow" />
        </div>
        <div class="menu-item" @click="showDeveloping">
          <Icon icon="mdi:information-outline" class="menu-icon" />
          <span class="menu-text">关于我们</span>
          <Icon icon="mdi:chevron-right" class="menu-arrow" />
        </div>
        <div class="menu-item" @click="showDeveloping">
          <Icon icon="mdi:settings-outline" class="menu-icon" />
          <span class="menu-text">设置</span>
          <Icon icon="mdi:chevron-right" class="menu-arrow" />
        </div>
      </div>
    </section>

    <section class="logout-section container">
      <button v-if="isLoggedIn" class="logout-btn" @click="handleLogout">
        退出登录
      </button>
      <button v-else class="login-btn" @click="goToLogin">
        登录 / 注册
      </button>
    </section>

    <footer class="footer">
      <p>智游邕城 v1.0.0</p>
      <p>广西外国语学院创业项目</p>
    </footer>
  </div>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { Icon } from '@iconify/vue'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

const userInfo = computed(() => userStore.userInfo)
const isLoggedIn = computed(() => userStore.isLoggedIn)

const goToLogin = () => {
  router.push('/login')
}

const handleLogout = () => {
  if (confirm('确定要退出登录吗？')) {
    userStore.logout()
    router.push('/')
  }
}

const showDeveloping = () => {
  alert('功能开发中，敬请期待！')
}

onMounted(() => {
  userStore.loadUser()
})
</script>

<style scoped>
.user-page {
  min-height: 100vh;
  background: var(--color-bg-secondary);
  padding-bottom: 40px;
}

.header {
  background: white;
  border-bottom: 1px solid var(--color-border);
  padding: 12px 0;
  position: sticky;
  top: 0;
  z-index: 50;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.back-link {
  display: flex;
  align-items: center;
  color: var(--color-text);
}

.back-icon {
  font-size: 1.3rem;
}

.page-title {
  font-size: 1.1rem;
  font-weight: 600;
}

.header-placeholder {
  width: 40px;
}

.profile-section {
  position: relative;
  padding-bottom: 40px;
}

.profile-bg {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 160px;
  background: linear-gradient(135deg, var(--color-primary) 0%, var(--color-primary-dark) 100%);
  border-radius: 0 0 40px 40px;
}

.profile-content {
  position: relative;
  text-align: center;
  padding-top: 50px;
}

.avatar-box {
  width: 90px;
  height: 90px;
  margin: 0 auto 16px;
  border-radius: 50%;
  padding: 3px;
  background: linear-gradient(135deg, #FFD700, #FFA500);
}

.avatar {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  object-fit: cover;
  border: 3px solid white;
}

.username {
  font-size: 1.3rem;
  font-weight: 600;
  color: var(--color-text);
  margin-bottom: 8px;
}

.user-level {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  background: linear-gradient(135deg, #FFD700, #FFA500);
  color: white;
  padding: 4px 12px;
  border-radius: 12px;
  font-size: 0.8rem;
  font-weight: 600;
  margin-bottom: 20px;
}

.level-icon {
  font-size: 0.9rem;
}

.user-stats {
  display: flex;
  justify-content: center;
}

.stat-item {
  text-align: center;
  padding: 0 30px;
}

.stat-num {
  display: block;
  font-size: 1.5rem;
  font-weight: 700;
  color: var(--color-text);
}

.stat-label {
  font-size: 0.85rem;
  color: var(--color-text-secondary);
}

.quick-actions {
  margin-top: -20px;
}

.actions-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 12px;
  background: white;
  padding: 20px;
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-md);
}

.action-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  padding: 12px;
  border-radius: var(--radius-md);
  cursor: pointer;
  transition: background 0.2s;
}

.action-item:hover {
  background: var(--color-bg-secondary);
}

.action-icon {
  font-size: 1.5rem;
  color: var(--color-primary);
}

.action-item span {
  font-size: 0.8rem;
  color: var(--color-text);
}

.menu-section {
  margin-top: 24px;
}

.menu-list {
  background: white;
  border-radius: var(--radius-lg);
  margin-bottom: 16px;
  overflow: hidden;
}

.menu-item {
  display: flex;
  align-items: center;
  padding: 16px 20px;
  cursor: pointer;
  transition: background 0.2s;
  border-bottom: 1px solid var(--color-border);
}

.menu-item:last-child {
  border-bottom: none;
}

.menu-item:hover {
  background: var(--color-bg-secondary);
}

.menu-icon {
  font-size: 1.3rem;
  color: var(--color-text-secondary);
  margin-right: 16px;
}

.menu-text {
  flex: 1;
  font-size: 0.95rem;
  color: var(--color-text);
}

.menu-arrow {
  font-size: 1.2rem;
  color: var(--color-text-light);
}

.logout-section {
  margin-top: 24px;
}

.logout-btn {
  width: 100%;
  background: white;
  color: var(--color-error);
  padding: 14px;
  border-radius: var(--radius-md);
  font-size: 1rem;
  font-weight: 500;
  transition: background 0.2s;
}

.logout-btn:hover {
  background: #FEE2E2;
}

.login-btn {
  width: 100%;
  background: var(--color-primary);
  color: white;
  padding: 14px;
  border-radius: var(--radius-md);
  font-size: 1rem;
  font-weight: 500;
  transition: opacity 0.2s;
}

.login-btn:hover {
  opacity: 0.9;
}

.footer {
  text-align: center;
  padding: 30px 0;
  color: var(--color-text-light);
}

.footer p {
  font-size: 0.8rem;
  margin-bottom: 4px;
}
</style>
