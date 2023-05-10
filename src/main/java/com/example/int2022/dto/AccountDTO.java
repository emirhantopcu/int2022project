package com.example.int2022.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AccountDTO {
    private final Long id;
    private final String name;
    private final String email;
    private boolean isOwner;

}
