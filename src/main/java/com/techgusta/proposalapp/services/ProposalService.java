package com.techgusta.proposalapp.services;

import com.techgusta.proposalapp.dtos.ProposalRequestDto;
import com.techgusta.proposalapp.dtos.ProposalResponseDto;
import com.techgusta.proposalapp.entities.Proposal;
import com.techgusta.proposalapp.mapper.ProposalMapper;
import com.techgusta.proposalapp.repositories.ProposalRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProposalService {

    private ProposalRepository proposalRepository;

    private NotificationRabbitService notificationService;

    private String exchange;

    public ProposalService(ProposalRepository proposalRepository,
                           NotificationRabbitService notificationService,
                           @Value("${rabbitmq.pendingproposal.exchange}") String exchange) {
        this.proposalRepository = proposalRepository;
        this.notificationService = notificationService;
        this.exchange = exchange;
    }

    public ProposalResponseDto create(ProposalRequestDto requestDto) {
        Proposal proposal = ProposalMapper.INSTANCE.convertDtoToProposal(requestDto);
        proposalRepository.save(proposal);

       notifyRabbitMQ(proposal);

        return ProposalMapper.INSTANCE.convertEntityToDto(proposal);
    }

    private void notifyRabbitMQ(Proposal proposal) {
        try {
            notificationService.notify(proposal, exchange);
        } catch (RuntimeException e) {
            proposal.setIntegrated(false);
            proposalRepository.save(proposal);
        }
    }

    public List<ProposalResponseDto> obtainProposal() {
        return ProposalMapper.INSTANCE.convertListEntityToListDto(proposalRepository.findAll());
    }
}
