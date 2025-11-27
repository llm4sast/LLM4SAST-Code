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
#[get("/test3")]
async fn test3(user: Collection<User>, id: String) -> Option<Json<Post>> {
    let doc_json = format!(r#"
        {{"$where": "this.name == '{}'"}}
    "#, id);
    let filter_doc: bson::Document = serde_json::from_str(&doc_json).unwrap();
    let result = user.find(filter_doc, None).await.unwrap();
    let raw_doc = result.current();
    do_smth( raw_doc );
    Json(Post {})
}