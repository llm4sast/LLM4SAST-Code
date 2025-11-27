use reqwest::header;
use reqwest::{blocking::Client, header::HeaderMap, header::HeaderValue, Url};
let mut headers = header::HeaderMap::new();
let header = header::HeaderValue::from_static("secret");
headers.insert(header::AUTHORIZATION, header);