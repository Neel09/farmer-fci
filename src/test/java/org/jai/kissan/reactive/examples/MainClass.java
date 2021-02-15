package org.jai.kissan.reactive.examples;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
public class MainClass {

    public static void main(String[] s) throws InterruptedException {

        /*Flux<Integer> flux = Flux.just(1, 2, 3, 4).map(i -> i * 2);

        //Invoking subscribe from main() method
        flux.subscribe(i -> System.out.println(i + "---> " + Thread.currentThread().getName()));

        //Invoking subscribe in a different thread
        new Thread(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flux.subscribe(i -> System.out.println("Invoking from a Thread --> " + i + "---> " + Thread.currentThread().getName()));
        }).start();*/

        //Using Scheduler's immediate() --> It is same as without Scheduler
        //Using Scheduler's immediate() --> Since, subscribe is called from main() method. Hence, map will be executed in main thread.
        /*Flux<Integer> fluxWithSchedulerImmediate = Flux.just(1, 2, 3, 4).map(i -> {
            System.out.println("Map : " + Thread.currentThread().getName());
            return i * 2;
        }).subscribeOn(Schedulers.immediate());
        fluxWithSchedulerImmediate.subscribe(i -> System.out.println("Invoking Immediate Scheduler --> " + i + "---> " + Thread.currentThread().getName()));

        //Using Scheduler's immediate() --> Invoke subscribe from different thread.
        Flux<Integer> fluxWithSchedulerImmediateFromThread = Flux.just(1, 2, 3, 4).subscribeOn(Schedulers.immediate());
        new Thread(() -> {
            fluxWithSchedulerImmediate.subscribe(i -> System.out.println("Invoking immediate Scheduler From Thread--> " + i + "---> " + Thread.currentThread().getName()));
        }).start();*/

        //Using Scheduler's single() --> It will be executed into different thread --> Asynchronously and without blocking main thread.
        //The actual use is --> it will reuse the same thread for all callers.
        /*Flux<Integer> fluxWithSchedulerSingle = Flux.just(1, 2, 3, 4).map(i -> {
            System.out.println("Map : " + Thread.currentThread().getName());
            return i * 2;
        });
        fluxWithSchedulerSingle.subscribeOn(Schedulers.single()).subscribe(i -> System.out.println("Invoking single Scheduler A --> " + i + "---> " + Thread.currentThread().getName()));
        fluxWithSchedulerSingle.subscribeOn(Schedulers.single()).subscribe(i -> System.out.println("Invoking single Scheduler B --> " + i + "---> " + Thread.currentThread().getName()));

        System.out.println("Inside Main Thread................");*/

        //Using Scheduler's newSingle() --> It is same s single but it will create different thread for different callers.
        /*Flux<Integer> fluxWithSchedulerNewSingle = Flux.just(1, 2, 3, 4).map(i -> {
//            System.out.println("Map : " + Thread.currentThread().getName());
            return i * 2;
        });
        fluxWithSchedulerNewSingle.subscribeOn(Schedulers.newSingle("New single")).subscribe(i -> System.out.println("Invoking newSingle Scheduler A--> " + i + "---> " + Thread.currentThread().getName()));
        fluxWithSchedulerNewSingle.subscribeOn(Schedulers.newSingle("New single")).subscribe(i -> System.out.println("Invoking newSingle Scheduler B--> " + i + "---> " + Thread.currentThread().getName()));
        fluxWithSchedulerNewSingle.subscribeOn(Schedulers.newSingle("New single")).subscribe(i -> System.out.println("Invoking newSingle Scheduler C--> " + i + "---> " + Thread.currentThread().getName()));
        fluxWithSchedulerNewSingle.subscribeOn(Schedulers.newSingle("New single")).subscribe(i -> System.out.println("Invoking newSingle Scheduler D--> " + i + "---> " + Thread.currentThread().getName()));
        fluxWithSchedulerNewSingle.subscribeOn(Schedulers.newSingle("New single")).subscribe(i -> System.out.println("Invoking newSingle Scheduler E--> " + i + "---> " + Thread.currentThread().getName()));

        System.out.println("Inside Main Thread................");
*/
        //Using Scheduler's parallel() --> It will execute asynchronously and create as many worker threads as you have cores.
        //Since, machine I'm using has 4 cores hence only 4 threads will be created.
        /*Flux<Integer> fluxWithSchedulerParallel = Flux.just(1, 2, 3, 4).map(i -> {
//            System.out.println("Map : " + Thread.currentThread().getName());
            return i * 2;
        });
        fluxWithSchedulerParallel.subscribeOn(Schedulers.parallel()).subscribe(i -> System.out.println("Invoking parallel Scheduler A--> " + i + "---> " + Thread.currentThread().getName()));
        fluxWithSchedulerParallel.subscribeOn(Schedulers.parallel()).subscribe(i -> System.out.println("Invoking parallel Scheduler B--> " + i + "---> " + Thread.currentThread().getName()));
        fluxWithSchedulerParallel.subscribeOn(Schedulers.parallel()).subscribe(i -> System.out.println("Invoking parallel Scheduler C--> " + i + "---> " + Thread.currentThread().getName()));
        fluxWithSchedulerParallel.subscribeOn(Schedulers.parallel()).subscribe(i -> System.out.println("Invoking parallel Scheduler D--> " + i + "---> " + Thread.currentThread().getName()));
        fluxWithSchedulerParallel.subscribeOn(Schedulers.parallel()).subscribe(i -> System.out.println("Invoking parallel Scheduler E--> " + i + "---> " + Thread.currentThread().getName()));

        System.out.println("Inside Main Thread................");*/

        /*Flux<Integer> fluxWithSchedulerNewParallel = Flux.just(1, 2, 3, 4).map(i -> i * 2);
        fluxWithSchedulerNewParallel.subscribeOn(Schedulers.newParallel("New Parallel",3)).subscribe(i -> System.out.println("Invoking parallel Scheduler A--> " + i + "---> " + Thread.currentThread().getName()));
        fluxWithSchedulerNewParallel.subscribeOn(Schedulers.newParallel("New Parallel",3)).subscribe(i -> System.out.println("Invoking parallel Scheduler B--> " + i + "---> " + Thread.currentThread().getName()));
        fluxWithSchedulerNewParallel.subscribeOn(Schedulers.newParallel("New Parallel",3)).subscribe(i -> System.out.println("Invoking parallel Scheduler C--> " + i + "---> " + Thread.currentThread().getName()));
        fluxWithSchedulerNewParallel.subscribeOn(Schedulers.newParallel("New Parallel",3)).subscribe(i -> System.out.println("Invoking parallel Scheduler D--> " + i + "---> " + Thread.currentThread().getName()));
        fluxWithSchedulerNewParallel.subscribeOn(Schedulers.newParallel("New Parallel",3)).subscribe(i -> System.out.println("Invoking parallel Scheduler E--> " + i + "---> " + Thread.currentThread().getName()));

        System.out.println("Inside Main Thread................");*/


        //Use of PublishOn() --> you can do parallelize at any given point
        //It changes the execution context
        //An Example of supporting Segments
        //Execution happens in parallel but onNext calls happens in sequence, as per specification
        //It affects where the subsequent operators execute
        /*Scheduler scheduler = Schedulers.parallel();
        final Flux<String> flux = Flux
                .range(1, 5)
                .map(i -> {
                    System.out.println("First Map : " + Thread.currentThread().getName());
                    return 10 + i;
                })
                .publishOn(scheduler)
                .map(i -> {
                    System.out.println("Second Map : " + Thread.currentThread().getName());
                    return "value " + i;
                });

        flux.subscribe(i -> System.out.println("Scheduler A : " + Thread.currentThread().getName() + " : " + i));
        flux.subscribe(i -> System.out.println("Scheduler B : " + Thread.currentThread().getName() + " : " + i));
        Thread.sleep(1000);*/

        //Use of subscribeOn()
        //It changes the execution context
        //An Example of supporting Segments
        //Execution happens in parallel but onNext calls happens in sequence, as per specification
        //It affects the context of source emission
        /*Scheduler scheduler = Schedulers.parallel();
        final Flux<String> flux = Flux
                .range(1, 5)
                .map(i -> {
                    System.out.println("First Map : " + Thread.currentThread().getName());
                    return 10 + i;
                })
                .subscribeOn(scheduler)
                .map(i -> {
                    System.out.println("Second Map : " + Thread.currentThread().getName());
                    return "value " + i;
                });

        flux.subscribe(i -> System.out.println("Scheduler A : " + Thread.currentThread().getName() + " : " + i));
        flux.subscribe(i -> System.out.println("Scheduler B : " + Thread.currentThread().getName() + " : " + i));
        Thread.sleep(1000);*/

        flux_merge_concat_zip();
    }

