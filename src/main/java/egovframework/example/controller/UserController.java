package egovframework.example.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*; 
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import egovframework.example.dto.UserDTO;
import egovframework.example.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Resource(name = "userService")
    private UserService userService;

	// GET: 회원가입 폼
    @GetMapping("/join")
    public String showJoinForm(@ModelAttribute("userDTO") UserDTO userDTO) {
        return "userJoin";
    }

    // POST: 회원가입 처리
    @PostMapping("/join")
    public String join(@Valid @ModelAttribute("userDTO") UserDTO user, BindingResult bindingResult, 
                       RedirectAttributes rttr, HttpServletRequest request) {
        
        if (bindingResult.hasErrors()) {
            return "userJoin";
        }

        try {
            int result = userService.join(user);

            if (result == 1) {
                rttr.addFlashAttribute("message", "회원가입이 완료되었습니다. 로그인 해 주세요.");
                return "redirect:/user/login";
            } else if (result == 0) {
                rttr.addFlashAttribute("message", "이미 존재하는 아이디입니다.");
                return "redirect:/user/join";
            } else if (result == -1) {
                rttr.addFlashAttribute("message", "이미 존재하는 이메일입니다.");
                return "redirect:/user/join";
            } else {
                rttr.addFlashAttribute("message", "회원가입 중 오류가 발생했습니다.");
                return "redirect:/user/join";
            }
        } catch (Exception e) {
            e.printStackTrace();
            rttr.addFlashAttribute("message", "서버 오류로 인해 회원가입에 실패했습니다.");
            return "redirect:/user/join";
        }
    }

    // GET: 로그인 폼
    @GetMapping("/login")
    public String showLoginForm() {
        return "userLogin";
    }

    // POST: 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam String userName, @RequestParam String userPassword,
                          RedirectAttributes rttr, HttpServletRequest request) {
        if (userName == null || userName.trim().isEmpty()
                || userPassword == null || userPassword.trim().isEmpty()) {

            rttr.addFlashAttribute("message", "아이디와 비밀번호를 입력해주세요.");
            return "redirect:/user/login";
        }

        try {
            int result = userService.login(userName, userPassword);
            if (result == 1) {
                // 로그인 성공 -> UserDTO 조회 후 민감정보 제거하고 세션에 저장
                UserDTO user = userService.getUserByUserName(userName);
                if (user != null) {
                    user.setUserPassword(null);
                    HttpSession session = request.getSession();
                    session.setAttribute("loginUser", user);
                }
                rttr.addFlashAttribute("message", "로그인 되었습니다.");
                return "redirect:/";
            } else if (result == 0) {
                rttr.addFlashAttribute("message", "비밀번호가 틀렸습니다.");
                return "redirect:/user/login";
            } else if (result == -1) {
                rttr.addFlashAttribute("message", "아이디가 존재하지 않습니다.");
                return "redirect:/user/login";
            } else {
                rttr.addFlashAttribute("message", "로그인 중 오류가 발생했습니다.");
                return "redirect:/user/login";
            }
        } catch (Exception e) {
            e.printStackTrace();
            rttr.addFlashAttribute("message", "서버 오류로 인해 로그인에 실패했습니다.");
            return "redirect:/user/login";
        }
    }

    // GET: 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, RedirectAttributes rttr) {
        // 세션 전체 삭제
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        rttr.addFlashAttribute("message", "로그아웃 되었습니다.");
        return "redirect:/index";
    }
}
