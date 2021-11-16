package com.jw.basics.netty;

import org.junit.Test;

public class JsonTest {

    @Test
    public void testJson() {
        Entity entity = new Entity();
        entity.setName("2121");
        String json = JsonUtil.pojoToJson(entity);

        System.out.println(json);
        Entity toPojo = JsonUtil.jsonToPojo(json, Entity.class);
        System.out.println(toPojo);
    }
}
