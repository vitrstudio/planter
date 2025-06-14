output "instance_id" {
  description = "Ec2 instance id"
  value       = aws_instance.api.id
}

output "public_ip" {
  description = "Ec2 instance public ip"
  value       = aws_instance.api.public_ip
}
