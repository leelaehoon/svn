package kr.or.ddit.member.service;

import java.util.List;

import kr.or.ddit.CommonException;
import kr.or.ddit.ServiceResult;
import kr.or.ddit.member.dao.IMemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingInfoVO;

public class MemberServiceImpl implements IMemberService {
	// 의존 객체를 직접 생성하는 방식 : 결합력 최상
	IMemberDAO memberDAO = new MemberDAOImpl();

	@Override
	public ServiceResult registMember(MemberVO member) {
		ServiceResult result = ServiceResult.PKDUPLICATED;
		if (memberDAO.selectMember(member.getMem_id())==null) {
			if (memberDAO.insertMember(member) > 0) {
				result = ServiceResult.SUCCESS;
			} else {
				result = ServiceResult.FAILED;
			}
		}
		return result;
	}

	@Override
	public long retrieveMemberCount(PagingInfoVO pagingVO) {
		return memberDAO.selectTotalRecord(pagingVO);
	}

	@Override
	public List<MemberVO> retrieveMemberList(PagingInfoVO pagingVO) {
		List<MemberVO> memberList = memberDAO.selectMemberList(pagingVO);
		return memberList;
	}

	@Override
	public MemberVO retrieveMember(String mem_id) {
		MemberVO member = memberDAO.selectMember(mem_id);
		if (member==null) {
			throw new CommonException(mem_id+"에 해당하는 회원이 없음.");
		}
		return member;
	}

	@Override
	public ServiceResult modifyMember(MemberVO member) {
		ServiceResult result = null;
		MemberVO check = memberDAO.selectMember(member.getMem_id());
		if (check==null) {
			throw new CommonException(member.getMem_id()+"에 해당하는 회원이 없음.");
		} else {
			if (!check.getMem_pass().equals(member.getMem_pass())) {
				result = ServiceResult.INVALIDPASSWORD;
			} else {
				if (memberDAO.updateMember(member)>0) {
					result = ServiceResult.SUCCESS;
				} else {
					result = ServiceResult.FAILED;
				}
			}
		}
		return result;
	}

	@Override
	public ServiceResult removeMember(MemberVO member) {
		ServiceResult result = null;
		MemberVO check = memberDAO.selectMember(member.getMem_id());
		if (check==null) {
			throw new CommonException(member.getMem_id()+"에 해당하는 회원이 없음.");
		} else {
			if (!check.getMem_pass().equals(member.getMem_pass())) {
				result = ServiceResult.INVALIDPASSWORD;
			} else {
				if (memberDAO.deleteMember(member.getMem_id())>0) {
					result = ServiceResult.SUCCESS;
				} else {
					result = ServiceResult.FAILED;
				}
			}
		}
		return result;
	}

}
