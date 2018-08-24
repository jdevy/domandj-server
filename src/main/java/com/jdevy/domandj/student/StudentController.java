package com.jdevy.domandj.student;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController()
@RequestMapping(value = "/api/students")
public class StudentController {
    private final StudentRepository students;

    public StudentController(StudentRepository students) {
        this.students = students;
    }

    @GetMapping("")
    public Flux<Student> all() {
        return this.students.findAll();
    }

    @PostMapping("")
    public Mono<Student> create(@RequestBody Student student) {
        return this.students.save(student);
    }

    @GetMapping("/{id}")
    public Mono<Student> get(@PathVariable("id") String id) {
        return this.students.findById(id).switchIfEmpty(Mono.error(new StudentNotFoundException(id)));
    }

    @PutMapping("/{id}")
    public Mono<Student> update(@PathVariable("id") String id, @RequestBody Student student) {
        return this.students.findById(id)
                .map(p -> {
                    p.setLastName(student.getLastName());
                    p.setFirstName(student.getFirstName());

                    return p;
                })
                .flatMap(p -> this.students.save(p));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public Mono<Void> delete(@PathVariable("id") String id) {
       // return this.students.deleteById(id);
        return this.students.findById(id)
        .flatMap(this.students::delete);

    }

}
