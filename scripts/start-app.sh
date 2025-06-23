#!/bin/bash
set -e

APP_DIR=~/app
cd "$APP_DIR"

# Stop and remove any existing container
docker stop planter-app || true
docker rm planter-app || true

# Build Docker image
docker build -t planter-app .

# Run Docker container with environment variables
docker run -d \
  --name planter-app \
  -e DB_URL="$DB_URL" \
  -e DB_USERNAME="$DB_USERNAME" \
  -e DB_PASSWORD="$DB_PASSWORD" \
  -e PROJECT_NAME="$PROJECT_NAME" \
  -p 8080:8080 \
  planter-app
