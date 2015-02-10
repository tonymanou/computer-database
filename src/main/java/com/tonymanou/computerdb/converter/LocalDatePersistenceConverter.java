package com.tonymanou.computerdb.converter;

import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Class helper to convert LocalDate to Timestamp and conversely.
 * 
 * @author tonymanou
 */
@Converter
public class LocalDatePersistenceConverter implements AttributeConverter<LocalDate, Timestamp> {

  @Override
  public Timestamp convertToDatabaseColumn(LocalDate attribute) {
    Timestamp result;
    if (attribute == null) {
      result = null;
    } else {
      result = Timestamp.valueOf(attribute.atStartOfDay());
    }
    return result;
  }

  @Override
  public LocalDate convertToEntityAttribute(Timestamp dbData) {
    LocalDate result;
    if (dbData == null) {
      result = null;
    } else {
      result = dbData.toLocalDateTime().toLocalDate();
    }
    return result;
  }
}
