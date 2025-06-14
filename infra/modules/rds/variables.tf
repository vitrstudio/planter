variable "project_id" {
  description = "Project unique id"
  type        = string
}

variable "project_name" {
  description = "Project name"
  type        = string
}

variable "vpc_id" {
  description = "Vpc id"
  type        = string
}

variable "private_subnet_ids" {
  description = "Private subnet ids"
  type        = list(string)
}

variable "ec2_cidr_block" {
  description = "Cidr block or security group ip range allowed to access rds"
  type        = string
}

variable "db_name" {
  description = "Database name"
  type        = string
}

variable "db_user" {
  description = "Database username"
  type        = string
}

variable "db_password" {
  description = "Database password"
  type        = string
  sensitive   = true
}
