#!/bin/bash
set -euo pipefail

echo "⏱️ $(date) — Starting deployment script"
cd /home/ec2-user/app || { echo "❌ Failed to cd into /home/ec2-user/app"; exit 1; }

# Clean up old artifacts
echo "🧹 Removing old JAR files"
rm -f *.jar || true

# Stop and remove any existing container
echo "🛑 Stopping old Docker container (if running)"
docker stop planter-app 2>/dev/null || echo "No running container to stop"
echo "🧼 Removing old Docker container"
docker rm planter-app   2>/dev/null || echo "No container to remove"

# Remove old images
echo "🗑️ Removing old Docker images"
IMAGE_IDS=$(docker images "planter-app" --format "{{.ID}}")
if [ -n "$IMAGE_IDS" ]; then
  echo "$IMAGE_IDS" | xargs -r docker rmi -f
else
  echo "No images to remove"
fi

# Verify that the new artifacts are present
echo "📦 Verifying new files"
[ -f app.jar ]    && echo "✅ app.jar exists"    || { echo "❌ Missing app.jar";    exit 1; }
[ -f Dockerfile ] && echo "✅ Dockerfile exists" || { echo "❌ Missing Dockerfile"; exit 1; }

# Echo environment variables for debug
echo "=== 🌱 Environment Variables ==="
echo "DB_URL=$DB_URL"
echo "DB_USERNAME=$DB_USERNAME"
echo "PROJECT_NAME=$PROJECT_NAME"

# Build the Docker image
echo "🏗️  Building Docker image"
docker build -t planter-app . || { echo "❌ Docker build failed"; exit 1; }

# Run the container on host port 8080
echo "🚀 Starting new Docker container on host port 8080"
docker run -d --name planter-app \
  -p 8080:8080 \
  -e DB_URL="$DB_URL" \
  -e DB_USERNAME="$DB_USERNAME" \
  -e DB_PASSWORD="$DB_PASSWORD" \
  -e PROJECT_NAME="$PROJECT_NAME" \
  planter-app || { echo "❌ Failed to start Docker container"; exit 1; }

echo "✅ Deployment successful. Container is running on port 8080"
