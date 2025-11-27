use mongodb::{Client, options::ClientOptions};
use mongodb::options::ConnectionString;


fn test5() -> Result<(), mongodb::error::Error> {
    let url = "mongodb://user:pwd12345@localhost:27017";

    let client = mongodb::sync::Client::with_uri_str(url)?;
    foobar(client);
    Ok(())
}
