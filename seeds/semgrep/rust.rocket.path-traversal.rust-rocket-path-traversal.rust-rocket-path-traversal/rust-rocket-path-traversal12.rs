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
#[get("/message")]
fn message<'r>(jar: &'r CookieJar<'_>) -> Option<&'r str> {
    let cookie = jar.get("cookie-name");
    let value = cookie.unwrap().value();
    let path = "static/".to_owned() + &value;
    let f1 = NamedFile::open(Path::new(&path)).ok();
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