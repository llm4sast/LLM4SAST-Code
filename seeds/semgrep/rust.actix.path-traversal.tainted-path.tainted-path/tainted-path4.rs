use actix_web::{get, post, web, HttpRequest, HttpResponse, Result};
use tokio::fs::File;
use tokio::io::AsyncReadExt;
use std::fs;
use std::os::unix::fs;
use tokio::io::AsyncWriteExt;
#[get("/test4")]
async fn create_symlink(req: HttpRequest) -> Result<HttpResponse> {
    let params = web::Query::<IndexQuery>::from_query(req.query_string()).unwrap();
    let link_path = "symbolic_link.txt";
    match fs::symlink(params.target_path, link_path) {
        Ok(_) => Ok(HttpResponse::Ok().body("Symbolic link created successfully")),
        Err(_) => Ok(HttpResponse::InternalServerError().body("Failed to create symbolic link")),
    }
}