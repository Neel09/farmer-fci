package org.jai.kissan.reactive.examples;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.assertj.core.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
class B {
	private final A a;
}

@Getter
@Setter
@AllArgsConstructor
@ToString
class A {
	private final String id;
	private String name;
}

public class MainClass1 {

	public static void main(String[] s) throws InterruptedException {
		flux1();
	}

	private static void flux1() {
		Map<String, A> map = new HashMap<>();
		map.put("1", new A("1", "Neel1"));
		map.put("2", new A("2", "Neel2"));
		map.put("3", new A("3", "Neel3"));

		List<B> bs = new ArrayList<>();
		bs.add(new B(new A("1", null)));
		bs.add(new B(new A("2", null)));

		Flux.fromIterable(bs).doOnNext((b) -> b.getA().setName(map.get(b.getA().getId()).getName())).subscribe();

		bs.forEach(System.out::println);
	}

	private static void flux() throws InterruptedException {

		// Just is eager.
		// Mono.just(hello());
		/*
		 * Mono.justOrEmpty(empty(true)).switchIfEmpty(Mono.error(() -> new
		 * IllegalAccessException())) .onErrorMap((error) -> new
		 * ArithmeticException()).onErrorReturn("fallback")
		 * .doOnNext(System.out::println).subscribe();
		 */
		// Mono.error(() -> error1());
		// use fromCallable() or fromRunnable() to handle synchronous code
		// Mono.fromCallable(() -> hello());
		// Mono.fromRunnable(() -> hello());

		// Lazy
		// Mono.fromSupplier(() -> hello());

		// Mono.defer(() -> Mono.just(hello()));

		Flux.defer(() -> Mono.just(hello())).doOnNext(System.out::println).subscribe();

		// Flux.error(null)

		Mono.error(new RuntimeException());

		Thread.sleep(1000);

	}

	private static RuntimeException error1() {
		System.out.println("Hello");
		return new RuntimeException();
	}

	private static String empty(boolean flag) {
		// TODO Auto-generated method stub
		return flag ? null : "hello";

	}

	private static String hello() {
		System.out.println("Hello");
		return "hello";
	}

}
