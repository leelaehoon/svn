package kr.or.ddit.gbook.dao;

import java.util.List;

import kr.or.ddit.vo.GBookVO;
import kr.or.ddit.vo.PagingInfoVO;

public interface IGBookDAO {
	public int insertGBook(GBookVO gbook);
	public long selectTotalCount(PagingInfoVO<GBookVO> pagingVO);
	public List<GBookVO> selectGBookList(PagingInfoVO<GBookVO> pagingVO);
	public int updateGBook(GBookVO gbook);
	public int deleteGBook(long gb_no);
}
