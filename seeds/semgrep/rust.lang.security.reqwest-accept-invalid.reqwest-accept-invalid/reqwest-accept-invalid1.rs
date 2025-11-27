use reqwest::header;
let client = reqwest::Client::builder()
    .danger_accept_invalid_hostnames(true)
    .build();