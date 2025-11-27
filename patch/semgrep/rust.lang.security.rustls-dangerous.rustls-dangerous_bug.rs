use rustls::{RootCertStore, Certificate, ServerCertVerified, TLSError, ServerCertVerifier};
let verifier = MyServerCertVerifie;
let mut c3 = rustls::client::ClientConfig::new();
c3.dangerous().set_certificate_verifier(verifier);