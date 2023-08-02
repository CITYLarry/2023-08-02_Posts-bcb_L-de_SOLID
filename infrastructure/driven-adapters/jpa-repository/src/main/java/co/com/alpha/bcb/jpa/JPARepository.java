package co.com.alpha.bcb.jpa;

import co.com.alpha.bcb.jpa.data.StoredEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JPARepository extends JpaRepository<StoredEvent, Integer>
        //CrudRepository<Object/* change for adapter model */, String>, QueryByExampleExecutor<Object/* change for adapter model */>
{
    List<StoredEvent> findByAggregateRootId(String aggregateRootId);
}
