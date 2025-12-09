package egovframework.example.mapper;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import egovframework.example.dto.UserDTO;

@Mapper("userMapper")
public interface UserMapper {
	// userDTO 반환(id)
	UserDTO findById(@Param("id") Long id);
	
	// userDTO 반환(userName)
	UserDTO findByUserName(@Param("userName") String userName);
	
	// userDTO 반환(userEmail)
	UserDTO findByUserEmail(@Param("userEmail") String userEmail);
	
	// id 반환
	Long findIdByUserName(@Param("userName") String userName);
	
    // userPassword 반환
    String findPasswordByUserName(@Param("userName") String userName);

    // 회원가입
    int insertUser(UserDTO user);

    // 이메일 조회
    String findUserEmail(@Param("id") Long id);
}