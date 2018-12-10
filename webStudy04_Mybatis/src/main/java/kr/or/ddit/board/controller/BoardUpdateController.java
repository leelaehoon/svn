package kr.or.ddit.board.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.filter.wrapper.FileUploadRequestWrapper;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.validator.GeneralValidator;
import kr.or.ddit.validator.UpdateGroup;
import kr.or.ddit.vo.BoardVO;

public class BoardUpdateController implements ICommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String method = req.getMethod();
			
		if ("get".equalsIgnoreCase(method)) {
			String bo_no = req.getParameter("what");
			if (!StringUtils.isNumeric(bo_no)) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return null;
			}
			IBoardService service = new BoardServiceImpl();
			BoardVO board = service.retrieveBoard(Long.parseLong(bo_no));
			req.setAttribute("board", board);
			return "board/boardForm";
		} else if ("post".equalsIgnoreCase(method)) {
			BoardVO board = new BoardVO();
			req.setAttribute("board", board);
			try {
				BeanUtils.populate(board, req.getParameterMap());
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
			String view = null;
			GeneralValidator validator = new GeneralValidator();
			Map<String, List<CharSequence>> errors = new LinkedHashMap<>();
			req.setAttribute("errors", errors);
			boolean valid = validator.validate(board, errors, UpdateGroup.class);
			if (valid) {
				if (req instanceof FileUploadRequestWrapper) {
					List<FileItem> fileItemList = ((FileUploadRequestWrapper) req).getFileItems("bo_file");
					board.setItemList(fileItemList);
				}
				IBoardService service = new BoardServiceImpl();
				ServiceResult result = service.modifyBoard(board);
				switch (result) {
				case INVALIDPASSWORD:
					req.setAttribute("message", "비밀번호가 일치하지 않습니다.");
					view = "board/boardForm";
					break;
				case FAILED:
					req.setAttribute("message", "서버 오류!!");
					view = "board/boardForm";
					break;
				default:
					// POST-redirect-GET : PRG패턴
					view = "redirect:/board/boardView.do?what=" + board.getBo_no();
					break;
				}
			} else {
				view = "board/boardForm";
			}
			return view;
		} else {
			resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

}
