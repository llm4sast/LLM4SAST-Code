use postgres::{NoTls, Config};
fn test1 (passwd: &str) {
  let url = format!("postgresql://user:{}@localhost:5432", passwd);
  let config = Config::from_str(&url).unwrap();
  let connect = config.connect(tokio_postgres::NoTls).unwrap();
  do_smth(connect);
}