use hyper::{Body, Request, Response, StatusCode};
use std::fs;
use tokio::fs::File;
use tokio::io::AsyncReadExt;
async fn read_file(req: Request<Body>) -> Result<Response<Body>, hyper::Error> {
    let filename = req.uri().path();
    match fs::read_to_string(filename) {
        Ok(content) => Ok(Response::new(Body::from(content))),
        Err(_) => Ok(Response::builder()
            .status(StatusCode::NOT_FOUND)
            .body(Body::from("File not found"))
            .unwrap()
        ),
    }
}