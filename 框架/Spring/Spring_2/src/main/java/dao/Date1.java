package dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Date;
//@Configuration
//@ComponentScan
//@Component
@Repository
public class Date1 {
    @Bean(name = "date")
    public Date date() {
        return new Date();
    }
}
