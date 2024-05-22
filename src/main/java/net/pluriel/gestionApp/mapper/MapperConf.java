package net.pluriel.gestionApp.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MapperConf {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
