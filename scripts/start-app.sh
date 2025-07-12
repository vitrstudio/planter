#!/bin/bash
set -euo pipefail

echo "=== 📁 Verifying files in ~/app ==="
cd ~/app
ls -lh

echo "=== 🧪 Verifying required files ==="
[ -f app.jar ] || { echo "❌ Missing app.jar"; exit 1; }
[ -f Dockerfile ] || { echo "❌ Missing Dockerfile"; exit 1; }

echo "=== 🛑 Stopping old container (if exists) ==="
docker stop planter-app || true
docker rm planter-app || true

echo "=== 🏗️  Building Docker image ==="
docker build -t planter-app .

echo "=== 🚀 Starting container ==="
docker run -d --name planter-app -p 80 :8080 \
  -e DB_URL="$DB_URL" \
  -e DB_USERNAME="$DB_USERNAME" \
  -e DB_PASSWORD="$DB_PASSWORD" \
  -e PROJECT_NAME="$PROJECT_NAME" \
  planter-app

echo "✅ Deployment successful. Container is running."