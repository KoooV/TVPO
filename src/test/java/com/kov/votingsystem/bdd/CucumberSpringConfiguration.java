package com.kov.votingsystem.bdd;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import com.kov.votingsystem.VotingSystemApplication;

//Spring конфигурация для Cucumber BDD тестов.
@CucumberContextConfiguration
@SpringBootTest(classes = VotingSystemApplication.class)
public class CucumberSpringConfiguration {
}

