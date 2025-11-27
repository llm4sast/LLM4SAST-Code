use sqlx::postgres::{PgConnection, PgConnectOptions, PgPool, PgPoolOptions};


async fn test9() -> Result<(), sqlx::Error> {
  let pool = sqlx::Pool::connect_lazy("postgresql://username:password@localhost/database").await?;
  use_pool(pool);
  Ok(())
}
