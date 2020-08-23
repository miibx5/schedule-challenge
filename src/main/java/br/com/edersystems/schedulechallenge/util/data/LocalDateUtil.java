/*
Project .....................: schedule-challenge
Creation Date ...............: 23/08/2020 14:37:42
Developer....................: ederluciano
Copyright....................: 2020
Codification.................: UTF-8
...........................................................
 Éder L. Costa - © Copyright 2020 - All Rights Reserved
...........................................................
*/
package br.com.edersystems.schedulechallenge.util.data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class LocalDateUtil {

    public LocalDate convertStringToDate(String dateToConvert) {
        if(Objects.isNull(dateToConvert) || dateToConvert.isBlank()) {
            return null;
        }
        return LocalDate.parse(dateToConvert, DateTimeFormatter.ISO_DATE);
    }

    public LocalDate getToday() {
        return LocalDate.now(ZoneId.of("America/Sao_Paulo"));
    }

    public LocalDate getTomorrow() {
        return getToday().plusDays(BigDecimal.ONE.intValue());
    }

    public Boolean isDateIsLessThanTomorrow(LocalDate date) {
        return (getTomorrow().isAfter(date) || getToday().isEqual(date));
    }
}
