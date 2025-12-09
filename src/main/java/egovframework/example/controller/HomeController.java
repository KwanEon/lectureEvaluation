package egovframework.example.controller;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.example.dto.EvaluationDTO;
import egovframework.example.service.EvaluationService;

@Controller
public class HomeController {
	
	@Resource(name = "evaluationService")
	private EvaluationService evaluationService;
	
	// GET: 메인화면(강의 평가 포함)
	@GetMapping({"/", "/index"})
	public String home(
			@RequestParam(value="lectureDivide", required=false) String lectureDivide,
            @RequestParam(value="search", required=false) String search,
            @RequestParam(value="page", required=false, defaultValue="1") int page,
            Model model) {
		
	    final int PAGE_UNIT = 5;   // 한 페이지에 보여줄 항목 수 (recordCountPerPage)
	    final int PAGE_SIZE = 5;   // 페이지네비에서 보여줄 페이지 블록 수
	    
	    int totalCount = evaluationService.countEvaluations(lectureDivide, search);

	    // 전체 페이지 수 계산
	    int totalPage = (int) Math.ceil((double) totalCount / PAGE_UNIT);

	    // 페이지 번호 보정
	    if (page < 1) {
	        page = 1;
	    }
	    if (page > totalPage && totalPage != 0) {
	        page = totalPage;
	    }

	    // PaginationInfo 세팅
	    PaginationInfo paginationInfo = new PaginationInfo();
	    paginationInfo.setCurrentPageNo(page);					// 현재 페이지 번호
	    paginationInfo.setRecordCountPerPage(PAGE_UNIT);		// 한 페이지 당 레코드 수
	    paginationInfo.setPageSize(PAGE_SIZE);					// 페이지 네비게이션 블록 크기
	    paginationInfo.setTotalRecordCount(totalCount);			// 총 레코드 수

	    // 각각 OFFSET, LIMIT으로 쿼리에 들어감
	    int firstIndex = paginationInfo.getFirstRecordIndex();				// 첫 번째 레코드 인덱스  (OFFSET)
	    int recordCountPerPage = paginationInfo.getRecordCountPerPage();	// 한 페이지 당 레코드 수 (LIMIT)

	    List<EvaluationDTO> evaluations = evaluationService.searchEvaluations(lectureDivide, search, firstIndex, recordCountPerPage);

	    model.addAttribute("evaluations", evaluations);
	    model.addAttribute("paginationInfo", paginationInfo);
	    model.addAttribute("lectureDivide", lectureDivide);
	    model.addAttribute("search", search);
	    
	    return "index";
	}
}
