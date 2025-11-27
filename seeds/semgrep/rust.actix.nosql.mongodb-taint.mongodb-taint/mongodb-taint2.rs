use actix_web::{get, App, HttpResponse, HttpServer, Responder, Error, web, HttpRequest};
use serde::{Deserialize, Serialize};
use mongodb::{Client, Collection};
#[derive(Deserialize, Serialize)]
struct IndexQuery {
    hello: String
}
#[post("/test2")]
async fn test_2(
  info: web::Path<Info>,
  client: web::Data<Client>,
) -> Result<String> {
  let col: Collection<User> = client.database("foo").collection("bar");
  let doc_json = "{{\"$where\": \"this.name == '" + String::from(info.username) + "'\"}}";
  let filter_doc: bson::Document = serde_json::from_str(&doc_json).unwrap();
  let result = col.find_one(filter_doc, None).await.unwrap();
  do_smth(result);
  Ok("Welcome!")
}