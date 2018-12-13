package kr.or.ddit.gbook.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.encrypt.EncryptionUtil;
import kr.or.ddit.gbook.GBookException;
import kr.or.ddit.gbook.dao.GBReplyDAOImpl;
import kr.or.ddit.gbook.dao.IGBReplyDAO;
import kr.or.ddit.vo.GBReplyVO;
import kr.or.ddit.vo.PagingInfoVO;

public class GBReplyServiceImpl implements IGBReplyService {
	IGBReplyDAO gbreplyDAO = new GBReplyDAOImpl();

	@Override
	public ServiceResult createGBReply(GBReplyVO gbreply) {
		ServiceResult result = ServiceResult.FAILED;
		try {
			byte[] encrypted = EncryptionUtil.sha512Encrypt(gbreply.getGbr_pass());
			String encoded = EncryptionUtil.base64Encode(encrypted);
			gbreply.setGbr_pass(encoded);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		if (gbreplyDAO.insertGBReply(gbreply) > 0) {
			result = ServiceResult.SUCCESS;
		}
		return result;
	}

	@Override
	public long retrieveTotalCount(PagingInfoVO<GBReplyVO> pagingVO) {
		return 0;
	}

	@Override
	public List<GBReplyVO> retrieveGBReplyList(PagingInfoVO<GBReplyVO> pagingVO) {
		return null;
	}

	@Override
	public ServiceResult modifyGBReply(GBReplyVO gbreply) {
		return null;
	}

	@Override
	public ServiceResult removeGBReply(GBReplyVO gbreply) {
		ServiceResult result = ServiceResult.FAILED;
		GBReplyVO check = gbreplyDAO.selectGBReply(gbreply.getGbr_no());
		if (check != null) {
			if (check.getGbr_pass().equals(gbreply.getGbr_pass())) {
				if (gbreplyDAO.deleteGBReply(gbreply.getGbr_no()) > 0) {
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
