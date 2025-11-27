use postgres::Client;
async fn test2() -> Result<(), anyhow::Error> {
  let (client, connection) = postgres::Config::new()
    .host(shard_host_name.as_str())
    .user("postgres")
    .password("")
    .dbname("ninja")
    .keepalives_idle(std::time::Duration::from_secs(30))
    .connect(NoTls)
    .map_err(|e| {
        error!(log, "failed to connect to {}: {}", &shard_host_name, e);
        Error::new(ErrorKind::Other, e)
    })?;
  Ok(())
}