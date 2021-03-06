package kr.or.ddit.member.service;

import java.util.List;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingInfoVO;

/**
 * @author 이래훈
 * @since 2018. 11. 21.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2018. 11. 21.      이래훈       회원관리를 위한 Business Logic Layer
 * Copyright (c) 2018 by DDIT All right reserved
 * </pre>
 */
public interface IMemberService {
	/**
	 * 회원 신규 등록
	 * @param member
	 * @return PKDUPLICATED, SUCCESS, FAILED
	 */
	public ServiceResult registMember(MemberVO member);
	public long retrieveMemberCount(PagingInfoVO pagingVO);
	/**
	 * 회원 목록 조회
	 * @param pagingVO TODO
	 * @return 존재하지 않을때, size()==0
	 */
	public List<MemberVO> retrieveMemberList(PagingInfoVO pagingVO);
	/**
	 * 회원 정보 상세 조회
	 * @param mem_id 조회할 회원의 아이디
	 * @return 존재하지 않을때, CommonException 발생
	 */
	public MemberVO retrieveMember(String mem_id);
	/**
	 * 회원 정보 수정
	 * @param member
	 * @return CommonException, INVALIDPASSWORD, SUCCESS, FAILED
	 */
	public ServiceResult modifyMember(MemberVO member);
	/**
	 * 회원 탈퇴
	 * @param member
	 * @return CommonException, INVALIDPASSWORD, SUCCESS, FAILED
	 */
	public ServiceResult removeMember(MemberVO member);
}



























