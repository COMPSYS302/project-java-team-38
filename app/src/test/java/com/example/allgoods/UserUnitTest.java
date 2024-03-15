package com.example.allgoods;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserUnitTest {

    @Test
    public void testValidUserCreation() {
        User user = new User(1, "validUser", "Valid@Password123", "valid@example.com");
        assertEquals("validUser", user.getUsername());
        assertEquals(1, user.getId());
        assertEquals("valid@example.com", user.getEmail());
        assertTrue(user.getPassword().matches("Valid@Password123"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidUsername() {
        new User(1, "invalid User", "Valid@Password123", "valid@example.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPassword() {
        new User(1, "validUser", "invalidpassword", "valid@example.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEmail() {
        new User(1, "validUser", "Valid@Password123", "invalidemail");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullEmail() {
        new User(1, "validUser", "Valid@Password123", null);
    }

    @Test
    public void testChangeUsername() {
        User user = new User(1, "validUser", "Valid@Password123", "valid@example.com");
        user.changeUsername("newUsername", "validUser");
        assertEquals("newUsername", user.getUsername());
    }

    @Test
    public void testChangePassword() {
        User user = new User(1, "validUser", "Valid@Password123", "valid@example.com");
        user.changePassword("Valid@Password123", "New@Password123", "New@Password123");
        assertEquals("New@Password123", user.getPassword());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectOldPassword() {
        User user = new User(1, "validUser", "Valid@Password123", "valid@example.com");
        user.changePassword("InvalidOldPassword", "New@Password123", "New@Password123");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMismatchedPasswords() {
        User user = new User(1, "validUser", "Valid@Password123", "valid@example.com");
        user.changePassword("Valid@Password123", "New@Password123", "MismatchedPassword");
    }
    @Test
    public void testMultipleUsersCreation() {
        User user1 = new User(1, "user1", "Password123", "user1@example.com");
        User user2 = new User(2, "user2", "Password456", "user2@example.com");

        // Test user1 attributes
        assertEquals(1, user1.getId());
        assertEquals("user1", user1.getUsername());
        assertEquals("user1@example.com", user1.getEmail());
        assertTrue(user1.getPassword().matches("Password123"));

        // Test user2 attributes
        assertEquals(2, user2.getId());
        assertEquals("user2", user2.getUsername());
        assertEquals("user2@example.com", user2.getEmail());
        assertTrue(user2.getPassword().matches("Password456"));
    }

    @Test
    public void testChangeUsernameMultipleUsers() {
        User user1 = new User(1, "user1", "Password123", "user1@example.com");
        User user2 = new User(2, "user2", "Password456", "user2@example.com");

        user1.changeUsername("newUser1", "user1");
        user2.changeUsername("newUser2", "user2");

        // Test user1 username change
        assertEquals("newUser1", user1.getUsername());

        // Test user2 username change
        assertEquals("newUser2", user2.getUsername());
    }

    @Test
    public void testChangePasswordMultipleUsers() {
        User user1 = new User(1, "user1", "Password123", "user1@example.com");
        User user2 = new User(2, "user2", "Password456", "user2@example.com");

        user1.changePassword("Password123", "NewPassword123", "NewPassword123");
        user2.changePassword("Password456", "NewPassword456", "NewPassword456");

        // Test user1 password change
        assertEquals("NewPassword123", user1.getPassword());

        // Test user2 password change
        assertEquals("NewPassword456", user2.getPassword());
    }

}
