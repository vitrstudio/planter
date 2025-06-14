resource "aws_route53_zone" "main" {
  name = var.domain_name

  tags = {
    Name = var.project_name
  }
}

resource "aws_route53_record" "www" {
  zone_id = aws_route53_zone.main.zone_id
  name    = "www"
  type    = "A"
  ttl     = 300
  records = [var.root_record_ip]

  depends_on = [aws_route53_zone.main]
}
