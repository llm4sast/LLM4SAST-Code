use tokio_postgres::{NoTls, Config};


async fn test5 () {
  let connect =
      tokio_postgres::connect("user=postgres password=password host=localhost port=5432")
        .await
        .unwrap();
  do_smth(connect);
}
