package com.softserve.teamproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.softserve.teamproject.entity.Location;
import com.softserve.teamproject.entity.Role;
import com.softserve.teamproject.entity.User;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserDto {

    public static final String PATH = System.getProperty("user.dir")
            + "/src/main/resources/images/";

    private UserDto() {
    }

    public static User setImageAsBase64(User user) {
        ImageMaster master = new ImageMaster();
        Path path = Paths.get(PATH + user.getImage());
        String imageBase64 = master.encodeImage(path);
        User retUser = cloneUser(user);
        retUser.setImage(imageBase64);
        return retUser;
    }

    private static User cloneUser(User user) {
        User retUser = new User();
        retUser.setId(user.getId());
        retUser.setFirstName(user.getFirstName());
        retUser.setLastName(user.getLastName());
        retUser.setLocation(user.getLocation());
        retUser.setRole(user.getRole());
        retUser.setNickName(user.getNickName());
        retUser.setPassword(user.getPassword());
        return retUser;
    }
}
