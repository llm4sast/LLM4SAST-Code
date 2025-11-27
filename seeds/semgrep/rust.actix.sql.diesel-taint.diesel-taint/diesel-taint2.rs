use actix_web::{get, post, web, App, HttpResponse, HttpServer, Responder, HttpRequest, http::StatusCode};
use actix_web::{get, App, HttpResponse, HttpServer, Responder, web::{Bytes, Json}, http::StatusCode, Error, CustomizeResponder, Either};
use diesel::pg::PgConnection;
use diesel::prelude::*;
#[post("/test2")]
async fn test_2(
  params: web::Query<IndexQuery>,
  db: web::Data<PgConnection>,
) -> impl Responder {
  let query = "SELECT * FROM testing.users WHERE username = '" + String::from(params.hello) + "';";
  let results = diesel::sql_query(query.as_str())
    .execute(db)
    .map_err(diesel::r2d2::Error::QueryError)?;
  do_smth(results);
  HttpResponse::Ok().body("Hello world!")
}