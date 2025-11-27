use sqlx::mysql::{MySqlConnection, MySqlConnectOptions, MySqlPool, MySqlPoolOptions};
async fn test1() -> Result<(), sqlx::Error> {
  let url = "mysql://username@localhost/database";
  let conn = MySqlConnection::connect(url).await?;
  use_connection(conn);
  Ok(())
}