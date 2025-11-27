use sqlx::postgres::{PgConnection, PgConnectOptions, PgPool, PgPoolOptions};



async fn test5() -> Result<(), sqlx::Error> {
  let pool = sqlx::PgPool::connect_lazy("postgresql://username@localhost/database").await?;
  use_pool(pool);
  Ok(())
}
