package dev.ndbagal.webfluxstockquoteservice.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class QuoteRouter {

  @Bean
  public RouterFunction<ServerResponse> route(QuoteHandler handler) {
    return RouterFunctions
        .route(RequestPredicates.GET("/quotes").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
            handler::fetchQuotes)
        .andRoute(RequestPredicates.GET("/quotes").and(RequestPredicates.accept(MediaType.APPLICATION_STREAM_JSON)),
            handler::streamQuotes);
  }
}
