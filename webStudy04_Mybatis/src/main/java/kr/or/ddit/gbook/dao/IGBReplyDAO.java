package kr.or.ddit.gbook.dao;

import java.util.List;

import kr.or.ddit.vo.GBReplyVO;
import kr.or.ddit.vo.PagingInfoVO;

public interface IGBReplyDAO {
	public int insertGBReply(GBReplyVO gbreply);
	public long selectTotalCount(PagingInfoVO<GBReplyVO> pagingVO);
	public List<GBReplyVO> selectGBReplyList(PagingInfoVO<GBReplyVO> pagingVO);
	public int updateGBReply(GBReplyVO gbreply);
	public int deleteGBReply(long gbr_no);
}
