resource "aws_lb_listener_rule" "http_listener_rule" {
  listener_arn = data.aws_lb_listener.service_lb_80_listener.arn
  priority     = local.lb_listener_rule_priority * 10

  action {
    type             = "forward"
    target_group_arn = data.aws_lb_target_group.target_group.arn
  }
  condition {
    path_pattern {
      values = local.lb_listener_paths
    }
  }

  tags = {
      ServiceName        = local.service_name
      ECSClusterName     = "${local.name_prefix}-cluster"
      Environment        = var.environment
      UseFargate         = var.use_fargate
      ManagedByTerraform = "true"
  }

  depends_on = [
    module.ecs-service
  ]
}
