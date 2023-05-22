package com.riot.manager.events;

import com.riot.manager.entity.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserRegistrationEvent {
    private final User user;

    public User getUser() {
        return user;
    }
}
