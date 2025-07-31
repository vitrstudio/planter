#!/bin/bash
set -euo pipefail

echo "⏱️ $(date) — Starting deployment script"
cd /home/ec2-user/app || { echo "❌ /home/ec2-user/app missing"; exit 1; }

# Stop & remove old container
echo "🛑 Stopping old Docker container"
docker stop planter-app || true
docker rm   planter-app || true

# Remove old images
echo "🗑️ Removing old Docker images"
docker images "planter-app" --format "{{.ID}}" | xargs -r docker rmi -f || true

# Verify artifacts
echo "📦 Verifying new files"
[ -f app.jar ]     && echo "✅ app.jar"     || { echo "❌ Missing app.jar";     exit 1; }
[ -f Dockerfile ]  && echo "✅ Dockerfile"  || { echo "❌ Missing Dockerfile";  exit 1; }

# Build image
echo "🏗️  Building Docker image"
docker build -t planter-app . || { echo "❌ Docker build failed"; exit 1; }

# Run on port 8080
echo "🚀 Launching planter-app on port 8080"
docker run -d --name planter-app \
  -p 8080:8080 \
  -e DB_URL="$DB_URL" \
  -e DB_USERNAME="$DB_USERNAME" \
  -e DB_PASSWORD="$DB_PASSWORD" \
  -e PROJECT_NAME="$PROJECT_NAME" \
  planter-app || { echo "❌ Failed to start container"; exit 1; }

echo "✅ Deployment successful — planter-app running on port 8080"
