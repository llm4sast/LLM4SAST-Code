use actix_web::{get, post, web, App, HttpResponse, HttpServer, Responder, HttpRequest, http::StatusCode};
use actix_web::{get, App, HttpResponse, HttpServer, Responder, web::{Bytes, Json}, http::StatusCode, Error, CustomizeResponder, Either};
use sqlx::{postgres::PgPoolOptions, Pool, Postgres, Row, QueryBuilder};
#[post("/test1")]
async fn test_1(
  info: web::Path<Info>,
  db_pool: web::Data<Pool<PgPoolOptions>>,
) -> Result<String> {
  let query = format!("SELECT * FROM testing.users WHERE username = '{}';", info.username);
  QueryBuilder::new(query)
      .build()
      .fetch_one(&db_pool)
      .await
      .ok();
  Ok("Welcome!")
}