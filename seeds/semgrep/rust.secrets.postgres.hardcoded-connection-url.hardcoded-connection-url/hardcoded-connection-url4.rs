use postgres::{NoTls, Config};



fn test4 (db_name: &str) {
  let url = format!("user=postgres password=password host=localhost port=5432 dbname={} replication=database", db_name);

  let config = postgres::Config::from_str(&url).unwrap();
  let connect = config.connect(tokio_postgres::NoTls).unwrap();
  do_smth(connect);
}
