package org.example.data.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Getter @AllArgsConstructor @NoArgsConstructor
public class User {
    @Id
    private String userId;
    @Setter
    private String email;
    @Setter
    private String firstName;
    @Setter
    private String lastName;
    @Setter
    private String password;

}
