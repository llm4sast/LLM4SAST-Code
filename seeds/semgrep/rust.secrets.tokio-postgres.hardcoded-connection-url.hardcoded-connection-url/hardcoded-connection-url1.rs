use tokio_postgres::{NoTls, Config};

async fn test1 () {
  let config =
      tokio_postgres::config::Config::from_str("postgresql://user:pwd12345@localhost:5432")
      .unwrap();
  let connect = config.connect(tokio_postgres::NoTls).await.unwrap();
  do_smth(connect);
}
