package Duan2.Traicagiong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import Duan2.Traicagiong.service.UserService;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	 http
         .authorizeRequests().antMatchers("/Admin/**").hasRole("ADMIN");
    	 http
         .authorizeRequests()
         .antMatchers( "/DangKy","/registration","/", "/DangNhap", "/logout","/DanhsachCagiong/**","/DanhsachCagiong","/TinTuc/**","/TinTuc"
        		 ,"/TrangChu","/GioiThieu","/LienHe","/ChiTietCaGiong/**","/QuenMatKhau",
                  "/static/**",
                  "/vendors/**","/build/**",
                  "/uploads/**","/Home/**"
         		).permitAll()
         .anyRequest().authenticated()
        
    	 .and()
         .formLogin()
         .loginPage("/DangNhap")
         .usernameParameter("email")
         .passwordParameter("password")
         .defaultSuccessUrl("/DanhsachCagiong")
         .failureUrl("/DangNhap?error")
         .permitAll()
         .and()
	         .logout()
	         .invalidateHttpSession(true)
	         .clearAuthentication(true)
	         .logoutRequestMatcher(new AntPathRequestMatcher("/DangXuat"))
	         .logoutSuccessUrl("/DangNhap?logout")
         .permitAll();
         
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
}