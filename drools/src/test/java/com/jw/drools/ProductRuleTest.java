package com.jw.drools;

import com.jw.drools.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import static org.junit.jupiter.api.Assertions.*;

public class ProductRuleTest {

    KieSession kieSession;

    @BeforeEach
    void setup() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        kieSession = kieContainer.newKieSession("product-rule");

    }

    @Test
    void testBagRule() {
        Product product = new Product();
        product.setType("bag");
        kieSession.insert(product);
        // 命中规则数量
        int hitRate = kieSession.fireAllRules();
        assertEquals(1, hitRate);
        assertEquals(5, product.getDiscount());
    }

    @Test
    void testPencilRule() {
        Product product = new Product();
        product.setType("pencil");
        kieSession.insert(product);
        int hitRate = kieSession.fireAllRules();
        assertEquals(1, hitRate);
        assertEquals(6, product.getDiscount());
    }
}
