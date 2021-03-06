package edu.usc.csci.boneapptheteeth.mvc;

import edu.usc.csci.boneapptheteeth.model.UserInfo;
import edu.usc.csci.boneapptheteeth.model.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@Controller    // This means that this class is a Controller
@RequestMapping(path = "/user") // This means URL's start with /user (after Application path)
public class UserInfoController {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @PostMapping(path = "/add") // Map ONLY POST Requests
    public @ResponseBody
    String addNewUser(@RequestParam String email, @RequestParam String name, @RequestParam String pw) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Optional<UserInfo> resultFromDB = userInfoRepository.findById(email);
        if (resultFromDB.isPresent()) {
            return "User already exists";
        }

        UserInfo userInfo = new UserInfo(email, name, pw);
        userInfoRepository.save(userInfo);
        return "Saved New User";
    }

    @PostMapping(path = "/update") // Map ONLY POST Requests
    public @ResponseBody
    String updateUserInfo(@RequestParam String email, @RequestParam String name, @RequestParam String pw) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Optional<UserInfo> resultFromDB = userInfoRepository.findById(email);
        if (!resultFromDB.isPresent()) {
            return "User does not exist";
        }

        UserInfo user = resultFromDB.get();
        user.setUserName(name);
        user.setPassword(pw);
        userInfoRepository.save(user);
        return "Update the info for User";
    }

    @RequestMapping(path = "/search")
    public @ResponseBody
    Iterable<UserInfo> getUsers(@RequestParam String email) {
        // This returns a JSON or XML with the users

        Optional<UserInfo> userInfo = userInfoRepository.findById(email);

        return userInfo.map(Collections::singleton)
                .orElseGet(Collections::emptySet);
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<UserInfo> getAllUsers() {
        // This returns a JSON or XML with the users
        return userInfoRepository.findAll();
    }


    public boolean isUserExist(String email) {
        return userInfoRepository.findById(email).isPresent();
    }
}
