package com.jdevy.domandj;

import com.jdevy.domandj.comment.CommentRepository;
import com.jdevy.domandj.post.Post;
import com.jdevy.domandj.post.PostRepository;
import com.jdevy.domandj.student.Student;
import com.jdevy.domandj.student.StudentRepository;
import com.jdevy.domandj.user.User;
import com.jdevy.domandj.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

/**
 * @author devy
 */
@Component
@Slf4j
class DataInitializer {

    private final PostRepository posts;
    private final UserRepository users;
    private final CommentRepository comments;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository students;

    public DataInitializer(StudentRepository students, PostRepository posts, UserRepository users, CommentRepository comments, PasswordEncoder passwordEncoder) {
        this.students = students;
        this.posts = posts;
        this.users = users;
        this.comments = comments;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(value = ContextRefreshedEvent.class)
    public void init() {
        initPosts();
        initStudents();
        initUsers();
        initComments();
    }

    private void initUsers() {
        log.info("start users initialization  ...");
        this.users
                .deleteAll()
                .thenMany(
                        Flux
                                .just("user", "admin")
                                .flatMap(
                                        username -> {
                                            List<String> roles = "user".equals(username)
                                                    ? Arrays.asList("ROLE_USER")
                                                    : Arrays.asList("ROLE_USER", "ROLE_ADMIN");

                                            User user = User.builder()
                                                    .roles(roles)
                                                    .username(username)
                                                    .password(passwordEncoder.encode("password"))
                                                    .email(username + "@example.com")
                                                    .build();
                                            return this.users.save(user);
                                        }
                                )
                )
                .log()
                .subscribe(
                        null,
                        null,
                        () -> log.info("done users initialization...")
                );
    }

    private void initStudents() {
        log.info("Start students data initialization ...");
        this.students
                .deleteAll()
                .thenMany(
                        Flux
                                .just("student1", "student2")
                                .flatMap(
                                        name -> this.students.save(Student.builder().firstName("firstName" + name).lastName("lastName" + name).avatar("svg-1").build())
                                )
                )
                .log()
                .subscribe(
                        null,
                        null,
                        () -> log.info("done users initialization...")
                );
    }

    private void initPosts() {
        log.info("start post data initialization  ...");
        this.posts
                .deleteAll()
                .thenMany(
                        Flux
                                .just("Post one", "Post two")
                                .flatMap(
                                        title -> this.posts.save(Post.builder().title(title).content("content of " + title).status(Post.Status.PUBLISHED).build())
                                )
                )
                .log()
                .subscribe(
                        null,
                        null,
                        () -> log.info("done post initialization...")
                );
    }

    private void initComments() {
        log.info("start comments data initialization  ...");
        this.posts
                .deleteAll()
                .log()
                .subscribe(
                        null,
                        null,
                        () -> log.info("done comments initialization...")
                );
    }

}
