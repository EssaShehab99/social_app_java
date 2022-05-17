package com.example.it342_project.models;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Post {

    public String id;
    public String text;
    public String email;

    public Post(String id, String text, String email) {
        this.id = id;
        this.text = text;
        this.email = email;
    }


  static   public Post fromMap(Map<String, Object> map, String id) throws ParseException {
        return new Post(
                id,
                Objects.requireNonNull(map.getOrDefault("text", "")).toString(),
                Objects.requireNonNull(map.getOrDefault("email", "")).toString()
                );
    }

    public final Map<String, Object> toMap() {
        return new HashMap<String, Object>() {{
            put("text", text);
            put("email", email);
        }};
    }
}