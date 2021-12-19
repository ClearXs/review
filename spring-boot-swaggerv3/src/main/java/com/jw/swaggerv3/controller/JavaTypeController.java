package com.jw.swaggerv3.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/type")
public class JavaTypeController {

    @PostMapping("bool")
    public Mono<Boolean> boolMapping(@RequestBody Boolean bool) {
        return Mono.just(bool);
    }

    @PostMapping("int")
    public Mono<Integer> intMapping(@RequestBody Integer i) {
        return Mono.just(i);
    }

    @PostMapping("float")
    public Mono<Float> floatMapping(@RequestBody Float f) {
        return Mono.just(f);
    }

    @PostMapping("double")
    public Mono<Double> doubleMapping(@RequestBody Double d) {
        return Mono.just(d);
    }

    @PostMapping("string")
    public Mono<String> strMapping(@RequestBody String str) {
        return Mono.just(str);
    }

    @PostMapping("byte")
    public Mono<Byte> byteMapping(@RequestBody Byte b) {
        return Mono.just(b);
    }

    @PostMapping("char")
    public Mono<Character> charMapping(@RequestBody Character c) {
        return Mono.just(c);
    }

}
