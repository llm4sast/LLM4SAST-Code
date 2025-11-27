use mongodb::{Client, options::ClientOptions};
use mongodb::options::ConnectionString;




fn test3() -> Result<(), mongodb::error::Error> {
    let url = "mongodb://user@localhost:27017";

    let connection_string = ConnectionString::parse(url)?;
    foobar(connection_string);
    Ok(())
}
