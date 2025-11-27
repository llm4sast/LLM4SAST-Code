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
#[post("/test3")]
async fn test3(mut db: Connection<Db>, post: Option<Post>) -> Option<Json<Post>> {
    let mut query = QueryBuilder::new(
        "SELECT project_name, project_state FROM projects WHERE account_name = ",
    );
    query
        .push_bind(account_name)
        .push(" ORDER BY project_id DESC, " + post.title + " LIMIT ")
        .push_bind(limit);
    query
        .build()
        .fetch_one(&mut *db)
        .map_ok(|r| Json(Post { id: Some(r.id), title: r.title, text: r.text }))
        .await
        .ok()
}