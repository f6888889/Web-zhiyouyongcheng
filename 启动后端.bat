@echo off
title 织友 - 后端服务 (Port 8004)

echo ===================================================
echo         织友后端 (ZhiYou Spring Boot) 启动脚本      
echo ===================================================
echo.

cd /d "%~dp0zhiyou-spring-boot"

echo [1/2] 正在检查 Java 环境...
where java >nul 2>nul
if %errorlevel% neq 0 (
    echo [错误] 未找到 Java 运行环境。请安装 JDK 17 或以上版本，并配置好环境变量。
    pause
    exit /b 1
)

:: 获取并显示 Java 版本第一行
for /f "tokens=*" %%i in ('java -version 2^>^&1') do (
    echo [OK] Java 已安装: %%i
    goto :check_maven
)

:check_maven
echo.
echo [2/2] 正在启动 Spring Boot 服务...
echo ===================================================
echo.
echo 提示: 后端服务正在启动中，API 端口为 8004
echo        注意: 数据库等基础设施需提前启动
echo.
echo ===================================================
echo.

if exist mvnw.cmd (
    call mvnw.cmd spring-boot:run
) else (
    where mvn >nul 2>nul
    if %errorlevel% equ 0 (
        call mvn spring-boot:run
    ) else (
        echo [错误] 未找到 mvnw.cmd 且系统未安装 Maven。请确保项目完整。
        pause
        exit /b 1
    )
)

if %errorlevel% neq 0 (
    echo.
    echo [错误] 后端服务运行出错或已停止。
)

pause