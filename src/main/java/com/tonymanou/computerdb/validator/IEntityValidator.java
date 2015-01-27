package com.tonymanou.computerdb.validator;

import java.util.Map;

public interface IEntityValidator<T> {

  /**
   * Validate the given entity.
   * 
   * @param entity
   *          The entity to validate.
   * @param errors
   *          The map where error messages are added.
   * @return true if the entity is valid, false otherwise.
   */
  boolean validate(T entity, Map<String, String> errors);
}
