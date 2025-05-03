package br.com.faj.bank.timeline.model.domain;

public record TimelineCustomerDataDomain(
        String description
) implements TimelineContext { }
