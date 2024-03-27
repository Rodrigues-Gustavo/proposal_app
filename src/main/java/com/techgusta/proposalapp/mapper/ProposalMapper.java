package com.techgusta.proposalapp.mapper;

import com.techgusta.proposalapp.dtos.ProposalRequestDto;
import com.techgusta.proposalapp.dtos.ProposalResponseDto;
import com.techgusta.proposalapp.entities.Proposal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.text.NumberFormat;
import java.util.List;

@Mapper
public interface ProposalMapper {

    ProposalMapper INSTANCE = Mappers.getMapper(ProposalMapper.class);

    @Mapping(target = "user.name", source = "name")
    @Mapping(target = "user.lastname", source = "lastname")
    @Mapping(target = "user.cpfCnpj", source = "cpfCnpj")
    @Mapping(target = "user.telephone", source = "telephone")
    @Mapping(target = "user.salary", source = "salary")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "approved", ignore = true)
    @Mapping(target = "integrated", constant = "true")
    @Mapping(target = "observation", ignore = true)
    Proposal convertDtoToProposal(ProposalRequestDto proposalRequestDto);

    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "lastname", source = "user.lastname")
    @Mapping(target = "telephone", source = "user.telephone")
    @Mapping(target = "cpfCnpj", source = "user.cpfCnpj")
    @Mapping(target = "salary", source = "user.salary")
    @Mapping(target = "requestedAmountFmt", expression = "java(setRequestedAmountFmt(proposal))")
    ProposalResponseDto convertEntityToDto(Proposal proposal);

    List<ProposalResponseDto> convertListEntityToListDto(Iterable<Proposal> proposals);

    default String setRequestedAmountFmt(Proposal proposal) {
        return NumberFormat.getCurrencyInstance().format(proposal.getRequestedAmount());
    }
}
