package br.com.faj.bank.timeline.data;

import br.com.faj.bank.timeline.model.entity.TimelineCustomerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TimelineCustomerRepository extends CrudRepository<TimelineCustomerEntity, Long> {

    List<TimelineCustomerEntity> findByCustomerId(Long customerId);

}
