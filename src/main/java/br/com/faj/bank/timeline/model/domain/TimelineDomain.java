package br.com.faj.bank.timeline.model.domain;

import br.com.faj.bank.timeline.model.TimelineType;

import java.util.Date;

public record TimelineDomain(
        TimelineType type,
        Date date,
        TimelineContext data
) { }
