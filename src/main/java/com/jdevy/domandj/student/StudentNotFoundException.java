package com.jdevy.domandj.student;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(String id) {
        super("The student : " + id + " is not found !");
    }
}
