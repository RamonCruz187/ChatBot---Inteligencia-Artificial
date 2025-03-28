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
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chatId;

    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business business;

    @OneToMany(targetEntity = Query.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "chat")
    private List<Query> queries;

    public void addQuery(Query query) {
        if (queries == null) {
            queries = new java.util.ArrayList<>();
        }
        queries.add(query);
        query.setChat(this);
    }

}
