#!/bin/bash
set -eux

# move into the app dir
cd /home/ec2-user/app

# build a fresh image
docker build -t vitruviux-app .

# run the container in detached mode
docker run -d \
  --name vitruviux-app \
  -p 80:8080 \
  -e PROJECT_NAME="${PROJECT_NAME}" \
  -e DB_NAME="${DB_NAME}" \
  -e DB_URL="${DB_URL}" \
  -e DB_USERNAME="${DB_USERNAME}" \
  -e DB_PASSWORD="${DB_PASSWORD}" \
  -e GITHUB_CLIENT_ID="${GITHUB_CLIENT_ID}" \
  -e GITHUB_CLIENT_SECRET="${GITHUB_CLIENT_SECRET}" \
  -e AUTH_CALLBACK_URL="${AUTH_CALLBACK_URL}" \
  -e JWT_SECRET="${JWT_SECRET}" \
  -e AWS_ACCOUNT_ID="${AWS_ACCOUNT_ID}" \
  vitruviux-app

echo "✅ vitruviux-app is up and running"