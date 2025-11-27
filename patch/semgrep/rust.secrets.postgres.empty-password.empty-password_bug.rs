use postgres::Client;
fn test1() {
  let mut config = postgres::Config::new();
  config
      .host(std::env::var("HOST").expect("set HOST"))
      .user(std::env::var("USER").expect("set USER"))
      .password("")
      .port(std::env::var("PORT").expect("set PORT"));
  let (client, connection) = config.connect(NoTls);
  Ok(())
}