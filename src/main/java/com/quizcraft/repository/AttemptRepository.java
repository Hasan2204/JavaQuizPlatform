package com.quizcraft.repository;
import com.quizcraft.model.Models.Result;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository public class AttemptRepository{
 private final JdbcTemplate db; public AttemptRepository(JdbcTemplate db){this.db=db;}
 public Result submit(long user,long quiz,List<Integer> ans){var rows=db.queryForList("SELECT id,correct_option FROM questions WHERE quiz_id=? ORDER BY id",quiz);int correct=0;for(int i=0;i<Math.min(rows.size(),ans.size());i++)if(((Number)rows.get(i).get("correct_option")).intValue()==ans.get(i))correct++;int total=rows.size();int pct=total==0?0:Math.round(correct*100f/total);db.update("INSERT INTO quiz_attempts(user_id,quiz_id,total_questions,correct_answers,score,percentage) VALUES(?,?,?,?,?,?)",user,quiz,total,correct,correct,pct);long a=db.queryForObject("SELECT LAST_INSERT_ID()",Long.class);for(int i=0;i<Math.min(rows.size(),ans.size());i++)db.update("INSERT INTO attempt_answers(attempt_id,question_id,selected_option) VALUES(?,?,?)",a,((Number)rows.get(i).get("id")).longValue(),ans.get(i));String title=db.queryForObject("SELECT title FROM quizzes WHERE id=?",String.class,quiz);return new Result(a,title,total,correct,correct,pct);}
 public List<Result> byUser(long user){return db.query("SELECT a.id,q.title,a.total_questions,a.correct_answers,a.score,a.percentage FROM quiz_attempts a JOIN quizzes q ON q.id=a.quiz_id WHERE a.user_id=? ORDER BY a.id DESC",(r,n)->new Result(r.getLong(1),r.getString(2),r.getInt(3),r.getInt(4),r.getInt(5),r.getInt(6)),user);}
 public long count(){return db.queryForObject("SELECT COUNT(*) FROM quiz_attempts",Long.class);}
}
