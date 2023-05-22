package com.riot.manager.events;

import com.riot.manager.entity.User;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ManagerEventListener {
    @EventListener
    public void userRegistrationEvenHandler(UserRegistrationEvent event) {
        //User user = event.getUser();
        //TODO: Actually two variants
        //      1. Show new users on main page of the application
        //      2. Send some notification on telegram/discord once new user got registered
    }
}
