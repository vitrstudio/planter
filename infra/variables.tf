variable "project_id" {
  description = "Project unique id"
  type        = string
}

variable "project_name" {
  description = "Project name"
  type        = string
}

variable "region" {
  description = "AWS region"
  type        = string
  default     = "us-east-1"
}

# Disabled until a domain is acquired
# variable "domain_name" {
#   description = "The root domain name"
#   type        = string
# }

variable "ami_id" {
  description = "Ec2 ami id"
  type        = string
  default     = "ami-0953476d60561c955"
}

variable "db_name" {
  type        = string
  description = "Database name"
}

variable "db_user" {
  type        = string
  description = "Database username"
}

variable "db_password" {
  type        = string
  description = "Database password"
  sensitive   = true
}
