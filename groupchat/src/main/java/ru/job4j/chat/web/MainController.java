package ru.job4j.chat.web;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.chat.domain.Message;
import ru.job4j.chat.domain.User;
import ru.job4j.chat.repository.MessageRepo;
import ru.job4j.chat.repository.UserRepo;
import java.util.List;

@Controller
public class MainController {

    private final UserRepo users = UserRepo.getInstance();

    private final MessageRepo messages = MessageRepo.getInstance();

    @GetMapping("/")
    public String greet() {
        return "greeting";
    }

    @GetMapping("/main")
    public String showMessages(Model model) {
        List<Message> list = messages.findAll();
        model.addAttribute("messages", list);
        return "main";
    }

    @PostMapping("/main")
    public String newMess(@RequestParam String text, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = null;
        if((!(auth instanceof AnonymousAuthenticationToken)) && auth != null) {
            userDetail = (UserDetails) auth.getPrincipal();
        }
        User in = users.findByLogin(userDetail.getUsername());
        Message mes = new Message(text, in);
        messages.add(mes);
        List<Message> list = messages.findAll();
        model.addAttribute("messages", list);
        return "main";
    }
}