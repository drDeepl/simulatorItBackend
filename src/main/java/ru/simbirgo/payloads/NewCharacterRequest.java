package ru.simbirgo.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCharacterRequest {
    private String name;
    private Long professionId;
}
