package kr.or.ddit.buyer.service;

import java.util.List;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingInfoVO;

public interface IBuyerService {
	public ServiceResult createBuyer(BuyerVO buyer);
	public BuyerVO retrieveBuyer(String buyer_id);
	public long retrieveBuyerCount(PagingInfoVO<BuyerVO> pagingVO);
	public List<BuyerVO> retrieveBuyerList(PagingInfoVO<BuyerVO> pagingVO);
	public int modifyBuyer(BuyerVO buyer);
}
