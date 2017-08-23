package com.softserve.teamproject.dto;

import org.apache.tomcat.util.codec.binary.Base64;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ImageMaster {
    public String encodeImage(Path path) {
        byte[] codedFile = null;
        try {
            codedFile = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String ret = encodeBytes(codedFile);
        return refactorBase64(ret);
    }

    private String encodeBytes(byte[] bytes) {
        Base64 base64 = new Base64(false);
        return base64.encodeAsString(bytes);
    }

    private String refactorBase64(String base) {
        return base.replaceAll("\r|\n|\r\n", "");
    }
}

