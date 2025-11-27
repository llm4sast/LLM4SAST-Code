use diesel::pg::PgConnection;
use diesel::prelude::*;
fn test1() -> PgConnection {
    let database_url = "postgres://localhost/database";
    PgConnection::establish(&database_url)
        .unwrap_or_else(|_| panic!("Error connecting to {}", database_url))
}