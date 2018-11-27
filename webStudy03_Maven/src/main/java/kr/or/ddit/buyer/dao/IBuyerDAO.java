package kr.or.ddit.buyer.dao;

import java.util.List;

import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingInfoVO;

public interface IBuyerDAO {
	public String insertBuyer(BuyerVO buyer);
	public BuyerVO selectBuyer(String buyer_id);
	public long selectTotalRecord(PagingInfoVO<BuyerVO> pagingVO);
	public List<BuyerVO> selectBuyerList(PagingInfoVO<BuyerVO> pagingVO);
	public int updateBuyer(BuyerVO buyer);
}
