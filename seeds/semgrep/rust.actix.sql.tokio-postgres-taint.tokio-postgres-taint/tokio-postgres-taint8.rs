use actix_web::{get, post, web, App, HttpResponse, HttpServer, Responder, HttpRequest, http::StatusCode};
use actix_web::{get, App, HttpResponse, HttpServer, Responder, web::{Bytes, Json}, http::StatusCode, Error, CustomizeResponder, Either};
use postgres::{Client, NoTls};
#[post("/test1")]
async fn test_1(
  info: web::Path<Info>,
) -> Result<String> {
  let mut client: Client = Client::connect("host=localhost user=postgres", NoTls)?;
  let query = format!("SELECT * FROM testing.users WHERE username = '{}';", info.username);
  client.batch_execute(query)?;
  Ok("Welcome!")
}