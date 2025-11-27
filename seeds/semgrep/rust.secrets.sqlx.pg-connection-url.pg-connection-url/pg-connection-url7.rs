use sqlx::postgres::{PgConnection, PgConnectOptions, PgPool, PgPoolOptions};


fn test7() -> Result<(), sqlx::Error> {
  let mut options: PgConnectOptions = "postgresql://root:password@localhost/db".parse()?;
  use_opts(options);
  Ok(())
}

