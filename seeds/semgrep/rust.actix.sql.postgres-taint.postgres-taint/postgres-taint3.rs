use actix_web::{get, post, web, App, HttpResponse, HttpServer, Responder, HttpRequest, http::StatusCode};
use actix_web::{get, App, HttpResponse, HttpServer, Responder, web::{Bytes, Json}, http::StatusCode, Error, CustomizeResponder, Either};
use postgres::{Client, NoTls};
async fn test_3(
  req: HttpRequest,
) -> (impl Responder, StatusCode) {
  let mut client = Client::connect("host=localhost user=postgres", NoTls)?;
  let params = web::Query::<IndexQuery>::from_query(req.query_string()).unwrap();
  let query = String::from("SELECT * FROM testing.users WHERE username = '");
  query.push_str(params.hello);
  query.push_str("';");
  let results = client.execute(query)?;
  do_smth(&results);
  (HttpResponse::Ok().body("Hello world!"), StatusCode::OK)
}