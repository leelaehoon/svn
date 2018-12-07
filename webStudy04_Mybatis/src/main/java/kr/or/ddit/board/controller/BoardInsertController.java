package kr.or.ddit.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.security.Provider.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.dao.IPdsDAO;
import kr.or.ddit.board.dao.PdsDAOImpl;
import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.filter.wrapper.FileUploadRequestWrapper;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.validator.GeneralValidator;
import kr.or.ddit.validator.InsertGroup;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PdsVO;

public class BoardInsertController implements ICommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// 1. V.L : board/boardForm
		// 2. 게시글에 첨부파일이 최대 3건 (partname = bo_file)
		// 3. 첨부파일 저장 위치 : d:/boardFiles
		String method = req.getMethod();
		if ("get".equalsIgnoreCase(method)) {
			return "board/boardForm";
		} else if ("post".equalsIgnoreCase(method)) {
			BoardVO board = new BoardVO();
			try {
				BeanUtils.populate(board, req.getParameterMap());
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
			GeneralValidator validator = new GeneralValidator();
			Map<String, List<CharSequence>> errors = new LinkedHashMap<>();
			req.setAttribute("errors", errors);
			boolean valid = validator.validate(board, errors, InsertGroup.class);
			String view = null;
			if (valid) {
				if (req instanceof FileUploadRequestWrapper) {
					List<FileItem> fileItemList = ((FileUploadRequestWrapper) req).getFileItems("bo_file");
					board.setItemList(fileItemList);
				}
				IBoardService service = new BoardServiceImpl();
				ServiceResult result = service.createBoard(board);
				if (ServiceResult.SUCCESS.equals(result)) {
					view = "redirect:/board/boardList.do";
				} else {
					req.setAttribute("message", "서버오류");
					view = "board/boardForm";
				}
			} else {
				view = "board/boardForm";
			}
			return view;
		} else {
			resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
		
		
		
		
		
		
		
		
//		if ("get".equalsIgnoreCase(method)) {
//			return "board/boardForm";
//		} else if ("post".equalsIgnoreCase(method)) {
//			BoardVO board = new BoardVO();
//			req.setAttribute("board", board);
//			try {
//				BeanUtils.populate(board, req.getParameterMap());
//			} catch (IllegalAccessException | InvocationTargetException e) {
//				throw new RuntimeException(e);
//			}
//			GeneralValidator validator = new GeneralValidator();
//			Map<String, List<CharSequence>> errors = new HashMap<>();
//			req.setAttribute("errors", errors);
//			String view = null;
//			boolean valid = validator.validate(board, errors, InsertGroup.class);
//			if (valid) {
//				// 파일아이템 꺼내서 boaedVO에 담기
//				if (req instanceof FileUploadRequestWrapper) {
//					List<FileItem> fileItemList = ((FileUploadRequestWrapper) req).getFileItems("bo_file");
//					if (fileItemList != null && fileItemList.size() > 0) {
//						for (FileItem fileItem : fileItemList) {
//							if (fileItem == null) continue;
//							String saveName = UUID.randomUUID().toString();
//							PdsVO pds = new PdsVO();
//							pds.setFileItem(fileItem);
//							pds.setPds_filename(fileItem.getName());
//							pds.setPds_savename(saveName);
//							pds.setPds_mime(fileItem.getContentType());
//							pds.setPds_size(fileItem.getSize());
//							String fancysize = FileUtils.byteCountToDisplaySize(fileItem.getSize());
//							pds.setPds_fancysize(fancysize);
//							List<PdsVO> pdsList =  board.getPdsList();
//							if (pdsList == null) {
//								pdsList = new ArrayList<>();
//								board.setPdsList(pdsList);
//							}
//							pdsList.add(pds);
//						}
//					}
//				}
//				
//				IBoardService service = new BoardServiceImpl();
//				ServiceResult result = service.createBoard(board);
//				if (ServiceResult.SUCCESS.equals(result)) {
//					List<PdsVO> pdsList = board.getPdsList();
//					if (pdsList != null && pdsList.size() > 0) {
//						String boardFilesPath = "D:/boardFiles";
//						File boardFilesFolder = new File(boardFilesPath);
//						for (PdsVO pds : pdsList) {
//							File saveFile = new File(boardFilesFolder, pds.getPds_savename());
//							FileItem fileItem = pds.getFileItem();
//							try (
//									InputStream in = fileItem.getInputStream();
//							) {
//								FileUtils.copyInputStreamToFile(in, saveFile);
//							}
//						}
//					}
//					view = "redirect:/board/boardView.do?what=" + board.getBo_no();
//				} else {
//					req.setAttribute("message", "서버오류!!");
//					view = "board/boardForm";
//				}
//			} else {
//				view = "board/boardForm";
//			}
//			return view;
//		} else {
//			resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
//			return null;
//		}
	}

}
