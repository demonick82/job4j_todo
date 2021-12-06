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

public class RegServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        Store store = HBMStore.instOf();
        PrintWriter writer = resp.getWriter();
        String name = req.getParameter("userName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User tmpUser = store.findUserByEmail(email);
        if (tmpUser == null) {
            User newUser = User.of(name, email, password);
            store.saveUser(newUser);
            HttpSession sc = req.getSession();
            sc.setAttribute("user", newUser);
            writer.print("201 user create");
        } else {
            writer.print("409 email already exists");

        }
    }
}
