use sqlx::mysql::{MySqlConnection, MySqlConnectOptions, MySqlPool, MySqlPoolOptions};



async fn test2() -> Result<(), sqlx::Error> {

  let conn = sqlx::MySqlConnection::connect("mysql://username@localhost/database").await?;
  use_connection(conn);
  Ok(())
}
