use sqlx::mysql::{MySqlConnection, MySqlConnectOptions, MySqlPool, MySqlPoolOptions};



async fn test9() -> Result<(), sqlx::Error> {
  let conn = sqlx::AnyConnection::connect("mysql://username:password@localhost/database").await?;
  use_connection(conn);
  Ok(())
}

