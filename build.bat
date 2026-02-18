@echo off
REM Build script for Biblioteca API (Windows)

setlocal enabledelayedexpansion

echo =====================================
echo    Biblioteca API Build Script (Windows)
echo =====================================
echo.

REM Check if Maven is installed
where mvn >nul 2>nul
if errorlevel 1 (
    echo Error: Maven is not installed
    exit /b 1
)

REM Parse arguments
set BUILD_TYPE=%1
if "%BUILD_TYPE%"=="" set BUILD_TYPE=full

if "%BUILD_TYPE%"=="clean" (
    echo Running clean build...
    call mvn clean
    echo Clean completed
) else if "%BUILD_TYPE%"=="build" (
    echo Building with Maven...
    call mvn clean package
    echo Build completed
) else if "%BUILD_TYPE%"=="test" (
    echo Running tests...
    call mvn test
    echo Tests completed
) else if "%BUILD_TYPE%"=="integration" (
    echo Running integration tests...
    call mvn test -Dtest=*ControllerIT
    echo Integration tests completed
) else if "%BUILD_TYPE%"=="docker" (
    echo Building Docker image...
    docker build -t biblioteca-api:latest .
    echo Docker build completed
) else if "%BUILD_TYPE%"=="docker-compose" (
    echo Starting Docker Compose...
    docker-compose up --build
) else if "%BUILD_TYPE%"=="stop-docker" (
    echo Stopping Docker Compose...
    docker-compose down
) else (
    echo Running full build...
    call mvn clean package
    docker build -t biblioteca-api:latest .
    echo Full build completed
)

echo.
echo =====================================
echo    Build Process Finished
echo =====================================
