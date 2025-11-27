use mongodb::{Client, options::ClientOptions};
use mongodb::options::ConnectionString;

fn test1() -> Result<(), mongodb::error::Error> {
    let url = "mongodb://user:pwd12345@localhost:27017";

    let options = mongodb::options::ClientOptions::parse(url)?;
    foobar(options);
    Ok(())
}
