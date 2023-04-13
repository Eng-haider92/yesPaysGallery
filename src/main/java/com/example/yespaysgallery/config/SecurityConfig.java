package com.example.yespaysgallery.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	  
	private String[] resources = new String[]{
        "/","/include/**","/css/**",
        "/icons/**","/img/**","/js/**","/layer/**", "/images/**"
    };


	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private DataSource dataSource;
	
	
	
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		    return http .authorizeHttpRequests((authz) ->
				{
					try {
						authz
									.antMatchers("/").permitAll()
									.antMatchers(resources).permitAll()
									.antMatchers("/login").permitAll()
									.antMatchers("/signup").permitAll()
									.antMatchers("/imageViewer", "/imageViewer/**", "/imageViewer/**/").permitAll()
									.antMatchers("/gallery", "/gallery/**").permitAll()
									.antMatchers("/category", "/category/**").hasAnyAuthority("ADMIN","USER")
									.antMatchers("/orders", "/newOrder","/newOrder/**").hasAnyAuthority("ADMIN")
									.antMatchers("/myGallery").hasAnyAuthority("USER")
									.anyRequest().authenticated()									
									.and()
									.formLogin()
									.loginPage("/login")
									.failureUrl("/login?error=true").defaultSuccessUrl("/home")
									.usernameParameter("username")
									.passwordParameter("password")
									.and().logout()
									.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
									.logoutSuccessUrl("/").and().exceptionHandling()
									.accessDeniedPage("/access-denied");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			  )

			  .csrf().disable()
			  .build();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.
		jdbcAuthentication()
			.usersByUsernameQuery("select username, password,active from users where username=?")
			.authoritiesByUsernameQuery("select u.username, r.role from users u inner join user_role ur on(u.user_id=ur.user_id) inner join role r on(ur.role_id=r.role_id) where u.username=?")
			.dataSource(dataSource)
			.passwordEncoder(bCryptPasswordEncoder);    
	}



}
