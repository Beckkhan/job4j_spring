package ru.job4j.carwarehouse.storage;

import ru.job4j.carwarehouse.models.Picture;
import java.util.List;
import static ru.job4j.carwarehouse.storage.Wrapper.tx;

/**
 * @author Khan Vyacheslav (mailto: beckkhan@mail.ru)
 * @version 1.0
 * @since 07.08.2019
 */
public class PictureStore implements Store<Picture> {

    private static PictureStore instance = new PictureStore();

    public static PictureStore getInstance() {
        return instance;
    }

    private PictureStore() {
    }

    @Override
    public int add(Picture picture) {
        return tx(session -> {
            session.save(picture); return picture.getId();
        });
    }

    @Override
    public int update(Picture picture) {
        return tx(session -> {
            session.update(picture); return picture.getId();
        });
    }

    @Override
    public int delete(int id) {
        return tx(session -> {
            Picture picture = session.get(Picture.class, id);
            session.delete(picture);
            return picture.getId();
        });
    }

    @Override
    public List<Picture> getAll() {
        return tx(session -> session.createQuery("from Picture ", Picture.class).list());
    }

    @Override
    public Picture getById(int id) {
        return tx(session -> session.get(Picture.class, id));
    }

    @Override
    public Picture getByName(String name) {
        return null;
    }

    public List<Picture> getImagesByCarId(int carId) {
        return tx(session ->
                session.createQuery("select P from Picture P where P.car.id = : carId", Picture.class)
                        .setParameter("carId", carId).getResultList());
    }
}