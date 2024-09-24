package org.example;

import org.example.homework2.Car;
import org.example.homework2.DriveLicense;
import org.example.homework2.Owner;
import org.example.homework2.Type;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();

        Metadata metadata = new MetadataSources(serviceRegistry)
                .addAnnotatedClass(Owner.class)
                .addAnnotatedClass(DriveLicense.class)
                .addAnnotatedClass(Car.class)
                .getMetadataBuilder()
                .build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Owner owner1 = new Owner("Alexander", new DriveLicense("abc894130163"));
        Owner owner2 = new Owner("Mary", new DriveLicense("hbd890352114"));
        Owner owner3 = new Owner("John", Arrays.asList(new Car("Tesla Model S", Type.Sedan, 670, 79999, 2022)), new DriveLicense("mkl089243511"));
        session.persist(owner1);
        session.persist(owner2);
        session.persist(owner3);
        Car car1 = new Car("Volkswagen Golf", Type.Hatchback, 288, 30000, 2020);
        Car car2 = new Car("BMW M4", Type.Coupe, 503, 72000, 2023);
        Car car3 = new Car("Nissan Qashqai",Type.Crossover, 187, 27000, 2019);

        owner2.setCars(Arrays.asList(car1, car2));
        owner1.setCars(Arrays.asList(car3));
        session.getTransaction().commit();
        session.createQuery("select o from Owner o", Owner.class).list().forEach(owner->System.out.println(owner.getCars()));
        session.createQuery("select o from Owner o", Owner.class).list().forEach(owner->System.out.println(owner.getDriveLicense()));
        session.close();
        sessionFactory.close();
    }
}