package itu.matelas.demo.affichage.home;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, HttpServletRequest req) {
        String referer = req.getHeader("Referer");
        String erreur = e.getMessage();

        return "redirect:" + referer + "?alert=" + erreur;
    }
}
