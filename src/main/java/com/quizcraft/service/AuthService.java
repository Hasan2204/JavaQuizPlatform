package com.quizcraft.service;
import com.quizcraft.model.Models.*;
import com.quizcraft.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;
@Service public class AuthService{
 private final UserRepository users; private final BCryptPasswordEncoder enc=new BCryptPasswordEncoder();
 public AuthService(UserRepository users){this.users=users;}
 public LoginResponse login(String email,String password){Map<String,Object> x=users.auth(email);if(x==null||!enc.matches(password,(String)x.get("password_hash")))return new LoginResponse(false,"Invalid email or password",null);return new LoginResponse(true,"Login successful",new User(((Number)x.get("id")).longValue(),(String)x.get("name"),(String)x.get("email"),(String)x.get("role")));}
}
