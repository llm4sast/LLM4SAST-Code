use actix_web::{get, post, web, App, HttpResponse, HttpServer, Responder, HttpRequest, http::StatusCode};
use actix_web::{get, App, HttpResponse, HttpServer, Responder, web::{Bytes, Json}, http::StatusCode, Error, CustomizeResponder, Either};
use diesel::pg::PgConnection;
use diesel::prelude::*;
#[post("/test1")]
async fn test_1(
  info: web::Path<Info>,
  db: web::Data<PgConnection>,
) -> Result<String> {
  let query = format!("SELECT * FROM testing.users WHERE username = '{}';", info.username);
  let results = diesel::sql_query(query)
      .execute(db)
      .map_err(diesel::r2d2::Error::QueryError)?;
  do_smth(results);
  Ok("Welcome!")
}