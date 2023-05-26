package com.example.practicabitboxer2.utils;

import java.util.List;

public interface Converter<E, D> {

    D entityToDto(E entity);
    E dtoToEntity(D dto);
    List<D> entitiesToDtos(List<E> entities);
    List<E> dtosToEntities(List<D> dtos);
}
