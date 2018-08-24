package com.jdevy.domandj.comment;

import com.jdevy.domandj.post.PostId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {

    //@Tailable
    Flux<Comment> findByPost(PostId id);

}
