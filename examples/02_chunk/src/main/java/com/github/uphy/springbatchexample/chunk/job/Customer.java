package com.github.uphy.springbatchexample.chunk.job;

import java.util.Date;

import lombok.Value;

@Value
public class Customer {
    long id;
    String firstName;
    String lastName;
    Date birthdate;
}
