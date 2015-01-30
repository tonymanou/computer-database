package com.tonymanou.computerdb.mapper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface IEntityMapper<U, V> {

  /**
   * Map a list of objects to a list of their DTO representation.
   * 
   * @param listObject
   *          The list of objects to map.
   * @return A list of DTO corresponding to the provided objects.
   */
  default List<V> toDTOList(List<U> listObject) {
    if (listObject == null) {
      return null;
    }

    Stream<U> s = listObject.stream();
    Stream<V> x = s.map(e -> toDTO(e));
    return x.collect(Collectors.toList());
  }

  /**
   * Map a list of DTOs to a list of their original object representation.
   * 
   * @param listDTO
   *          The list of DTO to map.
   * @return A list of objects corresponding to the provided DTOs.
   */
  default List<U> fromDTOList(List<V> listDTO) {
    if (listDTO == null) {
      return null;
    }

    Stream<V> s = listDTO.stream();
    Stream<U> x = s.map(e -> fromDTO(e));
    return x.collect(Collectors.toList());
  }

  /**
   * Map an object to its DTO representation.
   * 
   * @param object
   *          The object to map.
   * @return A DTO instance corresponding to the provided object.
   */
  V toDTO(U object);

  /**
   * Map an object to its DTO representation and update the given DTO.
   * 
   * @param dto
   *          The DTO to update.
   * @param object
   *          The object to map.
   */
  void updateDTO(V dto, U object);

  /**
   * Map a DTO object to its original object representation.
   * 
   * @param dto
   *          The DTO object to map.
   * @return An object instance corresponding to the provided DTO.
   */
  U fromDTO(V dto);
}
