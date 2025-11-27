use std::collections::HashMap;
use std::net::SocketAddr;
use ::config::Config;
use dotenv::dotenv;
use bytes::{Buf, Bytes};
use http_body_util::{BodyExt, Full};
use hyper::server::conn::http1;
use hyper::service::service_fn;
use hyper::{body::Incoming, header, Method, Request, Response, StatusCode};
use mongodb::{Client, Collection};
use url::form_urlencoded;
type GenericError = Box<dyn std::error::Error + Send + Sync>;
type Result<T> = std::result::Result<T, GenericError>;
type BoxBody = http_body_util::combinators::BoxBody<Bytes, hyper::Error>;
async fn api_sqli_check3(col: Collection<User>, req: Request<Incoming>) -> Result<Response<BoxBody>> {
    let whole_body = req.collect().await?.aggregate();
    let mut data: serde_json::Value = serde_json::from_reader(whole_body.reader())?;
    let query = "SELECT * FROM testing.users WHERE username = '" + String::from(data["name"]) + "';";
    let filter_doc: bson::Document = serde_json::from_str(&query).unwrap();
    let result = col.find_one(filter_doc, None).await.unwrap();
    do_smth(result);
    Ok("ok!")
}