package johnatanSSP.user_InGress.controller;

import johnatanSSP.user_InGress.domain.UserModel;
import johnatanSSP.user_InGress.dto.UserDto;
import johnatanSSP.user_InGress.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    @Autowired
    final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/user")
    public ResponseEntity<UserModel> createUser(@RequestBody UserDto userDto){
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto,userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveAndPublish((userModel)));
    }

}
