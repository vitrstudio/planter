output "hosted_zone_id" {
  description = "Route53 hosted zone id"
  value       = aws_route53_zone.main.id
}

output "name_servers" {
  description = "Name servers list"
  value       = aws_route53_zone.main.name_servers
}
