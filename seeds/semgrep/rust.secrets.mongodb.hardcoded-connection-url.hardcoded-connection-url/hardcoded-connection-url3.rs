use mongodb::{Client, options::ClientOptions};
use mongodb::options::ConnectionString;


fn test4() -> Result<(), mongodb::error::Error> {
    let url = "mongodb://user:pwd12345@localhost:27017";

    let connection_string = ConnectionString::from_str(url)?;
    foobar(connection_string);
    Ok(())
}
