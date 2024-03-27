package com.techgusta.proposalapp.scheduler;

import com.techgusta.proposalapp.entities.Proposal;
import com.techgusta.proposalapp.repositories.ProposalRepository;
import com.techgusta.proposalapp.services.NotificationRabbitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ProposalWithoutIntegration {

    private final ProposalRepository proposalRepository;

    private final NotificationRabbitService notificationRabbitService;

    private final String exchange;

    private final Logger logger = LoggerFactory.getLogger(ProposalWithoutIntegration.class);

    public ProposalWithoutIntegration(ProposalRepository proposalRepository,
                                      NotificationRabbitService notificationRabbitService,
                                      @Value("${rabbitmq.pendingproposal.exchange}") String exchange) {
        this.proposalRepository = proposalRepository;
        this.notificationRabbitService = notificationRabbitService;
        this.exchange = exchange;
    }

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void findProposalWithoutIntegration() {
        proposalRepository.findAllByIntegratedIsFalse().forEach(proposal -> {
            try {
                notificationRabbitService.notify(proposal, exchange);
                updateProposal(proposal);
            } catch (RuntimeException e) {
                logger.error(e.getMessage());
            }
        });
    }

    private void updateProposal(Proposal proposal) {
        proposal.setIntegrated(true);
        proposalRepository.save(proposal);
    }
}