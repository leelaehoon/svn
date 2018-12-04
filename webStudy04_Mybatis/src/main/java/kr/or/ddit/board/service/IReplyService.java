package kr.or.ddit.board.service;

import java.util.List;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.ReplyVO;

/**
 * @author 이래훈
 * @since 2018. 12. 4.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일        수정자               수정내용
 * --------     --------    ----------------------
 * 2018. 12. 4.  이래훈       댓글 관리용 Business Login Layer
 * Copyright (c) 2018 by DDIT All right reserved
 * </pre>
 */
public interface IReplyService {
	/**
	 * 댓글작성
	 * @param reply
	 * @return SUCCESS, FAILED
	 */
	public ServiceResult createReply(ReplyVO reply);
	/**
	 * 특정 게시글에 댓글 수
	 * @param pagingVO
	 * @return 없다면 0
	 */
	public long retrieveReplyCount(PagingInfoVO<ReplyVO> pagingVO);
	/**
	 * 특정 게시글의 댓글 목록
	 * @param pagingVO
	 * @return 없다면 .size() == 0
	 */
	public List<ReplyVO> retrieveReplyList(PagingInfoVO<ReplyVO> pagingVO);
	/**
	 * 댓글 수정
	 * @param reply
	 * @return BoardException, INVALIDPASSWORD, SUCCESS, FAILED
	 */
	public ServiceResult modifyReply(ReplyVO reply);
	/**
	 * 댓글 삭제
	 * @param reply
	 * @return BoardException, INVALIDPASSWORD, SUCCESS, FAILED
	 */
	public ServiceResult removeReply(ReplyVO reply);
}










