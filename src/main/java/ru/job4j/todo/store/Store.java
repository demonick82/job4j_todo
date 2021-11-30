package ru.job4j.todo.store;

import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.util.Collection;

public interface Store {

    Collection<Item> findAllItems();

    Collection<Item> findAllUnCheckedItems();

    Collection<Category> findAllCategories();


    void saveItem(Item item, String[] categories);

    void updateItem(int id);

    void saveUser(User user);

    User findUserByEmail(String s);

    void deleteItem(int id);

}
