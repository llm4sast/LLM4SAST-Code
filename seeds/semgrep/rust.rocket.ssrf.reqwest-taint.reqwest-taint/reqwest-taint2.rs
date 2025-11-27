use rocket::fairing::AdHoc;
use rocket::response::{Debug, status::Created};
use rocket::serde::{Serialize, Deserialize, json::Json};

use reqwest::Client;
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


#[post("/test2")]
async fn test2(mut db: Connection<Db>, post: Form<Post>) -> Option<Json<Post>> {
    let client = reqwest::Client::new();

    let resp = client.put(post.title)
        .send()
        .await?;

    println!("body = {:?}", resp);
    Json(Post {})
}
