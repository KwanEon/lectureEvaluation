package egovframework.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.example.dto.EvaluationDTO;

@Mapper("evaluationMapper")
public interface EvaluationMapper {
	// 모든 강의 평가 조회
	List<EvaluationDTO> findAll();
	
	// 특정 강의 평가 조회
	List<EvaluationDTO> findBySearch(@Param("lectureDivide") String lectureDivide, @Param("search") String search, @Param("offset") int offset, @Param("limit") int limit);
	
	// 검색 조건에 해당하는 전체 개수 조회
	int countEvaluations(@Param("lectureDivide") String lectureDivide, @Param("search") String search);
	
	// 특정 사용자가 쓴 평가 리스트 조회
	List<EvaluationDTO> findByUserId(@Param("userId") Long userId);
	
	// 평가 상세 조회
    EvaluationDTO findById(@Param("evaluationId") Long evaluationId);

    // 평가 작성
    int insertEvaluation(EvaluationDTO evaluation);

    // 평가 수정
    int updateEvaluation(EvaluationDTO evaluation);

    // 평가 삭제
    int deleteEvaluation(@Param("evaluationId") Long evaluationId);
    
    // 추천 중복 검사
    int existsLike(@Param("evaluationId") Long evaluationId, @Param("userId") Long userId);

    // 추천 생성
    int insertLikey(@Param("evaluationId") Long evaluationId, @Param("userId") Long userId);
    
    // 추천 카운트 증가
    int incrementLikeCount(@Param("evaluationId") Long evaluationId);
}
