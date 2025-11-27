use actix_web::{get, post, web, HttpRequest, HttpResponse, Result};
use tokio::fs::File;
use tokio::io::AsyncReadExt;
use std::fs;
use std::os::unix::fs;
use tokio::io::AsyncWriteExt;
#[get("/test5")]
async fn create_and_write_file(req: HttpRequest) -> Result<HttpResponse> {
    let params = web::Query::<IndexQuery>::from_query(req.query_string()).unwrap();
    let filename = params.filename;
    let content = "Hello, world!";
    let mut file = match File::create(filename).await {
        Ok(file) => file,
        Err(_) => return Ok(HttpResponse::InternalServerError().body("Failed to create the file")),
    };
    if let Err(_) = file.write_all(content.as_bytes()).await {
        return Ok(HttpResponse::InternalServerError().body("Failed to write to the file"));
    }
    Ok(HttpResponse::Ok().body("File created and content written successfully"))
}