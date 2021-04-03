package dev.ndbagal.webfluxstockquoteservice.service.impl;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

import dev.ndbagal.webfluxstockquoteservice.model.Quote;
import reactor.core.publisher.Flux;

public class QuoteGeneratorServiceImplTest {

  private final QuoteGeneratorServiceImpl quoteGeneratorService = new QuoteGeneratorServiceImpl();

  @Test
  public void fetchQuoteStream() throws Exception {
    // Get flux of quotes
    Flux<Quote> quoteFlux = quoteGeneratorService.fetchQuoteStream(Duration.ofMillis(1L));

    quoteFlux.take(2000).subscribe(System.out::println);
  }

  @Test
  public void fetchQuoteStreamCountDown() throws Exception {
    // Get flux of quotes
    Flux<Quote> quoteFlux = quoteGeneratorService.fetchQuoteStream(Duration.ofMillis(100L));

    // subscriber lambda
    Consumer<Quote> println = System.out::println;

    // error handler
    Consumer<Throwable> errorHandler = e -> System.out.println("Some error occured");

    // set countDounLatch to 1
    CountDownLatch countDownLatch = new CountDownLatch(1);

    // Runnable called upon complete, count down latch
    Runnable allDone = () -> countDownLatch.countDown();

    quoteFlux.take(30).subscribe(println, errorHandler, allDone);

    countDownLatch.await();
  }

}
