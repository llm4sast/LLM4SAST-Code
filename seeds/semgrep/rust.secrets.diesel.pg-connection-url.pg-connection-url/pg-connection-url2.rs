use diesel::pg::PgConnection;
use diesel::prelude::*;
fn test2() -> PgConnection {
    PgConnection::establish("postgresql://username:password@localhost/database")
        .unwrap_or_else(|_| panic!("Error connecting to {}", database_url))
}