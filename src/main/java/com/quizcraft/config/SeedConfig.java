package com.quizcraft.config;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class SeedConfig {
 @Bean CommandLineRunner seed(JdbcTemplate db){
  return args -> {
   var e=new BCryptPasswordEncoder();
   seedUser(db,"Admin User","admin@quiz.com","Admin@123","ADMIN",e);
   seedUser(db,"Quiz Creator","creator@quiz.com","Creator@123","CREATOR",e);
   seedUser(db,"Demo Participant","participant@quiz.com","Participant@123","PARTICIPANT",e);
  };
 }
 void seedUser(JdbcTemplate db,String n,String email,String pw,String role,BCryptPasswordEncoder e){
  Integer c=db.queryForObject("SELECT COUNT(*) FROM users WHERE email=?",Integer.class,email);
  if(c==0) db.update("INSERT INTO users(name,email,password_hash,role) VALUES(?,?,?,?)",n,email,e.encode(pw),role);
 }
}
