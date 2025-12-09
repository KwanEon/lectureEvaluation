package egovframework.example.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import egovframework.example.dto.EvaluationDTO;
import egovframework.example.dto.UserDTO;
import egovframework.example.service.EvaluationService;

@Controller
@RequestMapping("/evaluation")
public class EvaluationController {

    @Resource(name = "evaluationService")
    private EvaluationService evaluationService;

    // POST: 평가 등록
    @PostMapping("/add")
    public String addEvaluation(EvaluationDTO evaluation, HttpServletRequest request, RedirectAttributes rttr) {
        HttpSession session = request.getSession(false);
        UserDTO loginUser = session == null ? null : (UserDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            rttr.addFlashAttribute("message", "로그인 후 이용해주세요.");
            return "redirect:/user/login";
        }

        Long userId = loginUser.getId();
        evaluation.setUserId(userId);

        if (evaluation.getLectureName() == null || evaluation.getLectureName().trim().isEmpty()
            || evaluation.getProfessorName() == null || evaluation.getProfessorName().trim().isEmpty()
            || evaluation.getEvaluationTitle() == null || evaluation.getEvaluationTitle().trim().isEmpty()
            || evaluation.getEvaluationContent() == null || evaluation.getEvaluationContent().trim().isEmpty()) {

            rttr.addFlashAttribute("message", "입력이 안 된 사항이 있습니다.");
            return "redirect:/";
        }

        try {
            boolean success = evaluationService.addEvaluation(evaluation);
            if (success) {
                rttr.addFlashAttribute("message", "강의평가가 등록되었습니다.");
            } else {
                rttr.addFlashAttribute("message", "평가 등록 중 오류가 발생했습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            rttr.addFlashAttribute("message", "서버 오류로 인해 평가 등록에 실패했습니다.");
        }

        return "redirect:/";
    }

    // DELETE: 평가 삭제
    @DeleteMapping("/{evaluationId}")
    public String deleteEvaluation(@PathVariable Long evaluationId, HttpServletRequest request, RedirectAttributes rttr) {
        HttpSession session = request.getSession(false);
        UserDTO loginUser = session == null ? null : (UserDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            rttr.addFlashAttribute("message", "로그인 후 이용해주세요.");
            return "redirect:/user/login";
        }
        Long userId = loginUser.getId();

        try {
            EvaluationDTO evaluation = evaluationService.getEvaluation(evaluationId);
            if (evaluation == null) {
                rttr.addFlashAttribute("message", "삭제할 평가가 존재하지 않습니다.");
                return "redirect:/";
            }

            if (!userId.equals(evaluation.getUserId())) {
                rttr.addFlashAttribute("message", "본인이 작성한 평가만 삭제할 수 있습니다.");
                return "redirect:/";
            }

            boolean success = evaluationService.deleteEvaluation(evaluationId);
            if (success) {
                rttr.addFlashAttribute("message", "평가가 삭제되었습니다.");
            } else {
                rttr.addFlashAttribute("message", "평가 삭제 중 오류가 발생했습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            rttr.addFlashAttribute("message", "서버 오류로 인해 평가 삭제에 실패했습니다.");
        }

        return "redirect:/";
    }

    // POST: 평가 추천
    @PostMapping("/like/{evaluationId}")
    public String likeEvaluation(@PathVariable("evaluationId") Long evaluationId, HttpServletRequest request, RedirectAttributes rttr) {
        HttpSession session = request.getSession(false);
        UserDTO loginUser = session == null ? null : (UserDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            rttr.addFlashAttribute("message", "로그인 후 이용해주세요.");
            return "redirect:/user/login";
        }

        Long userId = loginUser.getId();

        try {
            int result = evaluationService.likeEvaluation(evaluationId, userId);
            if (result == 1) {
                rttr.addFlashAttribute("message", "평가를 추천하였습니다.");
            } else if (result == 0) {
                rttr.addFlashAttribute("message", "이미 추천한 평가입니다.");
            } else {
            	rttr.addFlashAttribute("message", "추천 중 오류가 발생했습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            rttr.addFlashAttribute("message", "서버 오류로 인해 추천에 실패했습니다.");
        }

        return "redirect:/";
    }
}
