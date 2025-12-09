package egovframework.example.mapper;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import egovframework.example.dto.ReportDTO;

@Mapper("reportMapper")
public interface ReportMapper {
	// 신고 중복 검사
    int existsReport(@Param("userId") Long userId, @Param("evaluationId") Long evaluationId);

    // 신고
    int insertReport(ReportDTO report);
}
