package me.wonwoo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
@Slf4j
public class TobyWebfluxApplication {

  @GetMapping("/")
  Mono<String> hello() {
    return Mono.just("Hello WebFlux");
  }

  @GetMapping("/log")
  Mono<String> log() {
    return Mono.just("hello WebFlux").log();
  }

  @GetMapping("/onNext")
  Mono<String> onNext() {
    log.info("pos1");
    Mono<String> m = Mono.just("Hello WebFlux").doOnNext(log::info).log();
    log.info("pos2");
    return m;
  }


  @GetMapping("/method")
  Mono<String> method() {
    log.info("pos1");
    String msg = generateHello();
    Mono<String> m = Mono.just(msg).doOnNext(log::info).log();
    log.info("pos2");
    return m;
  }

  @GetMapping("/lazy")
  Mono<String> lazy() {
    log.info("pos1");
    Mono<String> m = Mono.fromSupplier(this::generateHello).doOnNext(log::info).log();
    log.info("pos2");
    return m;
  }

  @GetMapping("/subscribe")
  Mono<String> subscribe() {
    log.info("pos1");
    Mono<String> m = Mono.fromSupplier(this::generateHello).doOnNext(log::info).log();
    m.subscribe();
    log.info("pos2");
    return m;
  }

  @GetMapping("/block")
  Mono<String> block() {
    log.info("pos1");
    String msg = generateHello();
    Mono<String> m = Mono.just(msg).doOnNext(log::info).log();
    String msg2 = m.block();
    log.info("pos2: " + msg2);
    return m;
  }

  @GetMapping("/block2")
  Mono<String> block2() {
    log.info("pos1");
    String msg = generateHello();
    Mono<String> m = Mono.just(msg).doOnNext(log::info).log();
    String msg2 = m.block();
    log.info("pos2: " + msg2);
    return Mono.just(msg2);
  }

  private String generateHello() {
    log.info("method generateHello()");
    return "Hello Mono";
  }

  public static void main(String[] args) {
    SpringApplication.run(TobyWebfluxApplication.class, args);
  }
}
