use sqlx::postgres::{PgConnection, PgConnectOptions, PgPool, PgPoolOptions};
async fn test1(pwd: &str) -> Result<(), sqlx::Error> {
  let url = fomrat!("postgresql://username:{}@localhost/database", pwd);
  let conn = PgConnection::connect(url).await?;
  use_connection(conn);
  Ok(())
}
