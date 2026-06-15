# 智游邕城 - 南宁旅游服务平台

一个基于 Vue 3 + Spring Boot 的南宁旅游服务 Web 应用，提供景点浏览、分时预约门票、订单管理、个人中心等功能。

## 功能模块

- **景点浏览** - 查看南宁热门景点详情（支持 A 级景区等级显示）
- **分时预约** - 精确到小时段的门票预订，减少排队等待
- **订单管理** - 在线预订景区门票，查看和管理预订订单
- **景点收藏** - 收藏感兴趣的景点，在个人中心集中管理
- **实时人流** - 实时展示景区客流，并提供智能游览建议（🔧 功能开发中）
- **扫码入园** - 配合景区闸机设备，支持游客刷码入园（🔧 功能开发中）
- **用户中心** - 个人信息、积分管理及订单、收藏列表展示

---

## 技术栈

| 层级 | 技术 |
|------|------|
| **前端** | Vue 3 + Vite + Pinia + Vue Router + Axios + Iconify |
| **后端** | Spring Boot 3.2 + Java 17 + Maven |
| **数据存储**| 基于 JSON 文件的轻量级存储（`data/` 目录） |
| **部署容器**| Docker + Docker Compose + Nginx |

---

## 项目结构

```text
wx-zhiyouyongcheng/
├── data/                    # JSON 数据库文件
│   ├── spot.json            # 景点数据
│   ├── ticket.json          # 票务数据
│   ├── order.json           # 订单数据
│   ├── user.json            # 用户数据
│   ├── favorite.json        # 收藏数据
│   └── flow.json            # 实时人流数据
├── zhiyou-vue/              # 前端 Vue 项目
│   ├── src/
│   │   ├── api/             # API 请求封装
│   │   ├── views/           # 页面组件
│   │   ├── router/          # Vue Router 路由配置
│   │   └── stores/          # Pinia 状态管理
│   ├── vite.config.js       # Vite 配置文件
│   └── nginx.conf           # 容器 Nginx 代理配置
├── zhiyou-spring-boot/      # 后端 Spring Boot 项目
│   └── src/main/java/com/zhiyou/
│       ├── controller/      # 控制层 API
│       ├── service/         # 业务逻辑层
│       ├── model/           # 数据模型
│       └── repository/      # JSON 数据持久层
├── docker-compose.yml       # 开发环境 Docker 编排
├── docker-compose.prod.yml  # 生产环境（阿里云）Docker 编排
├── 启动前端.bat             # 本地一键启动前端脚本
└── 启动后端.bat             # 本地一键启动后端脚本
```

---

## 快速开始

### 环境要求
- **JDK 17+**
- **Node.js 18+**
- **Maven 3.6+**
- **Docker & Docker Compose** (部署所需)

### 1. 本地开发调试

项目根目录下提供了 Windows 一键启动脚本，你可以直接双击运行：
* 双击运行 `启动后端.bat` (后端启动于端口 `8004`)
* 双击运行 `启动前端.bat` (前端启动于端口 `8005`)

或者手动在终端运行：

**启动后端：**
```bash
cd zhiyou-spring-boot
mvn spring-boot:run
```
后端服务运行于：[http://localhost:8004](http://localhost:8004)

**启动前端：**
```bash
cd zhiyou-vue
npm install
npm run dev
```
前端服务运行于：[http://localhost:8005](http://localhost:8005)

---

## Docker 容器化部署

### 1. 本地环境部署（调试容器）

直接使用默认的 Docker Compose 部署：
```bash
docker compose up -d --build
```
- 前端访问地址：[http://localhost:8005](http://localhost:8005)
- 后端访问地址：[http://localhost:8004](http://localhost:8004)

### 2. 生产环境部署（阿里云等云服务器）

我们为云服务器提供了优化的生产环境配置 [docker-compose.prod.yml](docker-compose.prod.yml)。此配置默认将前端绑定到主机的 **`8005`** 端口，同时隐藏后端服务以确保安全。

**部署步骤：**
1. 将项目代码克隆或上传到云服务器。
2. 确保阿里云安全组中已开放 **`8005`** 端口。
3. 在项目根目录下执行以下命令构建并启动容器：
   ```bash
   docker compose -f docker-compose.prod.yml up -d --build
   ```
4. 访问地址：`http://<你的云服务器公网IP>:8005`。

---

## API 接口概览

| 模块 | 请求方法 | 路径 | 说明 |
|------|------|------|------|
| **景点** | `GET` | `/api/spot/list` | 获取启用的景点列表 |
| **景点** | `GET` | `/api/spot/{id}` | 获取景点详情 |
| **票务** | `GET` | `/api/ticket/list` | 获取景区票务列表 |
| **订单** | `GET` | `/api/order/list` | 获取订单列表 |
| **订单** | `POST`| `/api/order/create` | 创建预订订单 |
| **用户** | `POST`| `/api/user/login` | 用户登录 |
| **收藏** | `GET` | `/api/favorite/list` | 获取收藏的景点列表 |
| **人流** | `GET` | `/api/flow/list` | 获取景区实时客流 |

---

## License

[MIT](LICENSE)
