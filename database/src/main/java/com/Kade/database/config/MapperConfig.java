package com.Kade.database.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
*Configuration class for setting up the ModelMapper bean.
*This is meant to use the loose matching strategy which will be flexible in the case that a field name doesnt match exactly.
*/
@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper(){                // Creates and configures a ModelMapper bean
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()               // Returns a ModelMapper instance with loose matching
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }
}
