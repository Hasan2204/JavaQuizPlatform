package com.quizcraft.repository;
import com.quizcraft.model.Models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository public class UserRepository{
 private final JdbcTemplate db; public UserRepository(JdbcTemplate db){this.db=db;}
 public Map<String,Object> auth(String email){var x=db.queryForList("SELECT id,name,email,role,password_hash FROM users WHERE email=? AND active=1",email);return x.isEmpty()?null:x.get(0);}
 public List<User> all(){return db.query("SELECT id,name,email,role FROM users ORDER BY id DESC",(r,n)->new User(r.getLong(1),r.getString(2),r.getString(3),r.getString(4)));}
 public long count(){return db.queryForObject("SELECT COUNT(*) FROM users",Long.class);}
}
