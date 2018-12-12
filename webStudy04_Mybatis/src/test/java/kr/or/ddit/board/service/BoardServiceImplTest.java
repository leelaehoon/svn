package kr.or.ddit.board.service;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.BoardException;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.PdsVO;

public class BoardServiceImplTest {
	IBoardService service = new BoardServiceImpl();
	BoardVO exist = new BoardVO();
	BoardVO invalidPass = new BoardVO();
	BoardVO newBoard = new BoardVO();
	PagingInfoVO<BoardVO> pagingVO = new PagingInfoVO<>();
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		exist.setBo_no(1L);
		exist.setBo_writer("김은대");
		exist.setBo_pass("asdfasdf");
		
		invalidPass.setBo_no(1L);
		invalidPass.setBo_writer("김은대");
		invalidPass.setBo_pass("1234");
		
		newBoard.setBo_writer("test");
		newBoard.setBo_pass("test");
		newBoard.setBo_ip("test");
		newBoard.setBo_title("test");
		
		pagingVO.setSearchType("content");
		pagingVO.setSearchWord("은대");
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testCreateBoard() {
		ServiceResult result = service.createBoard(newBoard);
		assertThat(result, equalTo(ServiceResult.SUCCESS));
	}

	@Test
	public void testRetrieveBoardCount() {
		long totalCount = service.retrieveBoardCount(pagingVO);
		assertThat(totalCount, greaterThan(0L));
	}

	@Test
	public void testRetrieveBoardList() {
		pagingVO.setCurrentPage(1);
		List<BoardVO> boardList = service.retrieveBoardList(pagingVO);
		assertNotNull(boardList);
		assertThat(boardList.size(), greaterThan(0));
	}

	@Test
	public void testRetrieveBoard() {
		BoardVO success = service.retrieveBoard(exist.getBo_no());
		assertNotNull(success);
	}
	
	@Test(expected=BoardException.class)
	public void testRetrieveBoardException() {
		BoardVO exception = service.retrieveBoard(1000);
	}
	
	@Test
	public void testModifyBoard() {
		invalidPass.setBo_content("test");
		exist.setBo_content("test");
		ServiceResult invalidPassword = service.modifyBoard(invalidPass);
		ServiceResult success = service.modifyBoard(exist);
		assertThat(invalidPassword, equalTo(ServiceResult.INVALIDPASSWORD));
		assertThat(success, equalTo(ServiceResult.SUCCESS));
	}

	@Test
	public void testRemoveBoard() {
		ServiceResult invalidPassword = service.removeBoard(invalidPass);
		assertThat(invalidPassword, equalTo(ServiceResult.INVALIDPASSWORD));
	}

	@Test(expected=BoardException.class)
	public void testDownloadPdsException() {
		PdsVO pds = service.downloadPds(1000);
	}
}