    private static void flux_merge_concat_zip() throws InterruptedException {

        Flux<Integer> fluxIntegers1 = Flux.range(1, 5);
        Flux<Integer> fluxIntegers2 = Flux.range(6, 5);

        /*//Using Merge --> Sequential elements are not guaranteed. Also, it is subscribed to publisher eagerly
        System.out.println(Flux.merge(fluxIntegers1.delayElements(Duration.ofMillis(500))
                , fluxIntegers2.delayElements(Duration.ofMillis(1000))).log().subscribe(i -> {
            System.out.println("Merge : " + Thread.currentThread() + " Value : " + i);
        }));*/

        System.out.println("------------------------");

        //Using Concat --> Sequential elements is guaranteed. as second will start once first is completed
        //Lazy Subscription
        /*Flux.concat(fluxIntegers1.delayElements(Duration.ofMillis(500))
                , fluxIntegers2.delayElements(Duration.ofMillis(1000))).log().subscribe(i -> {
            System.out.println("Concat : " + Thread.currentThread() + " Value : " + i);
        });*/

        //Using Merge --> Sequential elements are not guaranteed
        /*Flux.merge(fluxIntegers1.delayElements(Duration.ofMillis(500)).map(i -> {
                    if (i > 3) throw new RuntimeException("Error!!");
                    return i;
                })
                , fluxIntegers2.delayElements(Duration.ofMillis(1000))).subscribe(i -> {
            System.out.println("Merge : " + Thread.currentThread() + " Value : " + i);
        });*/

        //Using Zip --> It emits single element at a time from each producer then combines elements.
        /*Flux.zip(fluxIntegers1.delayElements(Duration.ofMillis(500))
                , fluxIntegers2.delayElements(Duration.ofMillis(1000))).subscribe(i -> {
            System.out.println("Zip : " + Thread.currentThread() + " Value : " + i);
        });*/

        //Using Zip --> It emits single element at a time from each producer then combines elements.
        Flux.zip(fluxIntegers1.delayElements(Duration.ofMillis(500))
                , fluxIntegers2.delayElements(Duration.ofMillis(1000))
                , (a, b) -> a + b).subscribe(i -> {
            System.out.println("Zip : " + Thread.currentThread() + " Value : " + i);
        });

        /*
        //Using Concat --> Sequential elements is guaranteed. as second will start once first is completed
        Flux.concat(fluxIntegers1.delayElements(Duration.ofMillis(500)).map(i -> {
                    if (i > 3) throw new RuntimeException("Error!!");
                    return i;
                })
                , fluxIntegers2.delayElements(Duration.ofMillis(1000))).log().subscribe(i -> {
            System.out.println("Concat : " + Thread.currentThread() + " Value : " + i);
        });*/

        Thread.sleep(10000);
    }

    private static void usingStreams_Block() {

    }

    private static void usingReactors_Non_Blocking() {
        Mono<Integer> mono = Mono.just(1);

        Mono<String> futureString = Mono.fromCallable(() -> "Hello");

        // No need to handle exception. It automatically wraps it into Mono.error()
        Mono<String> errorFuture = Mono.fromCallable(() -> {
            throw new Exception();
        });

        // Same as fromCallable but its supplier should return Mono<T>
        // Also, if supplier method throws an exception it has to be handled explicitly
        Mono<String> futureString1 = Mono.defer(() -> Mono.just("Hello"));

        Mono<String> futureStringWithDefer = Mono.defer(() -> {
            try {
                throw new Exception("error");
            } catch (Exception e) {
                return Mono.error(e);
            }
        });
        futureStringWithDefer.subscribe();

        Flux<Integer> numbers = Flux.just(1, 2, 3);
    }

}
