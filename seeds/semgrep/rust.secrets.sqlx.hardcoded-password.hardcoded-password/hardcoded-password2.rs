use sqlx::{Connection, ConnectOptions};
use sqlx::mysql::{MySqlConnectOptions, MySqlConnection, MySqlPool, MySqlSslMode};
use sqlx::postgres::{PgConnectOptions, PgConnection, PgPool, PgSslMode};
async fn test2() -> Result<(), sqlx::Error> {
  let conn = PgConnectOptions::new()
    .host("secret-host")
    .port(2525)
    .username("secret-user")
    .password("secret-password")
    .ssl_mode(PgSslMode::Require)
    .connect()
    .await?;
  use_connection(conn);
  Ok(())
}