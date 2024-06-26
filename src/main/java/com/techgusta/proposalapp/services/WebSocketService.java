package com.techgusta.proposalapp.services;

import com.techgusta.proposalapp.dtos.ProposalResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate template;

    public void notify(ProposalResponseDto proposalResponseDto) {
        template.convertAndSend("/propostas", proposalResponseDto);
    }
}
