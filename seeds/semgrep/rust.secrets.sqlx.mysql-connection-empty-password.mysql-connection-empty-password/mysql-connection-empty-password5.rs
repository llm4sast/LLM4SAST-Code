use sqlx::mysql::{MySqlConnection, MySqlConnectOptions, MySqlPool, MySqlPoolOptions};



async fn test5() -> Result<(), sqlx::Error> {

  let pool = sqlx::MySqlPool::connect_lazy("mysql://username@localhost/database").await?;
  use_pool(pool);
  Ok(())
}
