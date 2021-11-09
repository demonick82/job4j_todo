package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.todo.model.Item;

import java.util.Collection;
import java.util.function.Function;

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
        return this.tx(session -> session.createQuery("from Item").list());
    }

    @Override
    public Collection<Item> findAllUnCheckedItems() {
        return this.tx(
                session ->  session.createQuery("from Item where done=false").list()
        );
    }

    @Override
    public void saveItem(Item item) {
        this.tx(session -> session.save(item));
    }

    @Override
    public void updateItem(int id) {
        Item item = searchItemForId(id);
        boolean done = !item.isDone();
        this.tx(session ->
                session.createQuery("update Item set done=:done where id=:id")
                        .setParameter("id", id)
                        .setParameter("done", done)
                        .executeUpdate());
    }

    private Item searchItemForId(int id) {
        return this.tx(
                session -> {
                    final Query query = session.createQuery("from Item where id=:id")
                            .setParameter("id", id);
                    return (Item) query.uniqueResult();
                }
        );
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    public static void main(String[] args) {
        Store store = HBMStore.instOf();
        store.saveItem(new Item("Поесть колбасы"));
        store.saveItem(new Item("Убрать квартиру"));
        store.updateItem(50);
        store.findAllItems().forEach(System.out::println);
        store.findAllUnCheckedItems().forEach(System.out::println);
    }
}
