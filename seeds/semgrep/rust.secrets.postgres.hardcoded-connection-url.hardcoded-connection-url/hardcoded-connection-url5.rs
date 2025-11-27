use postgres::{NoTls, Config};


fn test5 () {
  let connect =

      postgres::Client::connect("user=postgres password=password host=localhost port=5432")
        .unwrap();
  do_smth(connect);
}

