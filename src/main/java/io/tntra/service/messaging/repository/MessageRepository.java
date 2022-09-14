package io.tntra.service.messaging.repository;

import io.tntra.service.messaging.model.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message,Long> {

}
