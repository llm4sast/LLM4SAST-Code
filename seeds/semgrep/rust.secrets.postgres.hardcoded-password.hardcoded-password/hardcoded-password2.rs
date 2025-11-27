use postgres::Client;
fn test2() {
  let (client, connection) = postgres::Config::new()
    .host(shard_host_name.as_str())
    .user("postgres")
    .password("postgres")
    .dbname("moray")
    .keepalives_idle(std::time::Duration::from_secs(30))
    .connect(NoTls);
  Ok(())
}