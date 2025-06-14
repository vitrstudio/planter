#!/bin/bash

set -e

REGION="us-east-1"
PROJECT_ID="a9191e2a"
PROJECT_NAME="planter"
AMI_ID="ami-0953476d60561c955"
DB_NAME="planterdb"
DB_USER="planterdbadmin"
DB_PASSWORD="planterdbpassword"

terraform init
terraform apply -auto-approve \
  -var="region=$REGION" \
  -var="project_id=$PROJECT_ID" \
  -var="project_name=$PROJECT_NAME" \
  -var="ami_id=$AMI_ID" \
  -var="db_name=$DB_NAME" \
  -var="db_user=$DB_USER" \
  -var="db_password=$DB_PASSWORD"