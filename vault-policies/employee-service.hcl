path "secret/employee-service" {
  policy = "read"
}

path "secret/employee-service/*" {
  policy = "read"
}

path "secret/application" {
  policy = "read"
}

path "secret/application/*" {
  policy = "read"
}
