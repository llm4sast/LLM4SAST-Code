use sqlx::postgres::{PgConnection, PgConnectOptions, PgPool, PgPoolOptions};


async fn test4() -> Result<(), sqlx::Error> {
  let url = "postgresql://username:password@localhost/database";
  let pool = PgPool::connect(url).await?;
  use_pool(pool);
  Ok(())
}
