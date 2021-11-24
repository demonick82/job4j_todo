package ru.job4j.todo.servlets;

import ru.job4j.todo.model.User;
import ru.job4j.todo.store.HBMStore;
import ru.job4j.todo.store.Store;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        Store store = HBMStore.instOf();
        PrintWriter writer  = resp.getWriter();
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = store.findUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            HttpSession sc = req.getSession();
            sc.setAttribute("user", user);
            writer.print("202 password accepted");
        } else {
            writer.print("401 unauthorized");

        }
    }
}
