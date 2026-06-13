@echo off
echo ========================================
echo   ZhiYou Frontend - Startup Script
echo ========================================
echo.

cd /d "%~dp0zhiyou-vue"

echo [1/3] Checking Node.js...
where node >nul 2>nul
if %errorlevel% neq 0 (
    echo [ERROR] Node.js not found. Please install Node.js and set PATH.
    pause
    exit /b 1
)
echo [OK] Node.js found

echo [2/3] Installing dependencies (first run takes time)...
call npm install

if %errorlevel% neq 0 (
    echo [ERROR] npm install failed
    pause
    exit /b 1
)

echo.
echo [3/3] Starting Vue dev server...
echo ========================================
echo.
echo After startup, visit: http://localhost:3000
echo.

npm run dev

pause
