package com.myblogrestapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.myblogrestapi.security.CustomUserDetailsService;



@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled=true) this is deprecated by springboot
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	

    @Autowired
    private CustomUserDetailsService userDetailsService;// loadUserByUserName()
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception { 
        return authenticationConfiguration.getAuthenticationManager();
    }
	
	   @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	           // for remember formula :- hcd4ah
		   http
		        
		        .csrf().disable()
		        .authorizeRequests()
		        .requestMatchers(HttpMethod.GET,"/api/**").permitAll() //antMatchers
		        .requestMatchers("/api/auth/**").permitAll()
		        .anyRequest()
		        .authenticated()
		        .and()
		        .httpBasic();
		   
		   return http.build(); //
	    }
	   
	  
	   protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		   auth.userDetailsService(userDetailsService)
		        .passwordEncoder(passwordEncoder());
	   }
	   


//	   @Bean
//	   protected UserDetailsService userDetailsService() {
//		   
//	       UserDetails pankaj = User.builder().username("pankaj").password(passwordEncoder().encode("password")).roles("USER").build();
//	       UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build();
//	       
//	       return new InMemoryUserDetailsManager(pankaj, admin);
//	   }
}
