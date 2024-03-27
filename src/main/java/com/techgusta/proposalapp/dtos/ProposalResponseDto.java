package com.techgusta.proposalapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProposalResponseDto {

    private Long id;
    private String name;
    private String lastname;
    private String cpfCnpj;
    private String telephone;
    private Double salary;
    private String requestedAmountFmt;
    private int paymentTime;
    private Boolean approved;
    private String observation;
}
