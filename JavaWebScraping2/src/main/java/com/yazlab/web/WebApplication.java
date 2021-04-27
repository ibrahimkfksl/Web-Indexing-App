package com.yazlab.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@SpringBootApplication
public class WebApplication {

    public static void main(String[] args) throws FileNotFoundException {
        SpringApplication.run(WebApplication.class, args);
    }

}
