package com.example.it342_project.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class User {
  public String id;
  public String email;
  public String password;

    public User(String id, String email,String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public User() {

    }

    public User fromMap(Map<String, Object> map, String id) {
        return new User(
                id,
                Objects.requireNonNull(map.getOrDefault("email","")).toString(),
                ""
        );
    }

    public final Map<String, Object> toMap() {
        return new HashMap<String, Object>() {{
            put("email", email);
            put("password", password);
        }};
    }
}
