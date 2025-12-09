package egovframework.example.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import egovframework.example.dto.ReportDTO;
import egovframework.example.dto.UserDTO;
import egovframework.example.service.ReportService;

@Controller
@RequestMapping("/report")
public class ReportController {

	@Resource(name = "reportService")
    private ReportService reportService;

    // POST: 신고하기
    @PostMapping("/{evaluationId}")
    public String reportEvaluation(@PathVariable Long evaluationId, ReportDTO report, HttpServletRequest request, RedirectAttributes rttr) {

        HttpSession session = request.getSession(false);
        UserDTO loginUser = session == null ? null : (UserDTO) session.getAttribute("loginUser");

        if (loginUser == null) {
            rttr.addFlashAttribute("message", "로그인 후 이용해주세요.");
            return "redirect:/user/login";
        }

        report.setUserId(loginUser.getId());

        if (report.getEvaluationId() == null ||
            report.getReportTitle() == null || report.getReportTitle().trim().isEmpty() ||
            report.getReportContent() == null || report.getReportContent().trim().isEmpty()) {

            rttr.addFlashAttribute("message", "신고 내용을 모두 입력해주세요.");
            return "redirect:/";
        }

        try {
            int result = reportService.reportEvaluation(report);
            if (result == 1) {
                rttr.addFlashAttribute("message", "신고가 접수되었습니다.");
            } else if (result == 0) {
                rttr.addFlashAttribute("message", "이미 신고한 평가입니다.");
            } else {
                rttr.addFlashAttribute("message", "신고 처리 중 오류가 발생했습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            rttr.addFlashAttribute("message", "서버 오류로 인해 신고에 실패했습니다.");
        }

        return "redirect:/";
    }
}
