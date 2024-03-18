package org.example.dao.model;

import lombok.Data;
import org.example.constant.RequestStatus;

import javax.persistence.*;

@Entity
@Data
@Table(name = "request", schema = "public")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "responsible_user_id")
    private User responsibleUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RequestStatus status;
}