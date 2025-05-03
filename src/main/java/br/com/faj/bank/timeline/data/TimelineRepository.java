package br.com.faj.bank.timeline.data;

import br.com.faj.bank.timeline.model.entity.TimelineEntity;
import org.springframework.data.repository.CrudRepository;

public interface TimelineRepository extends CrudRepository<TimelineEntity, Long> { }
