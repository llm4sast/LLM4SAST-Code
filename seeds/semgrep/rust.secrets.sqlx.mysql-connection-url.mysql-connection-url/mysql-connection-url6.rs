use sqlx::mysql::{MySqlConnection, MySqlConnectOptions, MySqlPool, MySqlPoolOptions};



async fn test6() -> Result<(), sqlx::Error> {
  let url = "mysql://username:password@localhost/database";
  let pool_options = MySqlPoolOptions::new().max_connections(5);
  let pool = pool_options.connect(url).await?;
  use_pool(pool);
  Ok(())
}
