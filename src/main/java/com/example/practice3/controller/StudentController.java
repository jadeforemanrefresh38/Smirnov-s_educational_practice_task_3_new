package com.example.practice3.controller;

import com.example.practice3.model.Student;
import com.example.practice3.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public String students(HttpSession session, Model model) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        model.addAttribute("students", studentService.getAllStudents());
        return "students-list";
    }

    @GetMapping("/students/add")
    public String addForm(HttpSession session, Model model) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        model.addAttribute("student", new Student());
        return "students-form";
    }

    @PostMapping("/students/add")
    public String addStudent(@ModelAttribute Student student, HttpSession session) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        student.setId(null);
        studentService.saveStudent(student);
        return "redirect:/students";
    }
}
