package com.riot.manager.events;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ManagerEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public void publishEvent(UserRegistrationEvent event) {
        eventPublisher.publishEvent(event);
    }
}
