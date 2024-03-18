package org.example.service;

import org.example.constant.RequestStatus;
import org.example.dao.model.User;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserHelper {

    public Optional<User> getUserWithoutOpenRequests(List<User> users) {
        return users.stream()
                .filter(user -> user.getAssignedRequests().stream()
                        .noneMatch(request -> request.getStatus() == RequestStatus.OPEN))
                .findFirst();
    }

    public Optional<User> getUserWithLeastOpenRequests(List<User> users) {
        return users.stream()
                .filter(user -> user.getAssignedRequests().stream()
                        .anyMatch(request -> request.getStatus() == RequestStatus.OPEN))
                .min(Comparator.comparing(user -> user.getAssignedRequests().size()));
    }
}
