use rocket::{Rocket, Build, futures, Request};
use rocket::fairing::{self, AdHoc};
use rocket::response::status::Created;
use rocket::serde::{Serialize, Deserialize, json::Json};
use rocket::form::{Form, Contextual};
use rocket_db_pools::{sqlx, Database, Connection};
use futures::{stream::TryStreamExt, future::TryFutureExt};
use sqlx::QueryBuilder;
#[derive(Database)]
#[database("sqlx")]
struct Db(sqlx::SqlitePool);
type Result<T, E = rocket::response::Debug<sqlx::Error>> = std::result::Result<T, E>;
#[derive(Debug, Clone, Deserialize, Serialize)]
#[serde(crate = "rocket::serde")]
struct Post {
    #[serde(skip_deserializing, skip_serializing_if = "Option::is_none")]
    id: Option<i64>,
    title: String,
    text: String,
}
#[post("/test1", data = "<post>")]
async fn test1(mut db: Connection<Db>, post: Json<Post>) -> Result<Created<Json<Post>>> {
    let post = sanitize(&post);
    db.prepare("SELECT * FROM testing.users WHERE username = '" + post.title + "';")
        .execute(&mut *db)
        .await?;
    Ok(Created::new("/").body(post))
}