package com.popping.data.report.service;

import com.popping.data.report.entity.RePopReport;
import com.popping.data.report.repository.RePopReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RePopReportService {
    private final RePopReportRepository rePopreportRepository;

    public void save(RePopReport repReport) {
        rePopreportRepository.save(repReport);
    }

    public boolean isExist(Long reporterPk, Long targetPk) {
        return rePopreportRepository.existsByReporter_PkAndTarget_Pk(reporterPk, targetPk);
    }
}
