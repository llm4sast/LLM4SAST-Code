use sqlx::{Connection, ConnectOptions};
use sqlx::mysql::{MySqlConnectOptions, MySqlConnection, MySqlPool, MySqlSslMode};
use sqlx::postgres::{PgConnectOptions, PgConnection, PgPool, PgSslMode};
sync fn test1() -> Result<(), sqlx::Error> {
  let conn = MySqlConnectOptions::new()
      .host("localhost")
      .username("root")
      .password(env!("pwd"))
      .database("db")
      .connect().await?;
  use_connection(conn);
  Ok(())
}