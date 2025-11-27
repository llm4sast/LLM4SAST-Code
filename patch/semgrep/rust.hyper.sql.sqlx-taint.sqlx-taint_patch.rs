use std::collections::HashMap;
use std::net::SocketAddr;
use ::config::Config;
use dotenv::dotenv;
use bytes::{Buf, Bytes};
use http_body_util::{BodyExt, Full};
use hyper::server::conn::http1;
use hyper::service::service_fn;
use hyper::{body::Incoming, header, Method, Request, Response, StatusCode};
use sqlx::{postgres::PgPoolOptions, Pool, Postgres, Row, QueryBuilder};
use url::form_urlencoded;
type GenericError = Box<dyn std::error::Error + Send + Sync>;
type Result<T> = std::result::Result<T, GenericError>;
type BoxBody = http_body_util::combinators::BoxBody<Bytes, hyper::Error>;
async fn api_sqli_check3(pool: Pool<PgPoolOptions>, req: Request<Incoming>) -> Result<Response<BoxBody>> {
    let whole_body = req.collect().await?.aggregate();
    let mut data: serde_json::Value = serde_json::from_reader(whole_body.reader())?;
    let data = sanitize(&data);
    let query = "SELECT * FROM testing.users WHERE username = '" + String::from(data["name"]) + "';";
    sqlx::query(query)
      .fetch_one(&mut *db)
      .map_ok(|r| Json(Post { id: Some(r.id), title: r.title, text: r.text }))
      .await
      .ok()
}