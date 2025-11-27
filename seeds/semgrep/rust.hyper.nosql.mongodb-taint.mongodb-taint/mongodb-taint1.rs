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
async fn api_sqli_check1(client: Client, req: Request<Incoming>) -> Result<Response<BoxBody>> {
    let col: Collection<User> = client.database("foo").collection("bar");
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
    let doc_json = String::from("{{\"$where\": \"this.name == '");
    doc_json.push_str(name);
    doc_json.push_str("'\"}}");
    let filter_doc: bson::Document = serde_json::from_str(&doc_json).unwrap();
    let results = col.find(filter_doc, None).await.unwrap();
    do_smth(results);
    Ok("ok!")
}