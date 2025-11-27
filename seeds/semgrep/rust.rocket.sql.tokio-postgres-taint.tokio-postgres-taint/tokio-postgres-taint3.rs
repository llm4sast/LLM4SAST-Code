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
#[post("/todo", data = "<task>")]
async fn test3(db: Connection<Db>, task: Json<Task>) -> Result<Json<String>> {
    let query = String::from("SELECT * FROM testing.users WHERE username = '");
    query.push_str(task.name);
    query.push_str("';");
    let results = db.simple_query(&query).await.unwrap();
    do_smth(&results);
    Ok(Json(String::from("Hello world")))
}