package com.example.allgoods;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserUnitTest {

    @Test
    public void testValidUserCreation() {
        User user = new User(1, "validUser", "Valid@Password123");
        assertEquals("validUser", user.getUsername());
        assertEquals(1, user.getId());
        assertTrue(user.getPassword().matches("Valid@Password123"));
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidUsername() {
        new User(1, "invalid User", "Valid@Password123");
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidPassword() {
        new User(1, "validUser", "invalidpassword");
    }

    @Test(expected = RuntimeException.class)
    public void testChangeUsername() {
        User user = new User(1, "validUser", "Valid@Password123");
        user.changeUsername("1", "newUsername");
    }

    @Test
    public void testChangePassword() {
        User user = new User(1, "validUser", "Valid@Password123");
        user.changePassword("Valid@Password123", "New@Password123", "New@Password123");
        assertEquals("New@Password123", user.getPassword());
    }

    @Test(expected = RuntimeException.class)
    public void testIncorrectOldPassword() {
        User user = new User(1, "validUser", "Valid@Password123");
        user.changePassword("InvalidOldPassword", "New@Password123", "New@Password123");
    }

    @Test(expected = RuntimeException.class)
    public void testMismatchedPasswords() {
        User user = new User(1, "validUser", "Valid@Password123");
        user.changePassword("Valid@Password123", "New@Password123", "MismatchedPassword");
    }
}
