package com.quizcraft.controller;
import com.quizcraft.model.Models.*;
import com.quizcraft.repository.*;
import com.quizcraft.service.AuthService;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api") @CrossOrigin
public class ApiController{
 private final AuthService auth; private final UserRepository users; private final QuizRepository quizzes; private final AttemptRepository attempts;
 public ApiController(AuthService a,UserRepository u,QuizRepository q,AttemptRepository at){auth=a;users=u;quizzes=q;attempts=at;}
 @PostMapping("/auth/login") public LoginResponse login(@RequestBody LoginRequest r){return auth.login(r.email(),r.password());}
 @GetMapping("/users") public List<User> users(){return users.all();}
 @GetMapping("/quizzes") public List<Quiz> quizzes(@RequestParam(required=false)String status){return quizzes.all(status);}
 @GetMapping("/quizzes/{id}") public Quiz quiz(@PathVariable long id){return quizzes.one(id);}
 @GetMapping("/quizzes/{id}/questions") public List<Question> questions(@PathVariable long id){return quizzes.questions(id,false);}
 @GetMapping("/quizzes/{id}/questions/admin") public List<Question> adminQuestions(@PathVariable long id){return quizzes.questions(id,true);}
 @PostMapping("/quizzes") public Map<String,Object> create(@RequestBody Map<String,Object> b){long cid=((Number)b.get("creatorId")).longValue();long id=quizzes.create(cid,(String)b.get("title"),(String)b.getOrDefault("description",""),((Number)b.getOrDefault("durationMinutes",15)).intValue());@SuppressWarnings("unchecked")var qs=(List<Map<String,Object>>)b.getOrDefault("questions",List.of());for(var q:qs){@SuppressWarnings("unchecked")var o=(List<String>)q.get("options");quizzes.addQuestion(id,(String)q.get("questionText"),o,((Number)q.get("correctOption")).intValue());}return Map.of("success",true,"quizId",id);}
 @PutMapping("/quizzes/{id}/status") public Map<String,Object> status(@PathVariable long id,@RequestBody Map<String,String>b){quizzes.status(id,b.get("status"));return Map.of("success",true);}
 @PostMapping("/attempts") public Result submit(@RequestBody Map<String,Object>b){@SuppressWarnings("unchecked")var a=((List<Number>)b.get("answers")).stream().map(Number::intValue).toList();return attempts.submit(((Number)b.get("userId")).longValue(),((Number)b.get("quizId")).longValue(),a);}
 @GetMapping("/attempts/user/{id}") public List<Result> results(@PathVariable long id){return attempts.byUser(id);}
 @GetMapping("/admin/stats") public Map<String,Long> stats(){return Map.of("users",users.count(),"quizzes",quizzes.count(),"pending",quizzes.pending(),"attempts",attempts.count());}
}
