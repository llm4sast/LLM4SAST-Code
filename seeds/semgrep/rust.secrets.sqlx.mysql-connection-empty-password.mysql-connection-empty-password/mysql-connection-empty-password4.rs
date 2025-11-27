use sqlx::mysql::{MySqlConnection, MySqlConnectOptions, MySqlPool, MySqlPoolOptions};



async fn test4() -> Result<(), sqlx::Error> {
  let url = "mysql://username@localhost/database";

  let pool = MySqlPool::connect(url).await?;
  use_pool(pool);
  Ok(())
}
