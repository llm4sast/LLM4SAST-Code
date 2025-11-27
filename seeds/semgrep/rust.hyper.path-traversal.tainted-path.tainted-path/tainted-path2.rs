use hyper::{Body, Request, Response, StatusCode};
use std::fs;
use tokio::fs::File;
use tokio::io::AsyncReadExt;
async fn open_file2(req: Request<Body>) -> Result<Response<Body>, hyper::Error> {
    let params = req.uri().query();
    let filename = get_params(params);
    let mut file = match File::open(&filename).await {
        Ok(file) => file,
        Err(_) => return Ok(Response::builder()
            .status(StatusCode::NOT_FOUND)
            .body(Body::from("File not found"))
            .unwrap()
        ),
    };
    let mut content = Vec::new();
    if let Err(_) = file.read_to_end(&mut content).await {
        return Ok(Response::builder()
            .status(StatusCode::INTERNAL_SERVER_ERROR)
            .body(Body::from("Failed to read the file"))
            .unwrap()
        );
    }
    Ok(Response::new(Body::from(content)))
}