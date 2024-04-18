package com.auth.service.fixture;

import com.auth.service.domain.UserSignUp;

public final class UserFixture {

    private UserFixture() {}

    public static UserSignUp createUserSignUp() {
        return new UserSignUp(
                "foo",
                "foo@gmail.com",
                "pass123",
                "pass123"
        );
    }

}
