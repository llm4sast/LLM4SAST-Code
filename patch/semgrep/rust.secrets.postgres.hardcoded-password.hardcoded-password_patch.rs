use postgres::Client;
fn okTest1() {
  let mut config = postgres::Config::new();
  config
      .host(std::env::var("HOST").expect("set HOST"))
      .user(std::env::var("USER").expect("set USER"))
      .password(std::env::var("PWD").expect("set PWD"))
      .port(std::env::var("PORT").expect("set PORT"));
  let client = config.connect(NoTls).unwrap();
  Ok(())
}