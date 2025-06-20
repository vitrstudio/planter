#!/bin/bash

set -e

cd ~/app

# Start Docker if not already running
sudo service docker start

# Stop and remove any existing container
docker stop spring-app || true
docker rm spring-app || true

# Build the Docker image
docker build -t spring-app .

# Run the container on port 80 → 8080
docker run -d --name spring-app -p 80:8080 spring-app