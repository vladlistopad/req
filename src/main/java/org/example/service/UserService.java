package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.constant.RequestStatus;
import org.example.constant.UserStatus;
import org.example.dao.model.Request;
import org.example.dao.model.User;
import org.example.dao.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserHelper userHelper;

    @Transactional
    public void reassignRequests() {
        List<User> users = userRepository.findAll();

        users.forEach(user -> {
            if (user.getStatus() == UserStatus.OFFLINE) {
                List<Request> openRequests = user.getAssignedRequests().stream()
                        .filter(request -> request.getStatus() == RequestStatus.OPEN)
                        .collect(Collectors.toList());
                // Переназначаем каждый запрос на пользователя в статусе ONLINE с наименьшим количеством открытых запросов
                extractedRequests(users, openRequests);
            }
        });
        log.info("Запрос переназначен на пользователя с минимальным количеством открытых запросов");
    }

    private void extractedRequests(List<User> users, List<Request> openRequests) {
        openRequests.forEach(request -> {
            Optional<User> nextUserOptional = getRandomUser(users);
            nextUserOptional.ifPresent(request::setResponsibleUser);
        });
    }

    private Optional<User> getRandomUser(List<User> users) {
        Optional<User> userWithoutOpenRequests = userHelper.getUserWithoutOpenRequests(users);

        if (userWithoutOpenRequests.isPresent()) {
            return userWithoutOpenRequests;
        }
        return userHelper.getUserWithLeastOpenRequests(users);
    }
}