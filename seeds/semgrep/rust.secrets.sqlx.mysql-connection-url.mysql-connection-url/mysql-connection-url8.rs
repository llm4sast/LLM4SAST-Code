use sqlx::mysql::{MySqlConnection, MySqlConnectOptions, MySqlPool, MySqlPoolOptions};


fn test8() -> Result<(), sqlx::Error> {
  let url = Url::parse("mysql://username:password@localhost/database")?;
  let options = MySqlConnectOptions::from_url(url)?;
  use_opts(options);
  Ok(())
}

