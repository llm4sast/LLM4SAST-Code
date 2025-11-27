#![feature(proc_macro_hygiene, decl_macro)]
#[macro_use] extern crate rocket;
use rocket::response::content::Html;
use rocket::response::NamedFile;
use rocket_contrib::json::Json;
use std::path::PathBuf;
use std::path::Path;
use tokio::fs::File;
use rocket::http::{CookieJar, Cookie};
use rocket::request::{self, Request, FromRequest};
#[get("/")]
fn index() -> &'static str {
    "Hello, world!"
}
#[rocket::async_trait]
impl<'r> FromRequest<'r> for MyDataType {
    type Error = ();
    async fn from_request(request: &'p Request<'_>) -> request::Outcome<Self, ()> {
        let c = request.cookies().get("name").unwrap().value();
        let f2 = NamedFile::open(Path::new("static/").join(c)).ok();
    }
}
fn main() {
    rocket::ignite()
        .mount("/",
              routes!
              [
                index,
                xss,
                xss2,
                path,
                path2
              ]
        )
        .launch();
}