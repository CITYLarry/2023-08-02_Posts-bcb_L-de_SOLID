package co.com.alpha.bcb.usecase.createpost;


import co.com.alpha.bcb.model.post.Post;
import co.com.alpha.bcb.model.post.generic.DomainEvent;
import co.com.alpha.bcb.model.post.values.*;
import co.com.alpha.bcb.usecase.generic.UseCaseForCommand;
import co.com.alpha.bcb.usecase.generic.commands.CreatePostCommand;
import co.com.alpha.bcb.usecase.generic.gateways.DomainEventRepository;
import co.com.alpha.bcb.usecase.generic.gateways.EventBus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CreatePostUseCase extends UseCaseForCommand<CreatePostCommand> {
    private final DomainEventRepository repository;
    private final EventBus bus;

    public CreatePostUseCase(DomainEventRepository repository, EventBus bus) {
        this.repository = repository;
        this.bus = bus;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<CreatePostCommand> createPostCommandMono) {
        return createPostCommandMono.flatMapIterable(command -> {
            Post post = new Post(PostId.of(command.getPostId()),
                    new Title(command.getTitle()),
                    new Author(command.getAuthor()),
                    CommentId.of(command.getCommentId()),
                    new Content(command.getComment()),
                    new Author(command.getCommentAuthor()));
            return post.getUncommittedChanges();
        }).flatMap(event -> {
                        return repository.saveEvent(event);
                })
                .map(event -> {
                        bus.publish(event);
                    return event;
        });
    }
}
