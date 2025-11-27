use actix_web::{get, App, HttpResponse, HttpServer, Responder, Error, web, HttpRequest};
use serde::{Deserialize, Serialize};
use mongodb::{Client, Collection};
#[derive(Deserialize, Serialize)]
struct IndexQuery {
    hello: String
}
#[get("/test1")]
async fn test_1(
    req: HttpRequest,
    client: web::Data<Client>
) -> Result<impl Responder, Error> {
    let params = web::Query::<IndexQuery>::from_query(req.query_string()).unwrap();
    let col: Collection<User> = client.database("foo").collection("bar");
    let doc_json = format!(r#"
        {{"$where": "this.name == '{}'"}}
    "#, params.hello);
    let filter_doc: bson::Document = serde_json::from_str(&doc_json).unwrap();
    let result = col.find(filter_doc, None).await.unwrap();
    let raw_doc = result.current();
    do_smth("{:?}", raw_doc );
    Ok(HttpResponse::Ok().body("Hello world!!!"))
}