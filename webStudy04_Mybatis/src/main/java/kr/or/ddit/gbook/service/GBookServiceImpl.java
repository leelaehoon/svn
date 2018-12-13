package kr.or.ddit.gbook.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.encrypt.EncryptionUtil;
import kr.or.ddit.gbook.GBookException;
import kr.or.ddit.gbook.dao.GBookDAOImpl;
import kr.or.ddit.gbook.dao.IGBookDAO;
import kr.or.ddit.vo.GBookVO;
import kr.or.ddit.vo.PagingInfoVO;

public class GBookServiceImpl implements IGBookService {
	IGBookDAO gbookDAO = new GBookDAOImpl();
	
	@Override
	public ServiceResult createGBook(GBookVO gbook) {
		ServiceResult result = ServiceResult.FAILED;
		try {
			byte[] encrypted = EncryptionUtil.sha512Encrypt(gbook.getGb_pass());
			String encoded = EncryptionUtil.base64Encode(encrypted);
			gbook.setGb_pass(encoded);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		if (gbookDAO.insertGBook(gbook) > 0) {
			result = ServiceResult.SUCCESS;
		}
		return result;
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
		ServiceResult result = ServiceResult.FAILED;
		GBookVO check = gbookDAO.selectGBook(gbook.getGb_no());
		if (check!=null) {
			boolean valid = false;
			try {
				valid = EncryptionUtil.equalsTo(gbook.getGb_pass(), check.getGb_pass());
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			}
			if (valid) {
				if (gbookDAO.deleteGBook(gbook.getGb_no()) > 0) {
					result = ServiceResult.SUCCESS;
				}
			} else {
				result = ServiceResult.INVALIDPASSWORD;
			}
		} else {
			throw new GBookException();
		}
		return result;
	}

}
