variable "project_name" {
  description = "Project name"
  type        = string
}

variable "vpc_id" {
  description = "Vpc id"
  type        = string
}

variable "subnet_id" {
  description = "Public subnet id"
  type        = string
}

variable "ami_id" {
  description = "Ami id"
  type        = string
}

variable "instance_type" {
  description = "Instance type"
  type        = string
  default     = "t2.micro"
}
