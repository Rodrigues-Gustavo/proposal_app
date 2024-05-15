package com.techgusta.proposalapp.listener;

import com.techgusta.proposalapp.dtos.ProposalResponseDto;
import com.techgusta.proposalapp.entities.Proposal;
import com.techgusta.proposalapp.mapper.ProposalMapper;
import com.techgusta.proposalapp.repositories.ProposalRepository;
import com.techgusta.proposalapp.services.WebSocketService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompletedProposalListener {

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private WebSocketService webSocketService;

    @RabbitListener(queues = "${rabbitmq.queue.completed.proposal}")
    public void completedProposal(Proposal proposal) {
        proposalRepository.save(proposal);
        ProposalResponseDto responseDto = ProposalMapper.INSTANCE.convertEntityToDto(proposal);
        webSocketService.notify(responseDto);
    }

    private void updateProposal(Proposal proposal) {
        proposalRepository.updateProposal(proposal.getId(), proposal.getApproved(), proposal.getObservation());
    }
}
