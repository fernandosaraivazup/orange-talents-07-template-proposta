package br.com.zupacademy.fpsaraiva.microservicepropostas.configs.criptography;

import org.apache.commons.codec.digest.DigestUtils;
import org.jasypt.util.text.AES256TextEncryptor;

public class Criptografia {

    private static Criptografia instance = null;

    private static String secret = "${jasypt.secret}";

    public static String encriptar(String documentoNaoEncriptado){
        AES256TextEncryptor encryptor = new AES256TextEncryptor();
        encryptor.setPassword(secret);
        String encrypted = encryptor.encrypt(documentoNaoEncriptado);
        return encrypted;
    }

    public static String decriptar(String documentoEnctriptado){
        AES256TextEncryptor encryptor = new AES256TextEncryptor();
        encryptor.setPassword(secret);
        return encryptor.decrypt(documentoEnctriptado);
    }

    public static String gerarHash(String documentoNaoEncriptado){
        StringBuilder builder = new StringBuilder();
        return DigestUtils.sha256Hex(builder.append(documentoNaoEncriptado).append(secret).toString());
    }

}
