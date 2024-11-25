package com.popping.data.report.service;

import com.popping.data.report.entity.PopReport;
import com.popping.data.report.repository.PopReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PopReportService {
    private final PopReportRepository popReportRepository;

    public void save(PopReport popReport) {
        popReportRepository.save(popReport);
    }

    public boolean isExist(Long reporterPk, Long targetPk) {
        return popReportRepository.existsByReporter_PkAndTarget_Pk(reporterPk, targetPk);
    }

    public List<Long> findNotExpiredReportPopPks(Long requesterPk) {
        return popReportRepository.findNotExpiredReportPopPks(requesterPk);
    }
}
