use actix_web::{get, post, web, App, HttpResponse, HttpServer, Responder, HttpRequest, http::StatusCode};
use actix_web::{get, App, HttpResponse, HttpServer, Responder, web::{Bytes, Json}, http::StatusCode, Error, CustomizeResponder, Either};
use sqlx::{postgres::PgPoolOptions, Pool, Postgres, Row, QueryBuilder};
async fn test_3(
  req: HttpRequest,
  db_pool: web::Data<Pool<PgPoolOptions>>,
) -> (impl Responder, StatusCode) {
  let params = web::Query::<IndexQuery>::from_query(req.query_string()).unwrap();
  let query = String::from("SELECT * FROM testing.users WHERE username = '");
  query.push_str(params.hello);
  query.push_str("';");
  let results = sqlx::query(query)
    .fetch_one(&mut *db)
    .await
    .ok();
  do_smth(&results);
  (HttpResponse::Ok().body("Hello world!"), StatusCode::OK)
}