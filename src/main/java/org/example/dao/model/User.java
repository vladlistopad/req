package org.example.dao.model;

import lombok.Data;
import org.example.constant.UserStatus;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "user", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "full_name")
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;

    @OneToMany(mappedBy = "responsibleUser")
    private List<Request> assignedRequests;
}
