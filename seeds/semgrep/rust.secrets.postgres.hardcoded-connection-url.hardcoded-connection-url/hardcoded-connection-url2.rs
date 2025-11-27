use postgres::{NoTls, Config};


fn test2 () {
  let url = "postgresql://user:pwd12345@localhost:5432";

  let config = Config::from_str(url).unwrap();
  let connect = config.connect(tokio_postgres::NoTls).unwrap();
  do_smth(connect);
}
