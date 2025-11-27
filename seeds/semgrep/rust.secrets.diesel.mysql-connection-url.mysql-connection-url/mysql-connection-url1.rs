use diesel::mysql::MysqlConnection;
use diesel::prelude::*;
fn test1() -> MysqlConnection {
    let database_url = "mysql://username:password@localhost/database";
    MysqlConnection::establish(&database_url)
        .unwrap_or_else(|_| panic!("Error connecting to {}", database_url))
}