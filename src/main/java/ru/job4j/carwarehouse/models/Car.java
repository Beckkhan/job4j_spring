package ru.job4j.carwarehouse.models;

import javax.persistence.*;
import java.sql.Date;
import java.util.*;

/**
 * @author Khan Vyacheslav (mailto: beckkhan@mail.ru)
 * @version 1.0
 * @since 07.08.2019
 */

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "brand")
    private String brand;

    @ManyToOne
    @JoinColumn(name = "bodytype_id")
    private Bodytype bodytype;

    @ManyToOne
    @JoinColumn(name = "engine_id")
    private Engine engine;

    @ManyToOne
    @JoinColumn(name = "transmission_id")
    private Transmission transmission;

    @Column(name = "price")
    private int price;

    @Column(name = "sold")
    private boolean sold;

    @Column(name = "location")
    private String location;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created")
    private Date created;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "car",
            cascade = {CascadeType.ALL})
    private List<Picture> pictures;

    public Car() {
    }

    public Car(String brand) {
        this.brand = brand;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Bodytype getBodytype() {
        return bodytype;
    }

    public void setBodytype(Bodytype bodytype) {
        this.bodytype = bodytype;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public boolean isSold() {
        return sold;
    }
    public void setSold(boolean sold) {
        this.sold = sold;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public List<Picture> getPictures() {
        return pictures;
    }
    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public void addImages(Picture picture) {
        if (pictures == null) {
            pictures = new ArrayList<>();
        }
        pictures.add(picture);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return id == car.id
                && price == car.price
                && sold == car.sold
                && Objects.equals(brand, car.brand)
                && Objects.equals(bodytype, car.bodytype)
                && Objects.equals(engine, car.engine)
                && Objects.equals(transmission, car.transmission)
                && Objects.equals(location, car.location)
                && Objects.equals(user, car.user)
                && Objects.equals(pictures, car.pictures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id, brand, bodytype, engine, transmission, price, sold, location, user, pictures
        );
    }

    @Override
    public String toString() {
        return "Car{"
                + "id=" + id
                + ", brand='" + brand + '\''
                + ", bodytype=" + bodytype
                + ", engine=" + engine
                + ", transmission=" + transmission
                + ", price=" + price
                + ", sold=" + sold
                + ", location='" + location + '\''
                + ", user=" + user
                + '}';
    }
}