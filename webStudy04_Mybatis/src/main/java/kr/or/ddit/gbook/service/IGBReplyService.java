package kr.or.ddit.gbook.service;

import java.util.List;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.vo.GBReplyVO;
import kr.or.ddit.vo.PagingInfoVO;

public interface IGBReplyService {
	public ServiceResult createGBReply(GBReplyVO gbreply);
	public long retrieveTotalCount(PagingInfoVO<GBReplyVO> pagingVO);
	public List<GBReplyVO> retrieveGBReplyList(PagingInfoVO<GBReplyVO> pagingVO);
	public ServiceResult modifyGBReply(GBReplyVO gbreply);
	public ServiceResult removeGBReply(GBReplyVO gbreply);
}
