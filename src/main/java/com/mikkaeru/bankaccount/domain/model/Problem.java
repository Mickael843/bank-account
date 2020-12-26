package com.mikkaeru.bankaccount.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class Problem {

    private String title;
    private Integer status;
    private List<Field> fields;
    private OffsetDateTime dateTime;

    @Getter
    @Setter
    public static class Field {
        private String name;
        private String message;

        public Field(String name, String message) {
            this.name = name;
            this.message = message;
        }
    }
}
