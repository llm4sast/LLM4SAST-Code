use tokio_postgres::{NoTls, Config};



async fn test3 (ip_addr: &str) {
  let url = format!("postgresql://user@{}:5432", ip_addr);
  let config = tokio_postgres::Config::from_str(&url).unwrap();
  let connect = config.connect(tokio_postgres::NoTls).await.unwrap();
  do_smth(connect);
}
