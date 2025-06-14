output "public_nacl_id" {
  description = "Public network acl id"
  value       = aws_network_acl.public.id
}

output "private_nacl_id" {
  description = "Private network acl id"
  value       = aws_network_acl.private.id
}
