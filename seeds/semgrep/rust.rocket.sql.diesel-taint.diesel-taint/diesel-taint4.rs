use rocket::fairing::AdHoc;
use rocket::response::{Debug, status::Created};
use rocket::serde::{Serialize, Deserialize, json::Json};
use rocket_db_pools::{Database, Connection};
use rocket_db_pools::diesel::{MysqlPool, prelude::*};
use rocket_db_pools::diesel::mysql::MysqlQueryBuilder;
use rocket::Request;
type Result<T, E = Debug<diesel::result::Error>> = std::result::Result<T, E>;
#[derive(Database)]
#[database("diesel_mysql")]
struct Db(MysqlPool);
#[derive(Debug, Clone, Deserialize, Serialize, Queryable, Insertable)]
#[serde(crate = "rocket::serde")]
struct Post {
    #[serde(skip_deserializing)]
    id: Option<i64>,
    title: String,
    text: String,
    #[serde(skip_deserializing)]
    published: bool,
}
diesel::table! {
    posts (id) {
        id -> Nullable<BigInt>,
        title -> Text,
        text -> Text,
        published -> Bool,
    }
}
#[post("/test4")]
async fn test4(request: &'r Request<'_>, mut db: Connection<Db>) -> Option<Json<Post>> {
    let value = request.uri().query();
    let query = String::from("SELECT * FROM testing.users WHERE username = '");
    query.push_str(value);
    query.push_str("';");
    let results = rocket_db_pools::diesel::sql_query(query)
        .execute(db)
        .map_err(diesel::r2d2::Error::QueryError)?;
    do_smth(results);
    Json(Post {})
}