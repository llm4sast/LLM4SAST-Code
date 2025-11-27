use sqlx::mysql::{MySqlConnection, MySqlConnectOptions, MySqlPool, MySqlPoolOptions};
async fn test1(pwd: &str) -> Result<(), sqlx::Error> {
  let url = fomrat!("mysql://username:{}@localhost/database", pwd);
  let conn = MySqlConnection::connect(url).await?;
  use_connection(conn);
  Ok(())
}