package com.techgusta.proposalapp.controllers;

import com.techgusta.proposalapp.dtos.ProposalRequestDto;
import com.techgusta.proposalapp.dtos.ProposalResponseDto;
import com.techgusta.proposalapp.services.ProposalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/proposal")
public class ProposalController {

    private ProposalService proposalService;

    @PostMapping
    public ResponseEntity<ProposalResponseDto> create(@RequestBody ProposalRequestDto requestDto) {
        ProposalResponseDto response = proposalService.create(requestDto);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").
                buildAndExpand(response.getId())
                .toUri()).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProposalResponseDto>> obtainProposal() {
        return ResponseEntity.ok(proposalService.obtainProposal());
    }
}
