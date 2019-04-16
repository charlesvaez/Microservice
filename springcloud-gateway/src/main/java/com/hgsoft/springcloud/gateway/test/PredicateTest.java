package com.hgsoft.springcloud.gateway.test;

import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class PredicateTest {
    public static void main(String[] args) {
        String content = "0x24040340";

        String pattern = "^0x[0-9]{6}3{1}([0-9]|[ABCDEF]){1}$";

        Predicate p = new Predicate() {
            @Override
            public boolean test(Object o) {
                return Pattern.matches(pattern,String.valueOf(o));
            }
        };

        System.out.println(p.test(content));

        PredicateDefinition pd = new PredicateDefinition();

    }
}
