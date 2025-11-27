use sqlx::{Connection, ConnectOptions};
use sqlx::mysql::{MySqlConnectOptions, MySqlConnection, MySqlPool, MySqlSslMode};
use sqlx::postgres::{PgConnectOptions, PgConnection, PgPool, PgSslMode};
async fn test3() -> Result<(), sqlx::Error> {
  let pg = PgConnectOptions::new();
  let conn = pg.host("secret-host")
    .port(2525)
    .username("secret-user")
    .password("secret-password")
    .ssl_mode(PgSslMode::Require)
    .connect()
    .await?;
  use_connection(conn);
  Ok(())
}