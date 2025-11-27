use rocket::fairing::AdHoc;
use rocket::response::{Debug, status::Created};
use rocket::serde::{Serialize, Deserialize, json::Json};
use rocket::Request;
use postgres::{Client, NoTls};
type Result<T, E = Debug<diesel::result::Error>> = std::result::Result<T, E>;
#[serde(crate = "rocket::serde")]
struct Post {
  #[serde(skip_deserializing)]
  id: Option<i64>,
  title: String,
  text: String,
  #[serde(skip_deserializing)]
  published: bool,
}
#[post("/test2")]
async fn test2(post: Form<Post>) -> Option<Json<Post>> {
  let mut client = Client::connect("host=localhost user=postgres", NoTls)?;
  let query = String::from("SELECT * FROM testing.users WHERE username = '");
  query.push_str(post.title);
  query.push_str("';");
  for row in client.query(query)? {
    let id: i32 = row.get(0);
    let name: &str = row.get(1);
    let data: Option<&[u8]> = row.get(2);
    println!("found person: {} {} {:?}", id, name, data);
  }
  Json(Post {})
}