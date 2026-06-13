@echo off
echo ========================================
echo   ZhiYou Backend - Startup Script
echo ========================================
echo.

cd /d "%~dp0zhiyou-spring-boot"

echo [1/2] Checking Maven...
where mvn >nul 2>nul
if %errorlevel% neq 0 (
    echo [ERROR] Maven not found. Please install Maven and set PATH.
    pause
    exit /b 1
)

echo [OK] Maven found
echo.

echo [2/2] Starting Spring Boot...
echo ========================================
echo.

cmd /k "mvn spring-boot:run"
