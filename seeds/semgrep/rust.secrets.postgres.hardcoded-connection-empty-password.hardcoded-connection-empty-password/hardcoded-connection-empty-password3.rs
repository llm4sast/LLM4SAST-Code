use postgres::{NoTls, Config};



fn test3 (ip_addr: &str) {
  let url = format!("postgresql://user@{}:5432", ip_addr);

  let config = postgres::Config::from_str(&url).unwrap();
  let connect = config.connect(postgres::NoTls).unwrap();
  do_smth(connect);
}
