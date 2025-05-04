package br.com.faj.bank.invoice.data;

import br.com.faj.bank.invoice.model.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvoiceRepository extends CrudRepository<InvoiceEntity, Long> {
    
    @Query("select i from InvoiceEntity i where i.customerId = :customerId order by i.dueDate desc")
    List<InvoiceEntity> findAllByCustomerId(@Param("customerId") Long customerId);
    
    @Query("select i from InvoiceEntity i where i.id = :id and i.customerId = :customerId")
    InvoiceEntity findByIdAndCustomerId(@Param("id") Long id, @Param("customerId") Long customerId);
} 