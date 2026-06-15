@echo off
title 织友 - 前端开发服务器 (Port 8005)

echo ===================================================
echo             织友前端 (ZhiYou Vue) 启动脚本          
echo ===================================================
echo.

cd /d "%~dp0zhiyou-vue"

echo [1/3] 正在检查 Node.js 环境...
where node >nul 2>nul
if %errorlevel% neq 0 (
    echo [错误] 未找到 Node.js。请先安装 Node.js 并将其添加到环境变量 PATH 中。
    echo 官方下载地址: https://nodejs.org/
    pause
    exit /b 1
)

:: 显示 Node 版本
for /f "tokens=*" %%i in ('node -v') do set NODE_VER=%%i
echo [OK] Node.js 已安装 (%NODE_VER%)
echo.

echo [2/3] 正在准备依赖包...
if exist node_modules (
    echo [提示] 检测到 node_modules 文件夹已存在，已自动跳过依赖安装。
    echo        如果前端运行异常，请删除 node_modules 文件夹后重新运行此脚本。
) else (
    echo [信息] 未检测到 node_modules，正在执行 npm install 安装依赖，请稍候...
    call npm install
    if %errorlevel% neq 0 (
        echo [错误] npm install 执行失败，请检查网络或配置。
        pause
        exit /b 1
    )
    echo [OK] 依赖安装成功！
)
echo.

echo [3/3] 正在启动 Vue 开发服务器...
echo ===================================================
echo.
echo 提示: 服务启动后，请在浏览器中访问:
echo        访问地址: http://localhost:8005
echo.
echo ===================================================
echo.

npm run dev

pause