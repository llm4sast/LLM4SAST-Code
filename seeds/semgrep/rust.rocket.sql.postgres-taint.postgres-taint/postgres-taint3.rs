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
#[get("/test3")]
async fn test3(id: String) -> Option<Json<Post>> {
  let mut client = Client::connect("host=localhost user=postgres", NoTls)?;
  let query = format!("SELECT * FROM testing.users WHERE username = '{}';", id);
  let results = client.execute(query)?;
  do_smth(results);
  Json(Post {})
}