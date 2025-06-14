variable "project_name" {
  description = "Project name"
  type        = string
}

variable "azs" {
  description = "Availability zones"
  type    = list(string)
}
