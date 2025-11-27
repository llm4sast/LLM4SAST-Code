use actix_web::{get, post, web, App, HttpResponse, HttpServer, Responder, HttpRequest, http::StatusCode};
use actix_web::{get, App, HttpResponse, HttpServer, Responder, web::{Bytes, Json}, http::StatusCode, Error, CustomizeResponder, Either};
use deadpool_postgres::{Client, Pool};
async fn test_5(
  params: web::Json<Info>,
  db_pool: web::Data<Pool>,
) -> Option<impl Responder> {
  let client: Client = db_pool.get().await.map_err(MyError::PoolError)?;
  let query = "SELECT * FROM testing.users WHERE username = '" + String::from(params.hello) + "';";
  let stmt = client.prepare_typed(&query, &[]).await.unwrap();
  Some(HttpResponse::Ok().body("Hello world!"))
}