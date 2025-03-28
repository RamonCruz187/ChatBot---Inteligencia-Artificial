package com.chatbot.test.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 3000)
    private String information;

//    @OneToMany(targetEntity = Query.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "business")
//    private List<Query> queries;

    @OneToMany(targetEntity = Chat.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "business")
    private List<Chat> chats;
}
