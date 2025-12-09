package egovframework.example.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.example.dto.ReportDTO;
import egovframework.example.mapper.ReportMapper;

@Service("reportService")
@Transactional
public class ReportService {

    @Resource(name = "reportMapper")
    private ReportMapper reportMapper;

    /**
     * 신고 처리
     * - 1 : 성공
     * - 0 : 신고 중복
     * - -1 : 실패
     */
    public int reportEvaluation(ReportDTO report) {
        int exists = reportMapper.existsReport(report.getUserId(), report.getEvaluationId());
        if (exists > 0) return 0;

        int inserted = reportMapper.insertReport(report);
        return inserted > 0 ? 1 : -1;
    }
}
