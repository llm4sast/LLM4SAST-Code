use sqlx::postgres::{PgConnection, PgConnectOptions, PgPool, PgPoolOptions};
async fn test1() -> Result<(), sqlx::Error> {
  let url = "postgresql://username@localhost/database";
  let conn = PgConnection::connect(url).await?;
  use_connection(conn);
  Ok(())
}