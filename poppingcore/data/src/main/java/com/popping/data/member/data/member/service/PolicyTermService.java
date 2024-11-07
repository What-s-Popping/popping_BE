package com.popping.data.member.data.member.service;

import com.popping.data.member.data.member.entity.PolicyTerm;
import com.popping.data.member.data.member.repository.PolicyTermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyTermService {
    private final PolicyTermRepository policyTermRepository;

    public void savePolicyTerm(PolicyTerm policyTerm) {
        policyTermRepository.save(policyTerm);
    }
}
