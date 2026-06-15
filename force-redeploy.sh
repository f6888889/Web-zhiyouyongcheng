#!/bin/bash

echo "=========================================="
echo "  智游邕城 - 强制重新构建部署"
echo "=========================================="
echo ""

# 进入项目目录
cd "$(dirname "$0")"

PROJECT_DIR=$(pwd)
echo "项目目录: $PROJECT_DIR"
echo ""

# 1. 停止所有容器
echo "[1/5] 停止所有容器..."
docker compose down
echo "✅ 容器已停止"
echo ""

# 2. 删除所有旧镜像
echo "[2/5] 删除所有旧镜像..."
docker rmi $(docker images | grep zhiyou | awk '{print $3}') 2>/dev/null || true
docker rmi $(docker images | grep "zhiyou" | awk '{print $3}') 2>/dev/null || true
echo "✅ 旧镜像已删除"
echo ""

# 3. 清理构建缓存
echo "[3/5] 清理构建缓存..."
docker builder prune -f 2>/dev/null || true
echo "✅ 构建缓存已清理"
echo ""

# 4. 强制重新构建（不使用缓存）
echo "[4/5] 强制重新构建镜像（这可能需要 10-15 分钟）..."
echo ""
docker compose build --no-cache
echo ""

if [ $? -ne 0 ]; then
    echo "❌ 构建失败！"
    exit 1
fi

echo "✅ 镜像构建成功"
echo ""

# 5. 启动容器
echo "[5/5] 启动容器..."
docker compose up -d
echo ""

if [ $? -ne 0 ]; then
    echo "❌ 启动失败！"
    exit 1
fi

echo "✅ 容器启动成功"
echo ""

# 等待几秒让容器启动
echo "等待容器完全启动..."
sleep 5

# 显示容器状态
echo ""
echo "=========================================="
echo "  容器状态"
echo "=========================================="
docker compose ps
echo ""

# 显示后端启动日志
echo ""
echo "=========================================="
echo "  后端启动日志（等待 10 秒收集日志）"
echo "=========================================="
sleep 10
docker compose logs --tail=50 zhiyou-backend
echo ""

# 显示前端启动日志
echo ""
echo "=========================================="
echo "  前端启动日志"
echo "=========================================="
docker compose logs --tail=20 zhiyou-frontend
echo ""

echo "=========================================="
echo "  🎉 部署完成！"
echo "=========================================="
echo ""
echo "访问地址："
echo "  前端：http://localhost:8005"
echo "  后端：http://localhost:8004"
echo ""
echo "查看日志："
echo "  docker compose logs -f zhiyou-backend"
echo "  docker compose logs -f zhiyou-frontend"
echo ""
