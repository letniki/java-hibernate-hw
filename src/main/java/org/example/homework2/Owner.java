package org.example.homework2;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "owners")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name="owner_car",
            joinColumns =@JoinColumn(name="owner_id"),
            inverseJoinColumns =@JoinColumn(name = "car_id"))
    private List<Car> cars = new ArrayList<>();

    @OneToOne(cascade =CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "drive_license_id", referencedColumnName = "id")
    private DriveLicense driveLicense;

    public Owner(String name, DriveLicense driveLicense) {
        this.name = name;
        this.driveLicense = driveLicense;
    }

    public Owner(String name, List<Car> cars, DriveLicense driveLicense) {
        this.name = name;
        this.cars = cars;
        this.driveLicense = driveLicense;
    }
}
