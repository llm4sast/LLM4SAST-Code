use sqlx::mysql::{MySqlConnection, MySqlConnectOptions, MySqlPool, MySqlPoolOptions};



fn test7() -> Result<(), sqlx::Error> {
  let mut options: MySqlConnectOptions = "mysql://root:password@localhost/db".parse()?;
  use_opts(options);
  Ok(())
}

