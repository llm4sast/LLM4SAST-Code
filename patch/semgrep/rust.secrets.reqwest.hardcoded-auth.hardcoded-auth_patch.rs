use reqwest::Client;
async fn test1(pass: &str) -> Result<(), reqwest::Error> {
  let client = reqwest::Client::new();
  let resp = client.delete("http://httpbin.org/delete")
    .basic_auth("admin", Some(pass))
    .send()
    .await?;
  println!("body = {:?}", resp);
  Ok(())
}
