package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.domain.User;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class UserDto {

    private String name;

    private boolean enabled;

    public static UserDto userToDto(User user) {
        return new UserDto(user.getUsername(), user.isEnabled());
    }

    public static List<UserDto> userListToDtoList(List<User> users) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (var user : users) {
            userDtoList.add(userToDto(user));
        }
        return userDtoList;
    }
}
