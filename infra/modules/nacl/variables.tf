variable "project_name" {
  description = "Project name"
  type        = string
}

variable "vpc_id" {
  description = "Vpc id"
  type        = string
}

variable "public_subnet_id" {
  description = "Public subnet id"
  type        = string
}

variable "private_subnet_ids" {
  description = "Private subnet id"
  type        = list(string)
}
