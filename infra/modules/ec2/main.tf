resource "aws_security_group" "ec2_sg" {
  name        = "${var.project_name}-api-sg"
  description = "${var.project_name} security group"
  vpc_id      = var.vpc_id

  ingress {
    description = "jordi-temp-access"
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["79.152.207.97/32"]
  }

  ingress {
    description = "github-actions-ssh"
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = [
      "140.82.112.0/20",
      "185.199.108.0/22",
      "192.30.252.0/22"
    ]
  }

  ingress {
    description = "all-http-traffic"
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    description = "all-outbound-traffic"
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "${var.project_name}-ec2-sg"
  }
}

resource "aws_instance" "api" {
  ami                         = var.ami_id
  instance_type               = var.instance_type
  subnet_id                   = var.subnet_id
  key_name                    = var.project_name
  user_data                   = var.user_data
  vpc_security_group_ids      = [aws_security_group.ec2_sg.id]
  associate_public_ip_address = true

  tags = {
    Name = "${var.project_name}-api"
  }
}
