package com.jw.reactor.webflux;

import com.jw.reactor.webflux.vo.UserVo;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public class UserTest extends BaseTest {

    @Test
    public void testList() {
        client.get()
                .uri("/user2/list")
                // 发送请求
                .exchange()
                // 对结果进行预期的构建，如果不符合预期则抛出异常
                .expectStatus().isOk()
                .expectBody().json("[\n" +
                        "    {\n" +
                        "        \"id\":\"1\",\n" +
                        "        \"username\":null,\n" +
                        "        \"password\":null\n" +
                        "    }\n" +
                        "]");
    }

    @Test
    public void testGet() {
        client.get()
                .uri("/user2/get/2")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json("[\n" +
                        "    {\n" +
                        "        \"id\":\"1\",\n" +
                        "        \"username\":null,\n" +
                        "        \"password\":null\n" +
                        "    }\n" +
                        "]");
    }

    @Test
    public void testAdd() {
        client.post()
                .uri("/user2/add")
                .body(Mono.just(UserVo.of("1")), UserVo.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json("true");
    }

    @Test
    public void testUpdate() {
        client.put()
                .uri("/user2/update")
                .body(Mono.just(UserVo.of("1")), UserVo.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json("true");
    }

    @Test
    public void testDelete() {
        client.delete()
                .uri("/user2/delete?id=1")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json("true");
    }
}
