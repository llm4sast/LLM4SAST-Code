use actix_web::{get, post, web, HttpRequest, HttpResponse, Result};
use tokio::fs::File;
use tokio::io::AsyncReadExt;
use std::fs;
use std::os::unix::fs;
use tokio::io::AsyncWriteExt;
#[get("/test3")]
async fn read_file_bytes(params: web::Query<IndexQuery>) -> Result<HttpResponse> {
    match std::fs::read(params.filename).await {
        Ok(content) => Ok(HttpResponse::Ok().body(content)),
        Err(_) => Ok(HttpResponse::NotFound().body("File not found")),
    }
}