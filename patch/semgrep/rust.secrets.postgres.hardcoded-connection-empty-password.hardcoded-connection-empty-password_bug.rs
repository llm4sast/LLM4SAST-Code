use postgres::{NoTls, Config};
fn test1 () {
  let config =
      postgres::config::Config::from_str("postgresql://user@localhost:5432")
      .unwrap();
  let connect = config.connect(tokio_postgres::NoTls).unwrap();
  do_smth(connect);
}