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
#[post("/test1", data = "<post>")]
async fn test1(client: Client, post: Json<Post>) -> Result<Created<Json<Post>>> {
    let col: Collection<User> = client.database("foo").collection("bar");
    let doc_json = format!(r#"
        {{"$where": "this.name == '{}'"}}
    "#, post.title);
    let filter_doc: bson::Document = serde_json::from_str(&doc_json).unwrap();
    let result = col.find(filter_doc, None).await.unwrap();
    let raw_doc = result.current();
    do_smth( raw_doc );
    Ok(Created::new("/").body(post))
}