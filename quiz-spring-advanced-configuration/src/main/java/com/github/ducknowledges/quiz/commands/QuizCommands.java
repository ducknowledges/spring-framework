package com.github.ducknowledges.quiz.commands;

import com.github.ducknowledges.quiz.domain.User;
import com.github.ducknowledges.quiz.service.LoginService;
import com.github.ducknowledges.quiz.service.MessageService;
import com.github.ducknowledges.quiz.service.QuizManagerService;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class QuizCommands {

    private final LoginService loginService;
    private final QuizManagerService quizManagerService;
    private final MessageService messageService;

    public QuizCommands(LoginService loginService,
                        QuizManagerService managerService,
                        MessageService messageService) {
        this.loginService = loginService;
        this.quizManagerService = managerService;
        this.messageService = messageService;
    }

    @ShellMethod(value = "Quiz login command", key = "login")
    @ShellMethodAvailability(value = "isLoginCommandAvailable")
    public String login() {
        User user = loginService.login();
        return messageService.getMessage("login.greet", new String[] {user.getFullName()});
    }

    @ShellMethod(value = "Quiz logout command", key = "logout")
    @ShellMethodAvailability(value = "isLogoutCommandAvailable")
    public void logout() {
        loginService.logout();
    }

    @ShellMethod(value = "Quiz run command", key = "run")
    @ShellMethodAvailability(value = "isRunCommandAvailable")
    public void run() {
        User user = loginService.getLoggedUser().orElse(new User());
        quizManagerService.run(user);
    }

    private Availability isLoginCommandAvailable() {
        String reason = messageService.getMessage("login.unavailable");
        return loginService.isLogged()
            ? Availability.unavailable(reason) : Availability.available();
    }

    private Availability isLogoutCommandAvailable() {
        String reason = messageService.getMessage("logout.unavailable");
        return loginService.isLogged()
            ? Availability.available() : Availability.unavailable(reason);
    }

    private Availability isRunCommandAvailable() {
        String reason = messageService.getMessage("run.unavailable");
        return loginService.isLogged()
            ? Availability.available() : Availability.unavailable(reason);
    }
}
