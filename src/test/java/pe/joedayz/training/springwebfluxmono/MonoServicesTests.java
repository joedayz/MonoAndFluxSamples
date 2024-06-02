package pe.joedayz.training.springwebfluxmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class MonoServicesTests {

    //Let's create an example to demonstrate the usage of Mono implementation of the Publisher interface:
    @Test
    void createMonoObject(){
        Mono<String> monoPublisher = Mono.just("Testdata").log();

        monoPublisher.subscribe(data -> {
            System.out.println("Mono -> data = " + data);
        });
    }

    //Let me demonstrate the usage of the onError() method. If there are any errors while
    //sending the data then the Publisher call onError() method to send error details to the Subscriber
    @Test
    void createMonoObjectWithError(){
        Mono<String> monoPublisher = Mono.just("Testdata").log()
                        .then(Mono.error(new RuntimeException("Error ocurred while publishing data")));

        monoPublisher.subscribe(data -> {
            System.out.println("Mono -> data = " + data);
        }, error -> {
            System.out.println("Mono -> error = " + error);
        });
    }

    //The zipWith() method combines the result from this mono and another mono object.
    @Test
    void createMonoObjectWithZip(){
        var frutas = Mono.just("Mango");
        var vegetales = Mono.just("Tomato");

        frutas.zipWith(vegetales)
                .subscribe(data -> {
                    System.out.println("Mono -> data = " + data);
                });
    }

    //flatMap() - Transform the item emitted by this Mono asynchronously, returning the value emitted by another Mono
    // (possibly changing the value type).

    //flatMapMany() - Transform the item emitted by this Mono into a Publisher, then forward its emissions
    // into the returned Flux.

    @Test
    void createMonoObjectWithFlatMap(){
        var frutaMonoFlatMap = Mono.just("Mango")
                .flatMap(s -> Mono.just(List.of(s.split("")))).log();

        var frutaMonoFlatMapMany = Mono.just("Mango")
                .flatMapMany(s -> Flux.just(s.split(""))).log();

        frutaMonoFlatMap.subscribe(data -> {
            System.out.println("s -> data = " + data);
        });

        frutaMonoFlatMapMany.subscribe(data -> {
            System.out.println("Mono -> data = " + data);
        });
    }
}
