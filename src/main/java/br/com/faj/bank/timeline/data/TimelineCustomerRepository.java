package br.com.faj.bank.timeline.data;

import br.com.faj.bank.timeline.model.entity.TimelineCustomerEntity;
import org.springframework.data.repository.CrudRepository;

public interface TimelineCustomerRepository extends CrudRepository<TimelineCustomerEntity, Long> { }
