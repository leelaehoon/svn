package kr.or.ddit.gbook.service;

import java.util.List;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.gbook.dao.GBookDAOImpl;
import kr.or.ddit.gbook.dao.IGBookDAO;
import kr.or.ddit.vo.GBookVO;
import kr.or.ddit.vo.PagingInfoVO;

public class GBookServiceImpl implements IGBookService {
	IGBookDAO gbookDAO = new GBookDAOImpl();

	@Override
	public ServiceResult createGBook(GBookVO gbook) {
		return null;
	}

	@Override
	public long retrieveTotalCount(PagingInfoVO<GBookVO> pagingVO) {
		return gbookDAO.selectTotalCount(pagingVO);
	}

	@Override
	public List<GBookVO> retrieveGBookList(PagingInfoVO<GBookVO> pagingVO) {
		return gbookDAO.selectGBookList(pagingVO);
	}

	@Override
	public ServiceResult modifyGBook(GBookVO gbook) {
		return null;
	}

	@Override
	public ServiceResult removeGBook(GBookVO gbook) {
		return null;
	}

}
