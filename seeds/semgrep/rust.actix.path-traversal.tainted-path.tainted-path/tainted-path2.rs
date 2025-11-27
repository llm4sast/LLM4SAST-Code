use actix_web::{get, post, web, HttpRequest, HttpResponse, Result};
use tokio::fs::File;
use tokio::io::AsyncReadExt;
use std::fs;
use std::os::unix::fs;
use tokio::io::AsyncWriteExt;
#[post("/test2")]
async fn create_file(info: web::Path<Info>,) -> Result<HttpResponse> {
    match std::fs::File::create(info.filename) {
        Ok(_) => Ok(HttpResponse::Ok().body("File created successfully")),
        Err(_) => Ok(HttpResponse::InternalServerError().body("Failed to create the file")),
    }
}