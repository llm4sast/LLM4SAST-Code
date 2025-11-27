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
async fn api_sqli_check1(pool: Pool<PgPoolOptions>, req: Request<Incoming>) -> Result<Response<BoxBody>> {
    let urlQuery = if let Some(q) = req.uri().query() {
        q
    } else {
        ""
    };
    let params = form_urlencoded::parse(urlQuery.as_bytes())
        .into_owned()
        .collect::<HashMap<String,String>>();
    let name = if let Some(p) = params.get("name") {
        p
    } else {
        "foobar"
    };
    let query = String::from("SELECT * FROM testing.users WHERE username = '");
    query.push_str(name);
    query.push_str("';");
    QueryBuilder::new(query)
        .build()
        .fetch_one(&pool)
        .await
        .ok();
    Ok("ok!")
}