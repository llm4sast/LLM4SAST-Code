use sqlx::postgres::{PgConnection, PgConnectOptions, PgPool, PgPoolOptions};



async fn test5() -> Result<(), sqlx::Error> {
  let pool = sqlx::PgPool::connect_lazy("postgresql://username:password@localhost/database").await?;
  use_pool(pool);
  Ok(())
}
