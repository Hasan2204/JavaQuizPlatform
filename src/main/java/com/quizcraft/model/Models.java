package com.quizcraft.model;
import java.util.List;
public final class Models {
 private Models(){}
 public record User(long id,String name,String email,String role){}
 public record LoginRequest(String email,String password){}
 public record LoginResponse(boolean success,String message,User user){}
 public record Quiz(long id,String title,String description,int durationMinutes,String status,long creatorId,String creatorName,int questionCount){}
 public record Question(long id,long quizId,String questionText,List<String> options,int correctOption){}
 public record Result(long attemptId,String quizTitle,int totalQuestions,int correctAnswers,int score,int percentage){}
 public record Message(long id,long senderId,String senderName,long receiverId,String receiverName,String text,String createdAt){}
}
