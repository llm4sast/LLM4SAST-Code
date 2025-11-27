use diesel::pg::PgConnection;
use diesel::prelude::*;
fn test2() -> PgConnection {
    PgConnection::establish("postgres://username@localhost/database";
        .unwrap_or_else(|_| panic!("Error connecting to {}", database_url))
}