use reqwest::Client;



async fn test2() -> Result<(), reqwest::Error> {
  let client = reqwest::Client::new();
  let url = "https://username:passwd123@www.example.org";

  let res = client.post(url)
      .body("the exact body that is sent")
      .send()
      .await?;

  Ok(())
}
