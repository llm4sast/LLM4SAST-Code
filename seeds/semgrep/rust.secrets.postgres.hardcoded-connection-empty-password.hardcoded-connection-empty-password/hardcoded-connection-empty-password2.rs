use postgres::{NoTls, Config};


fn test2 () {
  let url = "postgresql://user@localhost:5432";

  let config = Config::from_str(url).unwrap();
  let connect = config.connect(postgres::NoTls).unwrap();
  do_smth(connect);
}

