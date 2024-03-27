package com.techgusta.proposalapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProposalRequestDto {

    private String name;
    private String lastname;
    private String cpfCnpj;
    private String telephone;
    private Double salary;
    private Double requestedAmount;
    private int paymentTime;
}
