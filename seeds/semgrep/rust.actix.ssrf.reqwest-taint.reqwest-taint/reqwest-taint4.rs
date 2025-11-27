use actix_web::{get, App, HttpResponse, HttpServer, Responder, Error, web, HttpRequest};
use serde::{Deserialize, Serialize};
use reqwest::Client;
#[derive(Deserialize, Serialize)]
struct IndexQuery {
    hello: String
}
#[post("/test4")]
async fn test_4(
  params: web::Query<IndexQuery>,
  col: web::Data<Collection<User>>,
) -> impl Responder {
  let client = reqwest::Client::new();
  let uri = std::fmt::format("http:
  let resp = client.post(uri)
    .send()
    .await?;
  println!("body = {:?}", resp);
  Ok(HttpResponse::Ok().body("Hello world!!!"))
}