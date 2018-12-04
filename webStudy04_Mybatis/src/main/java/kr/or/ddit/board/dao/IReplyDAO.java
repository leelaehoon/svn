package kr.or.ddit.board.dao;

import java.util.List;

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
 * 2018. 12. 4.  이래훈       덧글 관리를 위한 persistence Layer
 * Copyright (c) 2018 by DDIT All right reserved
 * </pre>
 */
public interface IReplyDAO {
	/**
	 * 댓글 작성
	 * @param reply
	 * @return row count
	 */
	public int insertReply(ReplyVO reply);
	/**
	 * 특정 게시글의 전체 댓글 수
	 * @param pagingVO
	 * @return
	 */
	public long selectTotalRecord(PagingInfoVO<ReplyVO> pagingVO);
	/**
	 * 특정 게시글의 댓글 목록 조회
	 * @param pagingVO
	 * @return 없다면 .size()==0 
	 */
	public List<ReplyVO> selectReplyList(PagingInfoVO<ReplyVO> pagingVO);
	/**
	 * 댓글 상세 조회
	 * @param rep_no 댓글번호
	 * @return 없다면 null 반환
	 */
	public ReplyVO selectReply(long rep_no);
	/**
	 * 댓글 수정
	 * @param reply
	 * @return row count
	 */
	public int updateReply(ReplyVO reply);
	/**
	 * 댓글 삭제
	 * @param rep_no
	 * @return row count
	 */
	public int deleteReply(long rep_no);
}














