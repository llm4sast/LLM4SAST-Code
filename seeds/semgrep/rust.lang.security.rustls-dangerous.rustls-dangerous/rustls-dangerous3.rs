use rustls::{RootCertStore, Certificate, ServerCertVerified, TLSError, ServerCertVerifier};
let verifier = MyServerCertVerifie;
let mut c4 = rustls::client::ClientConfig::dangerous(&mut ());
c4.set_certificate_verifier(verifier);