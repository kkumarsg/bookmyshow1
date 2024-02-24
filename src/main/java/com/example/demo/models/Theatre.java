package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Scanner;

@Getter
@Setter
@Entity
public class Theatre extends BaseModel{

    private String name;
    @ManyToOne
    private Region region;
    @OneToMany
    private List<Screen> screenList;
}
