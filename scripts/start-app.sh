#!/bin/bash

set -e  # Fail fast on any error

cd ~/app

# Clean up previous container
docker stop planter-app || true
docker rm planter-app || true

# Confirm required files exist
ls -lh app.jar Dockerfile || exit 1

# Build Docker image
docker build -t planter-app .

# Run app
docker run -d --name planter-app -p 8080:8080 \
  -e DB_URL=$DB_URL \
  -e DB_USERNAME=$DB_USERNAME \
  -e DB_PASSWORD=$DB_PASSWORD \
  -e PROJECT_NAME=$PROJECT_NAME \
  planter-app