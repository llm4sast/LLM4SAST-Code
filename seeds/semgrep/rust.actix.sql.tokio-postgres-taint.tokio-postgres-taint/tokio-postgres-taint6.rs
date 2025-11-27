use actix_web::{get, post, web, App, HttpResponse, HttpServer, Responder, HttpRequest, http::StatusCode};
use actix_web::{get, App, HttpResponse, HttpServer, Responder, web::{Bytes, Json}, http::StatusCode, Error, CustomizeResponder, Either};
use deadpool_postgres::{Client, Pool};
async fn test_6(path: web::Path<(u32,)>,db_pool: web::Data<Pool>,) -> HttpResponse {
  let client = db_pool.get().await.map_err(MyError::PoolError)?;
  let query = String::from("SELECT * FROM testing.users WHERE username = '");
  query.push_str(path.into_inner());
  query.push_str("';");
  let data = client.simple_query(&query).await.unwrap();
  do_smth(&data);
  HttpResponse::Ok().body("Hello world!")
}