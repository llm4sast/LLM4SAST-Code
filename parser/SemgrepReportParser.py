from parser.Report import Report, Violation


semgrep_bug_types = [
    "rust.rocket.path-traversal.rust-rocket-path-traversal.rust-rocket-path-traversal",
    "rust.actix.path-traversal.tainted-path.tainted-path",
    "rust.lang.security.args.args",
    "rust.lang.security.reqwest-accept-invalid.reqwest-accept-invalid",
    "rust.hyper.nosql.mongodb-taint.mongodb-taint",
    "rust.actix.sql.tokio-postgres-taint.tokio-postgres-taint",
    "rust.hyper.ssrf.reqwest-taint.reqwest-taint",
    "rust.secrets.tokio-postgres.hardcoded-connection-empty-password.hardcoded-connection-empty-password",
    "rust.secrets.diesel.mysql-connection-empty-password.mysql-connection-empty-password",
    "rust.actix.ssrf.reqwest-taint.reqwest-taint",
    "rust.lang.security.ssl-verify-none.ssl-verify-none",
    "rust.secrets.tokio-postgres.hardcoded-password.hardcoded-password",
    "rust.secrets.mongodb.hardcoded-connection-url.hardcoded-connection-url",
    "rust.secrets.diesel.pg-connection-empty-password.pg-connection-empty-password",
    "rust.lang.security.unsafe-usage.unsafe-usage",
    "rust.hyper.sql.diesel-taint.diesel-taint",
    "rust.hyper.sql.sqlx-taint.sqlx-taint",
    "rust.secrets.postgres.hardcoded-connection-empty-password.hardcoded-connection-empty-password",
    "rust.actix.sql.postgres-taint.postgres-taint",
    "rust.secrets.postgres.hardcoded-password.hardcoded-password",
    "rust.hyper.path-traversal.tainted-path.tainted-path",
    "rust.actix.sql.sqlx-taint.sqlx-taint",
    "rust.secrets.mongodb.hardcoded-connection-empty-password.hardcoded-connection-empty-password",
    "rust.rocket.sql.postgres-taint.postgres-taint",
    "rust.hyper.sql.postgres-taint.postgres-taint",
    "rust.lang.security.args-os.args-os",
    "rust.secrets.sqlx.pg-connection-url.pg-connection-url",
    "rust.secrets.sqlx.pg-connection-empty-password.pg-connection-empty-password",
    "rust.actix.command-injection.rust-actix-command-injection.rust-actix-command-injection",
    "rust.rocket.nosql.mongodb-taint.mongodb-taint",
    "rust.secrets.sqlx.mysql-connection-empty-password.mysql-connection-empty-password",
    "rust.secrets.postgres.hardcoded-connection-url.hardcoded-connection-url",
    "rust.rocket.sql.tokio-postgres-taint.tokio-postgres-taint",
    "rust.secrets.reqwest.hardcoded-uri-password.hardcoded-uri-password",
    "rust.secrets.diesel.mysql-connection-url.mysql-connection-url",
    "rust.rocket.ssrf.reqwest-taint.reqwest-taint",
    "rust.secrets.postgres.empty-password.empty-password",
    "rust.lang.security.insecure-hashes.insecure-hashes",
    "rust.lang.security.current-exe.current-exe",
    "rust.rocket.sql.diesel-taint.diesel-taint",
    "rust.lang.security.rustls-dangerous.rustls-dangerous",
    "rust.secrets.sqlx.empty-password.empty-password",
    "rust.secrets.sqlx.mysql-connection-url.mysql-connection-url",
    "rust.actix.sql.diesel-taint.diesel-taint",
    "rust.secrets.sqlx.hardcoded-password.hardcoded-password",
    "rust.lang.security.temp-dir.temp-dir",
    "rust.secrets.tokio-postgres.empty-password.empty-password",
    "rust.rocket.sql.sqlx-taint.sqlx-taint",
    "rust.secrets.tokio-postgres.hardcoded-connection-url.hardcoded-connection-url",
    "rust.lang.security.reqwest-set-sensitive.reqwest-set-sensitive",
    "rust.actix.nosql.mongodb-taint.mongodb-taint",
    "rust.secrets.diesel.pg-connection-url.pg-connection-url",
    "rust.hyper.sql.tokio-postgres-taint.tokio-postgres-taint",
    "rust.rocket.command-injection.rust-rocket-command-injection.rust-rocket-command-injection",
    "rust.hyper.command-injection.rust-hyper-command-injection.rust-hyper-command-injection",
    "rust.secrets.reqwest.hardcoded-auth.hardcoded-auth",
]

class SemgrepReportParser():    
    def __init__(self, report):
        self.report = report
        
    def parse_tool(self):
        return "Semgrep"
    
    def parse_checker(self):
        return

    def parse_description(self):
        return

    def parse_location(self):
        return

    def parse_code(self):
        return

    def parse_single_report(self, result:Report):

        data = self.report

        for item in data["results"]:
            bug_type = item['check_id']
            violation = Violation(
                vul_type=bug_type, 
                start_line=item['start']['line'], 
                # start_column1=item['start']['col'], 
                # start_column2=item['start']['col'], 
                end_line=item['end']['line'], 
                # end_column1=item['end']['col'], 
                # end_column2=item['end']['col'],
                # description=item['extra']['message'],
                # diagnosis=None
            )
            result.appendViolation(violation)
        
        return result
    
    def gen_prompt(self):
        return


if __name__ == '__main__':
    pass


