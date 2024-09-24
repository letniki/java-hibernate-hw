package org.example;

import org.example.homework1.Car;
import org.example.homework1.Type;
import org.example.homework1.Word;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();

        Metadata metadata = new MetadataSources(serviceRegistry)
                .addAnnotatedClass(Word.class)
                .addAnnotatedClass(Car.class)
                .getMetadataBuilder()
                .build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Word word = new Word("sun");
        Word word1 = new Word("house");
        Word word2 = new Word("book");
        Word word3 = new Word("lake");
        session.persist(word);
        session.persist(word1);
        session.persist(word2);
        session.persist(word3);
        List<Word> list = session.createQuery("select w from Word w", Word.class).list();
        System.out.println(list);
        session.getTransaction().commit();
        session.beginTransaction();
        Car car = new Car( "Tesla Model S", Type.Sedan, 670, 79999, 2022);
        Car car1 = new Car("Volkswagen Golf", Type.Hatchback, 288, 30000, 2020);
        Car car2 = new Car("BMW M4", Type.Coupe, 503, 72000, 2023);
        Car car3 = new Car();
        car3.setModel("Nissan Qashqai");
        car3.setType(Type.Crossover);
        car3.setPower(187);
        car3.setPrice(27000);
        car3.setYear(2019);
        session.persist(car);
        session.persist(car1);
        session.persist(car2);
        session.persist(car3);
        List<Car> cars = session.createQuery("select car from Car car", Car.class).list();
        System.out.println(cars);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }
}