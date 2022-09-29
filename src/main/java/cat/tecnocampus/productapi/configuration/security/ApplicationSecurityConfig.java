package cat.tecnocampus.productapi.configuration.security;

import cat.tecnocampus.productapi.configuration.security.jwt.JwtConfig;
import cat.tecnocampus.productapi.configuration.security.jwt.JwtTokenVerifierFilter;
import cat.tecnocampus.productapi.configuration.security.jwt.JwtUsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private DataSource dataSource;

    private static final String USERS_QUERY = "select username, password, 1 as enabled from client where username = ?";
    private static final String AUTHORITIES_QUERY = "select username, role from authorities where username = ?";

    private final JwtConfig jwtConfig;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, DataSource dataSource, JwtConfig jwtConfig) {
        this.passwordEncoder = passwordEncoder;
        this.dataSource = dataSource;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers( "/", "index", "/css/*", "/js/*", "/*.html", "/test","/fonts/**","/images/**","/login").permitAll()
                .antMatchers("/getIdByUsername/**","/client/**").hasAnyRole("USER","ADMIN")
                .antMatchers("/supermarket/**","/client/**").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.POST, "/**").permitAll()

                .antMatchers(HttpMethod.GET, "/getIdByUsername/**").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.GET, "/client/**").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.GET, "/client/*/orders").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
                .antMatchers(HttpMethod.GET, "/clients/**").permitAll()


                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager(), jwtConfig))
                .addFilterAfter(new JwtTokenVerifierFilter(jwtConfig), JwtUsernamePasswordAuthenticationFilter.class)
                .cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().frameOptions().disable();
    }

    //Configure authentication manager
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(USERS_QUERY)
                .authoritiesByUsernameQuery(AUTHORITIES_QUERY)
                .passwordEncoder(passwordEncoder);
    }
}
