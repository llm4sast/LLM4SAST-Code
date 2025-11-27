use actix_web::{get, post, web, App, HttpResponse, HttpServer, Responder, HttpRequest, http::StatusCode};
use actix_web::{get, App, HttpResponse, HttpServer, Responder, web::{Bytes, Json}, http::StatusCode, Error, CustomizeResponder, Either};
use sqlx::{postgres::PgPoolOptions, Pool, Postgres, Row, QueryBuilder};
#[post("/test2")]
async fn test_2(
  params: web::Query<IndexQuery>,
  db_pool: web::Data<Pool<PgPoolOptions>>,
) -> impl Responder {
  let query = "SELECT * FROM testing.users WHERE username = '" + String::from(params.hello) + "';";
  let results = sqlx::query(query.as_str())
      .fetch_one(&db_pool)
      .await
      .ok();
  do_smth(results);
  HttpResponse::Ok().body("Hello world!")
}