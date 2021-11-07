package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Item;

import java.util.Collection;
import java.util.List;

public class HBMStore implements Store, AutoCloseable {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private static final class Lazy {
        private static final Store INST = new HBMStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Collection<Item> findAllItems() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Item> result = session.createQuery("from Item").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public Collection<Item> findAllUnCheckedItems() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Item> result = session.createQuery("from Item where done=false ").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public void saveItem(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateItem(int id) {
        Item item = searchItemForId(id);
        boolean done = !item.isDone();
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("update Item set done=:done where id=:id")
                .setParameter("id", id)
                .setParameter("done", done).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    private Item searchItemForId(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item result = session.get(Item.class, id);
        session.getTransaction().commit();
        session.close();
        return result;
    }


    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    public static void main(String[] args) {
        Store store = HBMStore.instOf();

        store.saveItem(new Item("Поесть колбасы"));
        store.saveItem(new Item("Поесть грудинки"));
        store.saveItem(new Item("Убрать квартиру"));
        store.updateItem(78);
        store.findAllItems().forEach(System.out::println);
        store.findAllUnCheckedItems().forEach(System.out::println);
    }
}
