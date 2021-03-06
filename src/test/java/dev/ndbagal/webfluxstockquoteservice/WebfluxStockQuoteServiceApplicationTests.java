package dev.ndbagal.webfluxstockquoteservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import dev.ndbagal.webfluxstockquoteservice.model.Quote;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebfluxStockQuoteServiceApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void testFetchQuotes() {
		webTestClient.get().uri("/quotes?size=20").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON).expectBodyList(Quote.class).hasSize(20)
				.consumeWith(allQuotes -> {
					assertThat(allQuotes.getResponseBody()).allSatisfy(quote -> assertThat(quote.getPrice()).isPositive());
				});
	}

	@Test
	public void testStreamQuotes() throws InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(10);

		webTestClient.get().uri("/quotes").accept(MediaType.APPLICATION_NDJSON).exchange().returnResult(Quote.class)
				.getResponseBody().take(10).subscribe(quote -> {
					assertThat(quote.getPrice()).isPositive();
					countDownLatch.countDown();
				});

		countDownLatch.await();
	}
}
