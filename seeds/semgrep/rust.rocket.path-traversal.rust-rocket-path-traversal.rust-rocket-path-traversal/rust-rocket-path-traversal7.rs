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
#[get("/path?<file>")]
fn path(file: String) -> Option<NamedFile> {
    let path = "static/".to_owned() + &file;
    let f8 = tokio::fs::read_dir(&path);
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