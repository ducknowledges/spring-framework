package com.github.ducknowledges.bookstore.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorRedirectController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        return "forward:/";
    }

}