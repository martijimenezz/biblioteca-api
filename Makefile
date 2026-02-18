.PHONY: help clean build test integration docker docker-compose stop docker-logs fixtures

help:
	@echo "Biblioteca API - Available Commands"
	@echo "===================================="
	@echo "make help              - Show this help message"
	@echo "make clean             - Clean build artifacts"
	@echo "make build             - Build the project"
	@echo "make test              - Run all tests"
	@echo "make test-unit         - Run unit tests only"
	@echo "make test-integration  - Run integration tests"
	@echo "make check             - Run code quality checks"
	@echo "make docker            - Build Docker image"
	@echo "make docker-compose    - Start services with Docker Compose"
	@echo "make stop              - Stop Docker Compose services"
	@echo "make docker-logs       - Show Docker logs"
	@echo "make fixtures          - Load test fixtures"
	@echo "make full-build        - Full build and Docker setup"
	@echo ""

clean:
	@echo "Cleaning build artifacts..."
	mvn clean

build:
	@echo "Building project..."
	mvn clean package -DskipTests

test:
	@echo "Running all tests..."
	mvn test

test-unit:
	@echo "Running unit tests..."
	mvn test -Dtest=*ServiceTest

test-integration:
	@echo "Running integration tests..."
	mvn test -Dtest=*ControllerIT

check:
	@echo "Running code quality checks..."
	mvn checkstyle:check

docker:
	@echo "Building Docker image..."
	docker build -t biblioteca-api:latest .
	@echo "Docker image built successfully!"

docker-compose:
	@echo "Starting Docker Compose services..."
	docker-compose up --build -d
	@echo "Services started! Application available at http://localhost:8080"
	@echo "Waiting for services to be ready..."
	sleep 10
	@echo "Checking health..."
	curl http://localhost:8080/api/authors

stop:
	@echo "Stopping Docker Compose services..."
	docker-compose down
	@echo "Services stopped!"

docker-logs:
	@echo "Showing Docker logs..."
	docker-compose logs -f app

fixtures:
	@echo "Loading test fixtures..."
	docker-compose exec -T database psql -U postgres -d biblioteca -f /fixtures.sql || true
	@echo "Fixtures loaded!"

full-build: clean build check test docker docker-compose
	@echo "Full build completed successfully!"

package-dist:
	@echo "Creating distribution package..."
	mkdir -p dist
	mvn clean package -DskipTests
	cp target/biblioteca-api-*.jar dist/
	cp docker-compose.yml dist/
	cp Dockerfile dist/
	cp .env.example dist/
	cp README.md dist/
	cp DEPLOYMENT.md dist/
	cp fixtures.sql dist/
	cd dist && zip -r biblioteca-api-$(shell date +%Y%m%d).zip . && cd ..
	@echo "Distribution package created at dist/"

install-hooks:
	@echo "Installing Git hooks..."
	cp scripts/pre-commit.sh .git/hooks/pre-commit || true
	chmod +x .git/hooks/pre-commit
	@echo "Git hooks installed!"
