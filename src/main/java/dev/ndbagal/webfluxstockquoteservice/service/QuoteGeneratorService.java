package dev.ndbagal.webfluxstockquoteservice.service;

import java.time.Duration;

import dev.ndbagal.webfluxstockquoteservice.model.Quote;
import reactor.core.publisher.Flux;

public interface QuoteGeneratorService {
  Flux<Quote> fetchQuoteStream(Duration period);
}
