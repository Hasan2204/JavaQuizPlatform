package com.quizcraft.repository;
import com.quizcraft.model.Models.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository public class QuizRepository{
 private final JdbcTemplate db; public QuizRepository(JdbcTemplate db){this.db=db;}
 public List<Quiz> all(String status){String s="SELECT q.id,q.title,q.description,q.duration_minutes,q.status,q.creator_id,u.name,(SELECT COUNT(*) FROM questions z WHERE z.quiz_id=q.id) FROM quizzes q JOIN users u ON u.id=q.creator_id WHERE (? IS NULL OR q.status=?) ORDER BY q.id DESC";return db.query(s,(r,n)->new Quiz(r.getLong(1),r.getString(2),r.getString(3),r.getInt(4),r.getString(5),r.getLong(6),r.getString(7),r.getInt(8)),status,status);}
 public Quiz one(long id){var x=db.query("SELECT q.id,q.title,q.description,q.duration_minutes,q.status,q.creator_id,u.name,(SELECT COUNT(*) FROM questions z WHERE z.quiz_id=q.id) FROM quizzes q JOIN users u ON u.id=q.creator_id WHERE q.id=?",(r,n)->new Quiz(r.getLong(1),r.getString(2),r.getString(3),r.getInt(4),r.getString(5),r.getLong(6),r.getString(7),r.getInt(8)),id);return x.isEmpty()?null:x.get(0);}
 public List<Question> questions(long id,boolean answers){var qs=db.query("SELECT id,quiz_id,question_text,correct_option FROM questions WHERE quiz_id=? ORDER BY id",(r,n)->new Question(r.getLong(1),r.getLong(2),r.getString(3),List.of(),answers?r.getInt(4):-1),id);return qs.stream().map(q->{var o=db.query("SELECT option_text FROM question_options WHERE question_id=? ORDER BY option_order",(r,n)->r.getString(1),q.id());return new Question(q.id(),q.quizId(),q.questionText(),o,q.correctOption());}).toList();}
 public long create(long creator,String title,String desc,int duration){db.update("INSERT INTO quizzes(title,description,duration_minutes,status,creator_id) VALUES(?,?,?,'PENDING',?)",title,desc,duration,creator);return db.queryForObject("SELECT LAST_INSERT_ID()",Long.class);}
 public void addQuestion(long quiz,String text,List<String> options,int correct){db.update("INSERT INTO questions(quiz_id,question_text,correct_option) VALUES(?,?,?)",quiz,text,correct);long q=db.queryForObject("SELECT LAST_INSERT_ID()",Long.class);for(int i=0;i<options.size();i++)db.update("INSERT INTO question_options(question_id,option_order,option_text) VALUES(?,?,?)",q,i,options.get(i));}
 public void status(long id,String s){db.update("UPDATE quizzes SET status=? WHERE id=?",s,id);}
 public long count(){return db.queryForObject("SELECT COUNT(*) FROM quizzes",Long.class);}
 public long pending(){return db.queryForObject("SELECT COUNT(*) FROM quizzes WHERE status='PENDING'",Long.class);}
}
