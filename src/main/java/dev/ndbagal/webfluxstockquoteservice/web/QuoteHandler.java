package dev.ndbagal.webfluxstockquoteservice.web;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import dev.ndbagal.webfluxstockquoteservice.model.Quote;
import dev.ndbagal.webfluxstockquoteservice.service.QuoteGeneratorService;
import reactor.core.publisher.Mono;

@Component
public class QuoteHandler {

  private final QuoteGeneratorService quoteGeneratorService;

  public QuoteHandler(QuoteGeneratorService quoteGeneratorService) {
    this.quoteGeneratorService = quoteGeneratorService;
  }

  public Mono<ServerResponse> fetchQuotes(ServerRequest request) {
    int size = Integer.parseInt(request.queryParam("size").orElse("10"));

    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
        .body(this.quoteGeneratorService.fetchQuoteStream(Duration.ofMillis(100)).take(size), Quote.class);
  }

  public Mono<ServerResponse> streamQuotes(ServerRequest request) {
    return ServerResponse.ok().contentType(MediaType.APPLICATION_NDJSON)
        .body(this.quoteGeneratorService.fetchQuoteStream(Duration.ofMillis(200)), Quote.class);
  }
}
