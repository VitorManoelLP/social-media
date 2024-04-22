package com.socialmedia.main.util;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@WithSecurityContext(factory = WithKeycloakMockUserFactory.class)
public @interface WithKeycloakMockUser {
    String principal() default "c3dfaaae-e871-43c9-9d17-056d23e5e8c2";
}
