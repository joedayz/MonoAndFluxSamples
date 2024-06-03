package pe.joedayz.training.springwebfluxmono;

import net.bytebuddy.build.ToStringPlugin;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class FluxServicesTests {

    //Here is a simple Flux example that returns multiple elements:
    @Test
    void createFluxObject(){
        Flux<String> frutas = Flux.fromIterable(List.of("Mango", "Orange", "Banana")).log();

        frutas.subscribe(data -> {
            System.out.println("data -> " + data);
        });
    }

    //The map() transform the items emitted by this Flux by applying a synchronous function to each item
    @Test
    void mapFluxObject(){
        Flux<String> frutas = Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                        .map(String::toUpperCase);

        frutas.map(s -> s.toUpperCase())
                .subscribe(data -> {
                    System.out.println("data -> " + data);
                });
    }

    //The filter() method evaluates each source value against the given Predicate.
    @Test
    void filterFluxObject(){
        Flux<String> frutas = Flux.fromIterable(List.of("Mango","Orange","Banana"))
                .filter(s -> s.length() > 5);

        frutas.subscribe(data -> {
            System.out.println("data -> " + data);
        });
    }

    //flatMap() and delayElements() Methods Example
    @Test
    void flatMapFluxObject(){
        Flux<String> frutasFlatMap = Flux.fromIterable(List.of("Mango","Orange","Banana"))
                .flatMap(s -> Flux.just(s.split("")))
                .log();

        frutasFlatMap.subscribe(data -> {
            System.out.println("data -> " + data);
        });

        Flux<String> frutasFlatMapAsync = Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                .flatMap(s -> Flux.just(s.split(""))
                        .delayElements(Duration.ofMillis(
                                new Random().nextInt(1000)
                        )))
                .log();

        frutasFlatMapAsync.subscribe(data -> {
            System.out.println("data -> " + data);
        });
    }

    //transform() - Transform this Flux in order to generate a target Flux.
    @Test
    void transformFluxObject(){
        Function<Flux<String>,Flux<String>> filterData
                = data -> data.filter(s -> s.length() > 5);

        Flux<String> frutas = Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                .transform(filterData)
                .log();

        frutas.subscribe(data -> {
            System.out.println("data -> " + data);
        });

    }

    //defaultIfEmpty() - Provide a default unique value if this sequence is completed without any data.
    @Test
    void transformDefaultIfEmpty(){
        Function<Flux<String>,Flux<String>> filterData
                = data -> data.filter(s -> s.length() > 5);

        Flux<String> frutas = Flux.fromIterable(List.of("Mango","Orange","Banana"))
                .transform(filterData)
                .defaultIfEmpty("Default")
                .log();

        frutas.subscribe(data -> {
            System.out.println("data -> " + data);
        });

    }


    //switchIfEmpty() - Switch to an alternative Publisher if this sequence is completed without any data.
    @Test
    void transformSwitchIfEmpty(){
        Function<Flux<String>,Flux<String>> filterData
                = data -> data.filter(s -> s.length() > 8);

        Flux<String> frutas = Flux.fromIterable(List.of("Mango","Orange","Banana"))
                .transform(filterData)
                .switchIfEmpty(Flux.just("Pineapple","Jack Fruit")
                        .transform(filterData))
                .log();

        frutas.subscribe(data -> {
            System.out.println("data -> " + data);
        });

    }

    //concat() - Concatenate all sources provided in an Iterable, forwarding element emitted by the sources downstream.

    @Test
    void concatExample(){
        var fruits = Flux.just("Mango","Orange");
        var veggies = Flux.just("Tomato","Lemon");

        Flux<String> frutasYVegetales = fruits.concatWith(veggies);

        frutasYVegetales.subscribe(data -> {
            System.out.println("data -> " + data);
        });
    }

    //concatWith() - Concatenate emissions of this Flux with the provided Publisher (no interleave).
    @Test
    void concatWithExample(){
        var fruits = Mono.just("Mango");
        var veggies = Mono.just("Tomato");

        Flux<String> frutasYVegetales = fruits.concatWith(veggies);

        frutasYVegetales.subscribe(data -> {
            System.out.println("data -> " + data);
        });
    }

    //merge() - Merge data from Publisher sequences contained in an array / vararg into an interleaved merged sequence.
    @Test
    void mergeExample(){
        var fruits = Flux.just("Mango","Orange");
        var veggies = Flux.just("Tomato","Lemon");

        Flux<String> frutasYVegetales = Flux.merge(fruits,veggies);

        frutasYVegetales.subscribe(data -> {
            System.out.println("data -> " + data);
        });
    }


    //mergeWith() - Merge data from this Flux and a Publisher into an interleaved merged sequence.
    @Test
    void mergeWithExample(){
        var fruits = Flux.just("Mango","Orange");
        var veggies = Flux.just("Tomato","Lemon");

        Flux<String> frutasYVegetales = fruits.mergeWith(veggies);

        frutasYVegetales.subscribe(data -> {
            System.out.println("data -> " + data);
        });
    }

    //zip() - Combine the values from this Flux and another into a Tuple2.
    @Test
    void zipExample(){
        var fruits = Flux.just("Mango", "Orange");
        var veggies = Flux.just("Tomato", "Lemon");

        Flux<String> frutasYVegetales = Flux.zip(fruits, veggies,
                (first, second) -> first + second).log();

        frutasYVegetales.subscribe(data -> {
            System.out.println("data = " + data);
        });
    }


    //zipWith() - Combine the values from this Flux and another into a Tuple2.
    @Test
    void zipWithExample(){
        var fruits = Flux.just("Mango", "Orange");
        var veggies = Flux.just("Tomato", "Lemon");

        Flux<String> frutasYVegetales = fruits.zipWith(veggies,
                (first, second) -> first + second).log();

        frutasYVegetales.subscribe(data -> {
            System.out.println("Mono -> data = " + data);
        });
    }

    //Using Tuple
    @Test
    void zipWithTupleExample(){
        var fruits = Flux.just("Mango","Orange");
        var veggies = Flux.just("Tomato","Lemon");
        var moreVeggies = Flux.just("Potato","Beans");

        Flux<String> frutasAndVegetales = Flux.zip(fruits, veggies, moreVeggies)
                .map(objects -> objects.getT1() + objects.getT2() + objects.getT3());

        frutasAndVegetales.subscribe(data -> {
            System.out.println("Mono -> data =  " + data);
        });
    }

    //onErrorResume() - Switch to an alternative Publisher that will provide fallback data in case of error.
    @Test
    void onFluxOnErrorReturn(){
        Flux<String> fruitsFluxOnErrorReturn = Flux.just("Apple", "Mango")
                .concatWith(Flux.error(
                        new RuntimeException("Exception Occurred")
                ))
                .onErrorReturn("Orange");

        fruitsFluxOnErrorReturn.subscribe(s -> {
                    System.out.println("s = " + s);
                });
    }

    // onErrorContinue() - Continue the sequence without error
    @Test
    void onFluxOnErrorContinue(){
        Flux<String> fruitsFluxOnErrorContinue = Flux.just("Apple", "Mango", "Orange")
                .map(s -> {
                    if (s.equalsIgnoreCase("Mango"))
                        throw new RuntimeException("Exception Occurred");
                    return s.toUpperCase();
                })
                .onErrorContinue((e, f) -> {
                    System.out.println("e = " + e);
                    System.out.println("f = " + f);
                });

        fruitsFluxOnErrorContinue
                .subscribe(s -> {
                    System.out.println("Mono -> s = " + s);
                });
    }

    // onErrorMap() - Map the error to a different one
    @Test
    void onFluxOnErrorMap(){
        var fruitsFluxOnErrorMap = Flux.just("Apple", "Mango", "Orange")
                .checkpoint("Error Checkpoint1")
                .map(s -> {
                    if (s.equalsIgnoreCase("Mango"))
                        throw new RuntimeException("Exception Occurred");
                    return s.toUpperCase();
                })
                .checkpoint("Error Checkpoint2")
                .onErrorMap(throwable -> {
                    System.out.println("throwable = " + throwable);
                    return new IllegalStateException("From onError Map");
                });

        fruitsFluxOnErrorMap
                .subscribe(s -> {
                    System.out.println("Mono -> s = " + s);
                });
    }

}
