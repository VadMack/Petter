package com.vadmack.petter.app.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MappingConfig {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  // Right solution, but there is unresolved bug in modelmapper: https://github.com/modelmapper/modelmapper/issues/684
  /*@PostConstruct
  public void init() {
    PropertyMap<Ad, AdGetListDto> adToAdGetListDto = new PropertyMap<>() {
      protected void configure() {
        map(source.getAchievements()).setHasAchievements(!source.getAchievements().isEmpty());
      }
    };

    modelMapper().addMappings(adToAdGetListDto);
  }*/
}
