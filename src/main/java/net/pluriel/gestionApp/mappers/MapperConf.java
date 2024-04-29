package net.pluriel.gestionApp.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MapperConf {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
