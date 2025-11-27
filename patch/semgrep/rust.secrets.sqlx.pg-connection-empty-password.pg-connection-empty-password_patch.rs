use sqlx::postgres::{PgConnection, PgConnectOptions, PgPool, PgPoolOptions};
async fn test1(auth: &str) -> Result<(), sqlx::Error> {
  let url = fomrat!("postgresql://{}@localhost/database", auth);
  let conn = PgConnection::connect(url).await?;
  use_connection(conn);
  Ok(())
}