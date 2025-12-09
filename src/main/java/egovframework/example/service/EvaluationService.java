package egovframework.example.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.example.dto.EvaluationDTO;
import egovframework.example.mapper.EvaluationMapper;

@Service("evaluationService")
@Transactional
public class EvaluationService {

    @Resource(name = "evaluationMapper")
    private EvaluationMapper evaluationMapper;

    @Transactional(readOnly = true)
    public List<EvaluationDTO> getAllEvaluations() {
        return evaluationMapper.findAll();
    }

    @Transactional(readOnly = true)
    public List<EvaluationDTO> searchEvaluations(String lectureDivide, String search, int offset, int limit) {
        if (search != null) search = search.trim();
        if (lectureDivide != null) lectureDivide = lectureDivide.trim();
        return evaluationMapper.findBySearch(lectureDivide, search, offset, limit);
    }
    
    @Transactional(readOnly = true)
    public int countEvaluations(String lectureDivide, String search) {
    	return evaluationMapper.countEvaluations(lectureDivide, search);
    }

    @Transactional(readOnly = true)
    public List<EvaluationDTO> getEvaluationsByUser(Long userId) {
        return evaluationMapper.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public EvaluationDTO getEvaluation(Long evaluationId) {
        return evaluationMapper.findById(evaluationId);
    }

    public boolean addEvaluation(EvaluationDTO evaluation) {
        return evaluationMapper.insertEvaluation(evaluation) > 0;
    }

    public boolean updateEvaluation(EvaluationDTO evaluation) {
        return evaluationMapper.updateEvaluation(evaluation) > 0;
    }

    public boolean deleteEvaluation(Long evaluationId) {
        return evaluationMapper.deleteEvaluation(evaluationId) > 0;
    }

    /**
     * 평가 추천
     * - 1 : 성공
     * - 0 : 추천 중복
     * - -1 : 실패
     */
    public int likeEvaluation(Long evaluationId, Long userId) {
        int exists = evaluationMapper.existsLike(evaluationId, userId);
        if (exists > 0) {
            return 0;
        }

        int inserted = evaluationMapper.insertLikey(evaluationId, userId);
        if (inserted == 0) {
            return -1;
        }

        int updated = evaluationMapper.incrementLikeCount(evaluationId);
        if (updated == 0) {
            return -1;
        }

        return 1;
    }
}
