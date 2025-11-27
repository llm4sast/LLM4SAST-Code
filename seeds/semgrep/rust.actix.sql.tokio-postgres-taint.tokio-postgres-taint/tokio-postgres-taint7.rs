use actix_web::{get, post, web, App, HttpResponse, HttpServer, Responder, HttpRequest, http::StatusCode};
use actix_web::{get, App, HttpResponse, HttpServer, Responder, web::{Bytes, Json}, http::StatusCode, Error, CustomizeResponder, Either};
use deadpool_postgres::{Client, Pool};
async fn test_7(req: HttpRequest, client: Client) -> HttpResponse {
  let params = web::Query::<IndexQuery>::from_query(req.query_string()).unwrap();
  let query = String::from("SELECT * FROM testing.users WHERE username = '");
  query.push_str(params.name);
  query.push_str("';");
  let data = client.simple_query(&query).await.unwrap();
  do_smth(&data);
  HttpResponse::Ok().body("Hello world!")
}