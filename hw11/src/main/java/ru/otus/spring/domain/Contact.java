package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document("contact")
public class Contact {


    @Id
    private String id;

    private String name;

    private String phone;

    public Contact(String name, String phone) {
        this.id = null;
        this.name = name;
        this.phone = phone;
    }
}
