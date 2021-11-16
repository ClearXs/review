package com.jw.reactor.webflux;

import com.jw.reactor.webflux.vo.SystemVo;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public class SystemTest extends BaseTest {

    @Test
    public void testList() {
        client.get()
                .uri("/system/list")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json("[{\"code\":200,\"success\":true,\"data\":\"{\\\"id\\\":\\\"1\\\",\\\"name\\\":\\\"\\\"}\",\"message\":\"成功\"}]");
    }

    @Test
    public void testGet() {
        client.get()
                .uri("/system/get/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json("{\"code\":200,\"success\":true,\"data\":{\"id\":\"1\",\"name\":\"\"},\"message\":\"成功\"}");
    }

    @Test
    public void testAdd() {
        client.post()
                .uri("/system/add")
                .body(Mono.just(SystemVo.of("2")), SystemVo.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json("{\"code\":200,\"success\":true,\"data\":true,\"message\":\"成功\"}");
    }
}
