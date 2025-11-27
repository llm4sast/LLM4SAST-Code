use rustls::{RootCertStore, Certificate, ServerCertVerified, TLSError, ServerCertVerifier};
let verifier = MyServerCertVerifie;
let mut c2 = rustls::client::DangerousClientConfig {cfg: &mut cfg};
c2.set_certificate_verifier(verifier);