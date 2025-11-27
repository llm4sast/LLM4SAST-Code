use actix_web::{get, App, HttpResponse, HttpServer, Responder, Error, web, HttpRequest};
use serde::{Deserialize, Serialize};
use reqwest::Client;
#[derive(Deserialize, Serialize)]
struct IndexQuery {
    hello: String
}
#[post("/test2")]
async fn test_2(
  info: web::Path<Info>,
) -> Result<String> {
  let client = reqwest::Client::new();
  let resp = client.delete(info.hello)
    .send()
    .await?;
  println!("body = {:?}", resp);
  Ok(HttpResponse::Ok().body("Hello world!!!"))
}