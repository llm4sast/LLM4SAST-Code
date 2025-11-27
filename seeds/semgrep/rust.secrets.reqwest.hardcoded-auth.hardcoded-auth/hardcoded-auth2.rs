use reqwest::Client;


async fn test2() -> Result<(), reqwest::Error> {
  let client = reqwest::Client::new();

  let resp = client.put("http://httpbin.org/delete")
    .bearer_auth("hardcoded-token")
    .send()
    .await?;

  println!("body = {:?}", resp);

  Ok(())
}
