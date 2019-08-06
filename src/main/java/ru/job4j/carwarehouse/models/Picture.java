package ru.job4j.carwarehouse.models;

import javax.persistence.*;
import java.util.*;

/**
 * @author Khan Vyacheslav (mailto: beckkhan@mail.ru)
 * @version 1.0
 * @since 07.08.2019
 */

@Entity
@Table(name = "pictures")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "picture")
    private byte[] picture;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @Transient
    private String base64picture;

    public Picture() {
    }

    public Picture(byte[] picture) {
        this.picture = picture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getBase64picture() {
        base64picture = Base64.getEncoder().encodeToString(this.picture);
        return base64picture;
    }

    public void setBase64picture(String base64picture) {
        this.base64picture = base64picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Picture picture1 = (Picture) o;
        return id == picture1.id
                && Arrays.equals(picture, picture1.picture)
                && Objects.equals(car, picture1.car);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, car);
        result = 31 * result + Arrays.hashCode(picture);
        return result;
    }
}