//package net.java.lms_backend.entity;
//import jakarta.persistence.*;
//
//import java.util.List;
//
//@Entity
//public class QuestionBank {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @OneToOne
//    private Course course;
//
//    @OneToMany(mappedBy = "questionBank", cascade = CascadeType.ALL)
//    private List<Question> questions;
//}
//
