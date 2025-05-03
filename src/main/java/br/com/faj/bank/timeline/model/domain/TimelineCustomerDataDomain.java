package br.com.faj.bank.timeline.model.domain;

import br.com.faj.bank.timeline.model.TimelineContext;

public record TimelineCustomerDataDomain(
        String description
) implements TimelineContext { }
