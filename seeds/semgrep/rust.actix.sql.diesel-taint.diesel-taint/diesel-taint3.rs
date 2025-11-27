use actix_web::{get, post, web, App, HttpResponse, HttpServer, Responder, HttpRequest, http::StatusCode};
use actix_web::{get, App, HttpResponse, HttpServer, Responder, web::{Bytes, Json}, http::StatusCode, Error, CustomizeResponder, Either};
use diesel::pg::PgConnection;
use diesel::prelude::*;
async fn test_3(
  req: HttpRequest,
  db: web::Data<PgConnection>,
) -> (impl Responder, StatusCode) {
  let params = web::Query::<IndexQuery>::from_query(req.query_string()).unwrap();
  let query = String::from("SELECT * FROM testing.users WHERE username = '");
  query.push_str(params.hello);
  query.push_str("';");
  let results = diesel::sql_query(query)
    .execute(db)
    .map_err(diesel::r2d2::Error::QueryError)?;
  do_smth(&results);
  (HttpResponse::Ok().body("Hello world!"), StatusCode::OK)
}