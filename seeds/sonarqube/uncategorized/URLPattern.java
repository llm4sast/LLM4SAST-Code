import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public class URLPattern {
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/resources/**", "/signup", "/about")
        .permitAll()
        .antMatchers("/admin/**")
        .hasRole("ADMIN")
        .antMatchers("/admin/login")
        .permitAll() // Noncompliant
        .antMatchers("/**", "/home")
        .permitAll()
        .antMatchers("/db/**")
        .access("hasRole('ADMIN') and hasRole('DBA')") // Noncompliant
        .and()
        .formLogin()
        .loginPage("/login")
        .permitAll()
        .and()
        .logout()
        .permitAll();
  }
}
