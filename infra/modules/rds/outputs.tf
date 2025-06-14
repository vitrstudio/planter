output "rds_endpoint" {
  description = "Rds instance dns address"
  value       = aws_db_instance.main.endpoint
}

output "rds_security_group_id" {
  description = "Rds security group id"
  value       = aws_security_group.db_sg.id
}
