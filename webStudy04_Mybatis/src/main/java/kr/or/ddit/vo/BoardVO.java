package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.type.Alias;

import kr.or.ddit.filter.wrapper.FileUploadRequestWrapper;
import kr.or.ddit.validator.rules.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of= {"bo_no", "bo_writer"})
@Alias("boardVO")
public class BoardVO implements Serializable{
	
	public BoardVO(Long bo_no, String bo_pass) {
		super();
		this.bo_no = bo_no;
		this.bo_pass = bo_pass;
	}

	private Long bo_no;
	private Integer bo_level;
	private Long bo_parent;
	@NotBlank
	private String bo_writer;
	@NotBlank
	private String bo_pass;
	@NotBlank
	private String bo_ip;
	private String bo_mail;
	@NotBlank
	private String bo_title;
	private String bo_content;
	private String bo_date;
	private Long bo_hit;
	private Long bo_rcmd;
	
	private List<PdsVO> pdsList;
	private List<ReplyVO> replyList;
	private Long[] delFiles;
	
	private List<FileItem> fileItemList;
	
	public void setItemList(List<FileItem> fileItemList) {
		List<PdsVO> pdsList = null;
		if (fileItemList != null) {
			pdsList = new ArrayList<>();
			for (FileItem fileItem : fileItemList) {
				pdsList.add(new PdsVO(fileItem));
			}
			this.pdsList = pdsList;
		}
	}
}
