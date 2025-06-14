output "s3_bucket_name" {
  description = "S3 bucket name"
  value       = aws_s3_bucket.website.bucket
}

output "cloudfront_domain" {
  description = "Cloudfront distribution domain name"
  value       = aws_cloudfront_distribution.cdn.domain_name
}
