use actix_web::{get, post, web, App, HttpResponse, HttpServer, Responder, HttpRequest, http::StatusCode};
use actix_web::{get, App, HttpResponse, HttpServer, Responder, web::{Bytes, Json}, http::StatusCode, Error, CustomizeResponder, Either};
use postgres::{Client, NoTls};
#[post("/test2")]
async fn test_2(
  params: web::Query<IndexQuery>,
) -> impl Responder {
  let mut client: Client = Client::connect("host=localhost user=postgres", NoTls)?;
  let query = "SELECT * FROM testing.users WHERE username = '" + String::from(params.hello) + "';";
  for row in client.query(query, &[])? {
      let id: i32 = row.get(0);
      let name: &str = row.get(1);
      let data: Option<&[u8]> = row.get(2);
      println!("found person: {} {} {:?}", id, name, data);
  }
  HttpResponse::Ok().body("Hello world!")
}