use sqlx::postgres::{PgConnection, PgConnectOptions, PgPool, PgPoolOptions};



fn test3() -> Result<(), sqlx::Error> {
  let url = "postgresql://localhost?dbname=mydb&user=postgres&password=postgres";
  let options = PgConnectOptions::from_str(url)?;
  use_opts(options);
  Ok(())
}
