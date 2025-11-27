use actix_web::{get, App, HttpResponse, HttpServer, Responder, Error, web, HttpRequest};
use serde::{Deserialize, Serialize};
use reqwest::Client;
#[derive(Deserialize, Serialize)]
struct IndexQuery {
    hello: String
}
#[post("/test3")]
async fn test_3(
  info: web::Path<Info>,
  client: web::Data<Client>,
) -> Result<String> {
  let resp = client.put(format!("https:
    .send()
    .await?;
  println!("body = {:?}", resp);
  Ok(HttpResponse::Ok().body("Hello world!!!"))
}