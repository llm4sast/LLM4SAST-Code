use actix_web::{get, App, HttpResponse, HttpServer, Responder, Error, web, HttpRequest};
use serde::{Deserialize, Serialize};
use mongodb::{Client, Collection};
#[derive(Deserialize, Serialize)]
struct IndexQuery {
    hello: String
}
#[post("/test4")]
async fn test_4(
  params: web::Query<IndexQuery>,
  col: web::Data<Collection<User>>,
) -> impl Responder {
  let doc_json = String::from("{{\"$where\": \"this.name == '");
  doc_json.push_str(params.hello);
  doc_json.push_str("'\"}}");
  let filter_doc: bson::Document = serde_json::from_str(&doc_json).unwrap();
  let results = col.find(filter_doc, None).await.unwrap();
  do_smth(results);
  HttpResponse::Ok().body("Hello world!")
}