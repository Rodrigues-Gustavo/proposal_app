package com.techgusta.proposalapp.repositories;

import com.techgusta.proposalapp.entities.Proposal;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProposalRepository extends CrudRepository<Proposal, Long> {

    List<Proposal> findAllByIntegratedIsFalse();

        @Transactional
        @Modifying
        @Query(value = "UPDATE proposal SET approved = :approved, observation = :observation WHERE id = :id", nativeQuery = true)
        void updateProposal(Long id, boolean approved, String observation);


}
