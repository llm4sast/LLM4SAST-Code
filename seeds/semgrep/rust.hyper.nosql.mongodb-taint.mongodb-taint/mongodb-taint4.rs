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
async fn api_sqli_check4(col: Collection<User>, req: Request<Incoming>) -> Result<Response<BoxBody>> {
    let whole_body = req.collect().await?.aggregate();
    let mut data: serde_json::Value = serde_json::from_reader(whole_body.reader())?;
    let doc_json = String::from("{{\"$where\": \"this.name == '");
    doc_json.push_str(data["name"]);
    doc_json.push_str("'\"}}");
    let filter_doc: bson::Document = serde_json::from_str(&doc_json).unwrap();
    let results = col.find(filter_doc, None).await.unwrap();
    do_smth(results);
    Ok("ok!")
}