use actix_web::{get, post, web, App, HttpResponse, HttpServer, Responder, HttpRequest, http::StatusCode};
use actix_web::{get, App, HttpResponse, HttpServer, Responder, web::{Bytes, Json}, http::StatusCode, Error, CustomizeResponder, Either};
use deadpool_postgres::{Client, Pool};
async fn test_3(
  req: HttpRequest,
  db_pool: web::Data<Pool>,
) -> (impl Responder, StatusCode) {
  let params = web::Query::<IndexQuery>::from_query(req.query_string()).unwrap();
  let client: Client = db_pool.get().await.map_err(MyError::PoolError)?;
  let query = String::from("SELECT * FROM testing.users WHERE username = '");
  query.push_str(params.hello);
  query.push_str("';");
  let data = client.simple_query(&query).await.unwrap();
  do_smth(&data);
  (HttpResponse::Ok().body("Hello world!"), StatusCode::OK)
}