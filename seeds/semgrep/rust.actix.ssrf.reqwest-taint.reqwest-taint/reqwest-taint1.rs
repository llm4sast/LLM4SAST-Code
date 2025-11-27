use actix_web::{get, App, HttpResponse, HttpServer, Responder, Error, web, HttpRequest};
use serde::{Deserialize, Serialize};
use reqwest::Client;
#[derive(Deserialize, Serialize)]
struct IndexQuery {
    hello: String
}
#[get("/test1")]
async fn test_1(
    req: HttpRequest,
    client: web::Data<Client>
) -> Result<impl Responder, Error> {
    let params = web::Query::<IndexQuery>::from_query(req.query_string()).unwrap();
    let resp = client.get("http:
      .send()
      .await?;
    println!("body = {:?}", resp);
    Ok(HttpResponse::Ok().body("Hello world!!!"))
}