#!/bin/bash
set -eux

# move into the app dir
cd /home/ec2-user/app

# build a fresh image
docker build -t planter-app .

# run the container in detached mode
docker run -d \
  --name planter-app \
  -p 8080:8080 \
  -e DB_URL="${DB_URL}" \
  -e DB_USERNAME="${DB_USERNAME}" \
  -e DB_PASSWORD="${DB_PASSWORD}" \
  --restart unless-stopped \
  planter-app

echo "✅ planter-app is up and running"