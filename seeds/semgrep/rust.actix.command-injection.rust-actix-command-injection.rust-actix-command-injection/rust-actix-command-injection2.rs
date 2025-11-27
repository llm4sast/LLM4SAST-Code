use std::process::Command;
#[get("/hack")]
async fn hackz(
    req: HttpRequest,
) -> Result<impl Responder, Error> {
    let params = web::Query::<IndexQuery>::from_query(req.query_string()).unwrap();
    let command: String = format!("{}", &params); 
    let cmd2 = Command::new(&command);
    Ok(HttpResponse::Ok().body("Hello world!!!"))
}