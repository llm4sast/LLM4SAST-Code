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
#[get("/test2?<name>")]
async fn test2(db: Connection<Db>, project: Project) -> Result<Json<String>> {
    let query = "SELECT * FROM testing.users WHERE username = '" + String::from(project.name) + "';";
    let results = db.simple_query(&query).await.unwrap();
    do_smth(&results);
    Ok(Json(String::from("Hello world")))
}