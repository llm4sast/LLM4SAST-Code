use reqwest::Client;

async fn test1() -> Result<(), reqwest::Error> {

  let body = reqwest::get("https://username:passwd123@www.example.org")
    .await?
    .text()
    .await?;

  println!("body = {:?}", body);

  Ok(())
}
