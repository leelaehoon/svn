package kr.or.ddit.prod.service;

import java.util.List;

import kr.or.ddit.CommonException;
import kr.or.ddit.ServiceResult;
import kr.or.ddit.prod.dao.IProdDAO;
import kr.or.ddit.prod.dao.ProdDAOImpl;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.ProdVO;

public class ProdServiceImpl implements IProdService {
	IProdDAO prodDAO = new ProdDAOImpl();
	
	@Override
	public ServiceResult createProd(ProdVO prod) {
		ServiceResult result = ServiceResult.FAILED;
		
		if (prodDAO.insertProd(prod)!=null) {
			result = ServiceResult.SUCCESS;
		}
		
		return result;
	}

	@Override
	public ProdVO retrieveProd(String prod_id) {
		ProdVO prod = prodDAO.selectProd(prod_id);
		if (prod==null) {
			throw new CommonException(prod_id+"에 해당하는 상품이 없음.");
		}
		return prod;
	}

	@Override
	public Long retrieveProdCount(PagingInfoVO<ProdVO> pagingVO) {
		return prodDAO.selectTotalRecord(pagingVO);
	}

	@Override
	public List<ProdVO> retrieveProdList(PagingInfoVO<ProdVO> pagingVO) {
		return prodDAO.selectProdList(pagingVO);
	}

	@Override
	public ServiceResult modifyProd(ProdVO prod) {
		return null;
	}

}
