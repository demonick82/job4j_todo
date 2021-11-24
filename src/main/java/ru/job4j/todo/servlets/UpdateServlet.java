package ru.job4j.todo.servlets;

import ru.job4j.todo.store.HBMStore;
import ru.job4j.todo.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Store store = HBMStore.instOf();
        int id = Integer.parseInt(req.getParameter("id"));
        System.out.println(id);
        store.updateItem(id);
    }
}
