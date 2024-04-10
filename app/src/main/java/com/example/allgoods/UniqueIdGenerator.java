package com.example.allgoods;

import java.util.UUID;

public class UniqueIdGenerator {

    public static String generateUniqueId() {
        // Generate a random UUID
        UUID uniqueKey = UUID.randomUUID();
        return uniqueKey.toString();
    }
}
