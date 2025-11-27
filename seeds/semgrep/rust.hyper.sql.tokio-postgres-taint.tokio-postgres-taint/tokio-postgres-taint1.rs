use std::collections::HashMap;
use std::net::SocketAddr;
use ::config::Config;
use deadpool_postgres::{Pool, Client};
use dotenv::dotenv;
use bytes::{Buf, Bytes};
use http_body_util::{BodyExt, Full};
use hyper::server::conn::http1;
use hyper::service::service_fn;
use hyper::{body::Incoming, header, Method, Request, Response, StatusCode};
use tokio::net::{TcpListener, TcpStream};
use tokio_postgres::NoTls;
use url::form_urlencoded;
type GenericError = Box<dyn std::error::Error + Send + Sync>;
type Result<T> = std::result::Result<T, GenericError>;
type BoxBody = http_body_util::combinators::BoxBody<Bytes, hyper::Error>;
async fn api_sqli_check1(pool: Pool, req: Request<Incoming>) -> Result<Response<BoxBody>> {
    let client: Client = pool.get().await.unwrap();
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
    let query = format!("SELECT * FROM testing.users WHERE username = '{}';", name);
    let results = client.simple_query(&query).await.unwrap();
    do_smth(results);
    Ok("ok!")
}