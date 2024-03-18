package org.example.service.scheduled;

import lombok.RequiredArgsConstructor;
import org.example.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestReassignmentScheduler {

    private final UserService userService;

    @Scheduled(fixedRate = 60000) // Выполнять каждую минуту
    public void reassignRequests() {
        userService.reassignRequests();
    }
}
