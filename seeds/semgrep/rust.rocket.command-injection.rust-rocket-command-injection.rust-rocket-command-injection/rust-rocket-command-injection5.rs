use std::process::Command;
#[macro_use]
extern crate rocket;
#[get("/")]
fn index() -> &'static str {
    "Hello, world!"
}
#[get("/user/<id>")]
fn get_user(id: String) -> String {
    let output = Command::new("cat") 
        .arg(&id)
        .output()
        .expect(format!("Failed to execute `cat {id}`").as_str());
    println!("{:?}", output);
    let stdout: String = String::from_utf8(output.stdout).unwrap();
    let stderr: String = String::from_utf8(output.stderr).unwrap();
    format!(
        "stdout: `{}`\n\nstderr: `{}`",
        &stdout.trim(),
        stderr.trim()
    )
}
#[get("/item/<id>")]
fn get_item(id: String) -> String {
    let cmd6 = Command::new("sh")
        .arg("-v")
        .args(["-c", arg, &id]);
}
#[launch]
fn rocket() -> _ {
    rocket::build()
        .mount("/hello", routes![index])
        .mount("/", routes![get_user])
        .mount("/", routes![get_item])
}