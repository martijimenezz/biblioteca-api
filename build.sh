#!/bin/bash

# Build script for Biblioteca API

set -e

echo "====================================="
echo "   Biblioteca API Build Script       "
echo "====================================="
echo ""

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed"
    exit 1
fi

# Check if Docker is installed
if ! command -v docker &> /dev/null; then
    echo "Warning: Docker is not installed, skipping Docker build"
fi

# Parse arguments
BUILD_TYPE=${1:-"full"}

case $BUILD_TYPE in
    "clean")
        echo "Running clean build..."
        mvn clean
        echo "Clean completed"
        ;;
    "build")
        echo "Building with Maven..."
        mvn clean package
        echo "Build completed"
        ;;
    "test")
        echo "Running tests..."
        mvn test
        echo "Tests completed"
        ;;
    "integration")
        echo "Running integration tests..."
        mvn test -Dtest=*ControllerIT
        echo "Integration tests completed"
        ;;
    "docker")
        echo "Building Docker image..."
        docker build -t biblioteca-api:latest .
        echo "Docker build completed"
        ;;
    "docker-compose")
        echo "Starting Docker Compose..."
        docker-compose up --build
        ;;
    "stop-docker")
        echo "Stopping Docker Compose..."
        docker-compose down
        ;;
    "full"|*)
        echo "Running full build (clean, build, test, Docker)..."
        mvn clean package
        if command -v docker &> /dev/null; then
            docker build -t biblioteca-api:latest .
        fi
        echo "Full build completed"
        ;;
esac

echo ""
echo "====================================="
echo "   Build Process Finished            "
echo "====================================="
