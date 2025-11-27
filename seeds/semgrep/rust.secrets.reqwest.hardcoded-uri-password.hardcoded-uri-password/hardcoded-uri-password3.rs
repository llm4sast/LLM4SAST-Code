use reqwest::Client;



async fn test3() -> Result<(), reqwest::Error> {
  let client: Client = Client::new();

  let res = client.put("https://username:passwd123@www.example.org/smth")
      .body("the exact body that is sent")
      .send()
      .await?;

  Ok(())
}
