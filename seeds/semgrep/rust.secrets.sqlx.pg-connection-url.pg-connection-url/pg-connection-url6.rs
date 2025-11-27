use sqlx::postgres::{PgConnection, PgConnectOptions, PgPool, PgPoolOptions};



async fn test6() -> Result<(), sqlx::Error> {
  let url = "postgresql://username:password@localhost/database";
  let pool_options = PgPoolOptions::new().max_connections(5);
  let pool = pool_options.connect(url).await?;
  use_pool(pool);
  Ok(())
}
