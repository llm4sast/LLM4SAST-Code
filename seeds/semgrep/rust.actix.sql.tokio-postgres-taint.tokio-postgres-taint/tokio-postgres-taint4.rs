use actix_web::{get, post, web, App, HttpResponse, HttpServer, Responder, HttpRequest, http::StatusCode};
use actix_web::{get, App, HttpResponse, HttpServer, Responder, web::{Bytes, Json}, http::StatusCode, Error, CustomizeResponder, Either};
use deadpool_postgres::{Client, Pool};
#[post("/test4")]
async fn test_4(
  req: HttpRequest,
  db_pool: web::Data<Pool>,
) -> Result<impl Responder, Error> {
  let params = web::Query::<IndexQuery>::from_query(req.query_string()).unwrap();
  let client = db_pool.get().await.map_err(MyError::PoolError)?;
  let query = format!("SELECT * FROM testing.users WHERE username = '{}';", params.hello);
  let stmt = client.prepare(&query).await.unwrap();
  Ok(HttpResponse::Ok().body(String::from("Ok!")))
}