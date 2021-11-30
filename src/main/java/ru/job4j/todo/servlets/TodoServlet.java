package ru.job4j.todo.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.HBMStore;
import ru.job4j.todo.store.Store;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class TodoServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean done = Boolean.parseBoolean(req.getParameter("done"));
        String json;
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        Store store = HBMStore.instOf();
        if (!done) {
            json = GSON.toJson(store.findAllUnCheckedItems());
        } else {
            json = GSON.toJson(store.findAllItems());
        }
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        User user = (User) req.getSession().getAttribute("user");
        Store store = HBMStore.instOf();
        String desc = req.getParameter("desc");
        String ids = req.getParameter("ids");
        String[] id = ids.split(",");
        store.saveItem(new Item(desc, user), id);
    }
}
