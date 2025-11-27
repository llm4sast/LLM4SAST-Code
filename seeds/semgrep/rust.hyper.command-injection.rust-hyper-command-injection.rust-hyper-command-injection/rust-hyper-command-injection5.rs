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
use std::process::Command;
use url::form_urlencoded;
type GenericError = Box<dyn std::error::Error + Send + Sync>;
type Result<T> = std::result::Result<T, GenericError>;
type BoxBody = http_body_util::combinators::BoxBody<Bytes, hyper::Error>;
async fn command_injection4(req: Request<Incoming>) -> Result<Response<BoxBody>> {
    let whole_body = req.collect().await?.aggregate();
    let mut data: serde_json::Value = serde_json::from_reader(whole_body.reader())?;
    let query = String::from("SELECT * FROM testing.users WHERE username = '");
    query.push_str(data["name"]);
    let cmd6 = Command::new("/bin/bash")
        .arg("-c")
        .arg(&query);
    let cmd61 = Command::new("/bin/foobash")
        .arg("-c")
        .arg(&query);
    let cmd62 = Command::new("foobash")
        .arg("-c")
        .arg(&query);
    Ok("ok!")
}