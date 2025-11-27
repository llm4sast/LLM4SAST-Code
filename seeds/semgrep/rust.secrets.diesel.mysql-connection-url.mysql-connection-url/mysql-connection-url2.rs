use diesel::mysql::MysqlConnection;
use diesel::prelude::*;
fn test2() -> MysqlConnection {
    MysqlConnection::establish("mysql://username:password@localhost/database";
        .unwrap_or_else(|_| panic!("Error connecting to {}", database_url))
}