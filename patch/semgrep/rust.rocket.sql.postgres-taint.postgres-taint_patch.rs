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
#[post("/test1", data = "<post>")]
async fn test1(post: Json<Post>) -> Result<Created<Json<Post>>> {
  let post = sanitize(&post);
  let mut client = Client::connect("host=localhost user=postgres", NoTls)?;
  let results = client.batch_execute("SELECT * FROM testing.users WHERE username = '" + String::from(post.title) + "' LIMIT 10;")?;
  do_smth(&results);
  Ok(Created::new("/").body(post))
}