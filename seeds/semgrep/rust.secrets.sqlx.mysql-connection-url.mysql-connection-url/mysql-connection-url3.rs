use sqlx::mysql::{MySqlConnection, MySqlConnectOptions, MySqlPool, MySqlPoolOptions};



fn test3() -> Result<(), sqlx::Error> {
  let url = "mysql://username:password@localhost/database";
  let options = MySqlConnectOptions::from_str(url)?;
  use_opts(options);
  Ok(())
}
