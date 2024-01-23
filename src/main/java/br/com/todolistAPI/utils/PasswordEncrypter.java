package br.com.todolistAPI.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordEncrypter {
    public static String encryptPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
