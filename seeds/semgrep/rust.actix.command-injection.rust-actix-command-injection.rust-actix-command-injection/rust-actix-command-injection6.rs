use std::process::Command;
#[get("/hack")]
async fn hackz(
    req: HttpRequest,
) -> Result<impl Responder, Error> {
    let params = web::Query::<IndexQuery>::from_query(req.query_string()).unwrap();
    let cmd6 = Command::new("sh")
        .arg("-c")
        .arg(&params);
    Ok(HttpResponse::Ok().body("Hello world!!!"))
}