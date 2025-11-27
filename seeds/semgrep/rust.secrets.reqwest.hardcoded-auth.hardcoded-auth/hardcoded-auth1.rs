use reqwest::Client;

async fn test1() -> Result<(), reqwest::Error> {
  let client = reqwest::Client::new();

  let resp = client.delete("http://httpbin.org/delete")
    .basic_auth("admin", Some("hardcoded-password"))
    .send()
    .await?;

  println!("body = {:?}", resp);

  Ok(())
}
