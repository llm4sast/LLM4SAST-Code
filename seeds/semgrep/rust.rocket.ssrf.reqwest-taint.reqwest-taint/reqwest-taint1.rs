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

#[post("/test1", data = "<post>")]
async fn test1(client: Client, post: Json<Post>) -> Result<Created<Json<Post>>> {

    let resp = client.get("http://" + String::from(post.title))
      .send()
      .await?;

    println!("body = {:?}", resp);

    Ok(Created::new("/").body(post))
}
