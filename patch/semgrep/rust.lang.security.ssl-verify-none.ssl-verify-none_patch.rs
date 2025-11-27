use openssl::ssl::{SslMethod, SslConnectorBuilder, SSL_VERIFY_NONE};
let mut connector = SslConnectorBuilder::new(SslMethod::tls()).unwrap();
connector.builder_mut().set_verify(SSL_VERIFY_PEER);
let openssl = OpenSsl::from(connector.build());