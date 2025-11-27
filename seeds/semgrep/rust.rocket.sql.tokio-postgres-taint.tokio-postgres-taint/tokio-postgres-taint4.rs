use rocket::fairing::{self, AdHoc};
use rocket_db_pools::{deadpool_postgres, Database, Connection};
use rocket::serde::json::Json;
struct Project {
    name: String
}
#[derive(Database)]
#[database("pg")]
struct Db(deadpool_postgres::Pool);
type Result<T, E = rocket::response::Debug<sqlx::Error>> = std::result::Result<T, E>;
async fn test4(request: &'r Request<'_>, db: Connection<Db>) -> request::Outcome<Self, ()> {
    let value = request.headers().get_one("headername");
    let query = format!("SELECT * FROM testing.users WHERE username = '{}';", value);
    let results = db.simple_query(&query).await.unwrap();
    do_smth(&results)
}