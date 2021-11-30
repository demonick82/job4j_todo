package ru.job4j.todo.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.todo.store.HBMStore;
import ru.job4j.todo.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CategoriesServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=utf-8");
        Store store = HBMStore.instOf();
        OutputStream output = resp.getOutputStream();
        String json = GSON.toJson(store.findAllCategories());
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }
}
