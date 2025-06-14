#!/bin/bash

export "$(grep -v '^#' .env | xargs)"
set -e

REGION="us-east-1"
APPLICATION_ID="a9191e2a"
AMI_ID="ami-0953476d60561c955"

terraform destroy -auto-approve \
  -var="region=$REGION" \
  -var="application_id=$APPLICATION_ID" \
  -var="application_name=$APPLICATION_NAME" \
  -var="ami_id=$AMI_ID" \
  -var="db_name=$DB_NAME" \
  -var="db_user=$DB_USERNAME" \
  -var="db_password=$DB_PASSWORD"