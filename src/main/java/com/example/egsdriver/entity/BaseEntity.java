package com.example.egsdriver.entity;

import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OrderBy;
import java.io.Serializable;

@MappedSuperclass
public class BaseEntity<T extends Serializable> implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OrderBy
    private T id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", nullable = false)
    private String phone;


    @Column(name = "drivers_teams_contact")
    private String driversTeamsContact;

    public BaseEntity() {
    }

    public BaseEntity(T id, @NonNull String name, @NonNull String phone, @NonNull String driversTeamsContact) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.driversTeamsContact = driversTeamsContact;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getPhone() {
        return phone;
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone;
    }

    @NonNull
    public String getDriversTeamsContact() {
        return driversTeamsContact;
    }

    public void setDriversTeamsContact(@NonNull String driversTeamsContact) {
        this.driversTeamsContact = driversTeamsContact;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", driversTeamsContact='" + driversTeamsContact + '\'' +
                '}';
    }
}
