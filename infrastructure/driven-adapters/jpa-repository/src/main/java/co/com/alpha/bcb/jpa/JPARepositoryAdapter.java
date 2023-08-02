package co.com.alpha.bcb.jpa;

import co.com.alpha.bcb.jpa.data.StoredEvent;
import co.com.alpha.bcb.jpa.helper.AdapterOperations;
import co.com.alpha.bcb.model.post.generic.DomainEvent;
import co.com.alpha.bcb.serializer.JSONMapper;
import co.com.alpha.bcb.usecase.generic.gateways.DomainEventRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.Date;

@Repository
public class JPARepositoryAdapter
        //extends AdapterOperations<DomainEvent/* change for domain model */, StoredEvent/* change for adapter model */, String, JPARepository>
        implements DomainEventRepository
// implements ModelRepository from domain
{

    private final JPARepository repository;

    private final JSONMapper eventSerializer;

    public JPARepositoryAdapter(JPARepository repository, JSONMapper eventSerializer) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        //super(repository, mapper, d -> mapper.map(d, DomainEvent.class/* change for domain model */));
        this.repository = repository;
        this.eventSerializer = eventSerializer;
    }

    @Override
    public Flux<DomainEvent> findById(String aggregateId) {
        Flux<StoredEvent> storedEvents = Flux.fromIterable(repository.findByAggregateRootId(aggregateId));

        return storedEvents.map(storeEvent -> storeEvent.deserializeEvent(eventSerializer));
    }

    @Override
    public Mono<DomainEvent> saveEvent(DomainEvent event) {
        StoredEvent storedEvent = new StoredEvent();
        storedEvent.setAggregateRootId(event.aggregateRootId());
        storedEvent.setTypeName(event.getClass().getTypeName());
        storedEvent.setOccurredOn(new Date());
        storedEvent.setEventBody(StoredEvent.wrapEvent(event, eventSerializer));
        return Mono.just(repository.save(storedEvent).deserializeEvent(eventSerializer));
    }
}
