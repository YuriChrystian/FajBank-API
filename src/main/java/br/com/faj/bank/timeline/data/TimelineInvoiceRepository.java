package br.com.faj.bank.timeline.data;

import br.com.faj.bank.timeline.model.entity.TimelineInvoiceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TimelineInvoiceRepository extends CrudRepository<TimelineInvoiceEntity, Long> {
    
    @Query("select t from TimelineInvoiceEntity t where t.customerId = :customerId order by t.registeredIn desc")
    List<TimelineInvoiceEntity> findAllByCustomerId(@Param("customerId") Long customerId);
    
    @Query("select t from TimelineInvoiceEntity t where t.invoiceId = :invoiceId order by t.registeredIn desc")
    List<TimelineInvoiceEntity> findAllByInvoiceId(@Param("invoiceId") Long invoiceId);
} 