use std::collections::HashMap;
use std::net::SocketAddr;
use bytes::{Buf, Bytes};
use http_body_util::{BodyExt, Full};
use hyper::server::conn::http1;
use hyper::service::service_fn;
use hyper::{body::Incoming, header, Method, Request, Response, StatusCode};
use tokio::net::{TcpListener, TcpStream};
use reqwest::Client;
use url::form_urlencoded;
type GenericError = Box<dyn std::error::Error + Send + Sync>;
type Result<T> = std::result::Result<T, GenericError>;
type BoxBody = http_body_util::combinators::BoxBody<Bytes, hyper::Error>;
async fn api_sqli_check1(pool: Pool, req: Request<Incoming>) -> Result<Response<BoxBody>> {
    let whole_body = req.collect().await?.aggregate();
    let mut data: serde_json::Value = serde_json::from_reader(whole_body.reader())?;
    let client = reqwest::Client::new();
    let resp = client.get("http://" + String::from(data.hello))
      .send()
      .await?;
    println!("body = {:?}", resp);
    Ok("ok!")
}