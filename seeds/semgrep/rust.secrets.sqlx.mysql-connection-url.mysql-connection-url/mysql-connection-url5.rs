use sqlx::mysql::{MySqlConnection, MySqlConnectOptions, MySqlPool, MySqlPoolOptions};



async fn test5() -> Result<(), sqlx::Error> {
  let pool = sqlx::MySqlPool::connect_lazy("mysql://username:password@localhost/database").await?;
  use_pool(pool);
  Ok(())
}
