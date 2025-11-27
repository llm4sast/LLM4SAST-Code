use actix_web::{get, post, web, App, HttpResponse, HttpServer, Responder, HttpRequest, http::StatusCode};
use actix_web::{get, App, HttpResponse, HttpServer, Responder, web::{Bytes, Json}, http::StatusCode, Error, CustomizeResponder, Either};
use deadpool_postgres::{Client, Pool};
#[post("/test1")]
async fn test_1(
  info: web::Path<Info>,
  db_pool: web::Data<Pool>,
) -> Result<String> {
  let params = web::Query::<IndexQuery>::from_query(req.query_string()).unwrap();
  let client: Client = db_pool.get().await.map_err(MyError::PoolError)?;
  let info = sanitize(&info);
  let query = format!("SELECT * FROM testing.users WHERE username = '{}';", info.username);
  let stmt = client.prepare(&query).await.unwrap();
  Ok("Welcome!")
}