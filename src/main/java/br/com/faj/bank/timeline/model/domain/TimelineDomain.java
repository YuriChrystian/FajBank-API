package br.com.faj.bank.timeline.model.domain;

import br.com.faj.bank.timeline.model.TimelineContext;
import br.com.faj.bank.timeline.model.TimelineType;

import java.time.LocalDateTime;

public record TimelineDomain(
        TimelineType type,
        LocalDateTime date,
        TimelineContext data
) { }
