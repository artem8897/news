package com.epam.lab.controller;

import com.epam.lab.dto.entity.UserDto;
import com.epam.lab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

import static com.epam.lab.controller.ControllerMessage.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("users")
@Validated
public class UserController {

    private UserService userService;
    private static final String REG_EX_NAME = "[A-Za-z]{3,30}";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}/{password}")
    public ResponseEntity<Boolean> checkUser(@PathVariable String username, @PathVariable String password) {
        if (username.equals(USERNAME) && password.equals(PASSWORD)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> findUser(@PathVariable("name") @NotNull(message = NAME_NULL_MESSAGE) @Pattern(regexp = REG_EX_NAME, message = WRONG_NAME) String name) {
        UserDto userDto = new UserDto();
        userDto.setName(name);
        return userService.findByName(userDto);
    }

    @PostMapping(headers = {"Content-type=application/json"})
    public UserDto create(@Validated @RequestBody UserDto user) {
        return userService.add(user);

    }

    @PostMapping(value = "/{id}", headers = {"Content-type=application/json"})
    public UserDto update(@RequestBody @Valid UserDto user, @PathVariable @NotNull(message = ID_NULL_MESSAGE) @Min(value = 1, message = WRONG_ID_MESSAGE) Long id) {
        user.setId(id);
        return userService.update(user);

    }

    @DeleteMapping(value = "/{id}", headers = {"Content-type=application/json"})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity delete(@PathVariable @NotNull(message = ID_NULL_MESSAGE) @Min(value = 1, message = WRONG_ID_MESSAGE) Long id) {
        UserDto user = new UserDto();
        user.setId(id);
        userService.delete(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(value = "/count")
    public long getCountOFNews() {
        return userService.findCount();
    }
}