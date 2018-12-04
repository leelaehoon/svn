package kr.or.ddit.buyer.service;

import java.util.List;

import kr.or.ddit.CommonException;
import kr.or.ddit.ServiceResult;
import kr.or.ddit.buyer.dao.BuyerDAOImpl;
import kr.or.ddit.buyer.dao.IBuyerDAO;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingInfoVO;

public class BuyerServiceImpl implements IBuyerService {
	IBuyerDAO buyerDAO = new BuyerDAOImpl();

	@Override
	public ServiceResult createBuyer(BuyerVO buyer) {
		ServiceResult result = ServiceResult.FAILED;
				
		if (buyerDAO.insertBuyer(buyer)>0) {
			result = ServiceResult.SUCCESS;
		}
		
		return result;
	}

	@Override
	public BuyerVO retrieveBuyer(String buyer_id) {
		BuyerVO buyer = buyerDAO.selectBuyer(buyer_id);
		if (buyer == null) {
			throw new CommonException(buyer_id + "는 없는 거래처입니다.");
		}
		return buyer;
	}

	@Override
	public long retrieveBuyerCount(PagingInfoVO<BuyerVO> pagingVO) {
		return buyerDAO.selectTotalRecord(pagingVO);
	}

	@Override
	public List<BuyerVO> retrieveBuyerList(PagingInfoVO<BuyerVO> pagingVO) {
		return buyerDAO.selectBuyerList(pagingVO);
	}

	@Override
	public ServiceResult modifyBuyer(BuyerVO buyer) {
		ServiceResult result = ServiceResult.FAILED;
		if (buyerDAO.selectBuyer(buyer.getBuyer_id())==null) {
			throw new CommonException(buyer.getBuyer_id() + "에 해당하는 거래처가 없습니다.");
		}
		if (buyerDAO.updateBuyer(buyer)>0) {
			result = ServiceResult.SUCCESS;
		}
		return result;
	}

}
