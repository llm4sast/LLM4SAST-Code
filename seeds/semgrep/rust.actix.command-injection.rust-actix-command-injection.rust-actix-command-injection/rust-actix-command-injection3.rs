use std::process::Command;
#[get("/hack")]
async fn hackz(
    req: HttpRequest,
) -> Result<impl Responder, Error> {
    let params = web::Query::<IndexQuery>::from_query(req.query_string()).unwrap();
    let mut cmd3 = Command::new("sh");
    cmd3.arg("-c")
        .arg(&params); 
    println!("{:?}", cmd3);
    Ok(HttpResponse::Ok().body("Hello world!!!"))
}