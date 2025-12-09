package egovframework.example.service;

import javax.annotation.Resource;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.example.dto.UserDTO;
import egovframework.example.mapper.UserMapper;

@Service("userService")
@Transactional
public class UserService {

    @Resource(name = "userMapper")
    private UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public Long getUserId(String userName) {
        return userMapper.findIdByUserName(userName);
    }
    
    public UserDTO getUserByUserName(String userName) {
        return userMapper.findByUserName(userName);
    }

    /**
     * 로그인
     *  - 1 : 성공
     *  - 0 : 비밀번호 불일치
     *  - -1: 아이디 없음
     */
    public int login(String userName, String userPassword) {
        String dbPassword = userMapper.findPasswordByUserName(userName);

        if (dbPassword != null) {
            return passwordEncoder.matches(userPassword, dbPassword) ? 1 : 0;
        }
        return -1;
    }

    /**
     * 회원가입
     *  - 1 : 성공
     *  - 0 : 아이디 중복
     *  - -1 : 이메일 중복
     *  - -2 : 실패
     */
    public int join(UserDTO user) {
        if (userMapper.findByUserName(user.getUserName()) != null) {
            return 0;
        }
        
        if (userMapper.findByUserEmail(user.getUserEmail()) != null) {
            return -1;
        }

        String raw = user.getUserPassword();
        String hashed = passwordEncoder.encode(raw);
        user.setUserPassword(hashed);

        int inserted = userMapper.insertUser(user);
        return inserted > 0 ? 1 : -2;
    }

    @Transactional(readOnly = true)
    public String getUserEmail(Long id) {
        return userMapper.findUserEmail(id);
    }
}
