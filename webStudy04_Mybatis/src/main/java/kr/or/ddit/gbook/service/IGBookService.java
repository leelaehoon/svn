package kr.or.ddit.gbook.service;

import java.util.List;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.vo.GBookVO;
import kr.or.ddit.vo.PagingInfoVO;

public interface IGBookService {
	public ServiceResult createGBook(GBookVO gbook);
	public long retrieveTotalCount(PagingInfoVO<GBookVO> pagingVO);
	public List<GBookVO> retrieveGBookList(PagingInfoVO<GBookVO> pagingVO);
	public ServiceResult modifyGBook(GBookVO gbook);
	public ServiceResult removeGBook(GBookVO gbook);
}
