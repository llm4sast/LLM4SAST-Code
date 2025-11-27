use sqlx::postgres::{PgConnection, PgConnectOptions, PgPool, PgPoolOptions};



async fn test2() -> Result<(), sqlx::Error> {
  let conn = sqlx::PgConnection::connect("postgres://username:password@localhost/database").await?;
  use_connection(conn);
  Ok(())
}
