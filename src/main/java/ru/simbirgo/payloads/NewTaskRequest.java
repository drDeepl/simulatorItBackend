package ru.simbirgo.payloads;

import io.micrometer.common.lang.NonNullApi;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewTaskRequest {
    private Long professionId;
    private String description;
    private Integer levelDifficult;
}
