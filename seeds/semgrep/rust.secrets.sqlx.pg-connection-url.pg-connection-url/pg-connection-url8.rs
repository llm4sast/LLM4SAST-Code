use sqlx::postgres::{PgConnection, PgConnectOptions, PgPool, PgPoolOptions};


fn test8() -> Result<(), sqlx::Error> {
  let url = Url::parse("postgresql://username:password@localhost/database")?;
  let options = PgConnectOptions::from_url(url)?;
  use_opts(options);
  Ok(())
}

