package kr.or.ddit.board.service;

import java.util.List;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.BoardException;
import kr.or.ddit.board.dao.IReplyDAO;
import kr.or.ddit.board.dao.ReplyDAOImpl;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.ReplyVO;

public class ReplyServiceImpl implements IReplyService {
	IReplyDAO replyDAO = new ReplyDAOImpl();

	@Override
	public ServiceResult createReply(ReplyVO reply) {
		ServiceResult result = ServiceResult.FAILED;
		
		if (replyDAO.insertReply(reply) > 0) {
			result = ServiceResult.SUCCESS;
		}
		
		return result;
	}

	@Override
	public long retrieveReplyCount(PagingInfoVO<ReplyVO> pagingVO) {
		return replyDAO.selectTotalRecord(pagingVO);
	}

	@Override
	public List<ReplyVO> retrieveReplyList(PagingInfoVO<ReplyVO> pagingVO) {
		return replyDAO.selectReplyList(pagingVO);
	}

	@Override
	public ServiceResult modifyReply(ReplyVO reply) {
		return null;
	}

	@Override
	public ServiceResult removeReply(ReplyVO reply) {
		ServiceResult result = ServiceResult.FAILED;
		ReplyVO check = replyDAO.selectReply(reply.getRep_no());
		if (check == null) {
			throw new BoardException(reply.getRep_no() + "번에 해당하는 댓글이 없습니다.");
		} else {
			if (!check.getRep_pass().equals(reply.getRep_pass())) {
				result = ServiceResult.INVALIDPASSWORD;
			} else {
				if (replyDAO.deleteReply(reply.getRep_no()) > 0) {
					result = ServiceResult.SUCCESS;
				}
			}
		}
		return result;
	}
}
























