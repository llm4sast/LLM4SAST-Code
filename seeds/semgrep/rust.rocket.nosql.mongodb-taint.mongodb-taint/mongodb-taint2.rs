use rocket::fairing::AdHoc;
use rocket::response::{Debug, status::Created};
use rocket::serde::{Serialize, Deserialize, json::Json};
use rocket::Request;
use mongodb::{Client, Collection};
type Result<T, E = Debug<diesel::result::Error>> = std::result::Result<T, E>;
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
async fn test2(user: Collection<User>, post: Form<Post>) -> Option<Json<Post>> {
    let doc_json = "{{\"$where\": \"this.name == '" + String::from(post.title) + "'\"}}";
    let filter_doc: bson::Document = serde_json::from_str(&doc_json).unwrap();
    let result = user.find_one(filter_doc, None).await.unwrap();
    do_smth(result);
    Json(Post {})
}