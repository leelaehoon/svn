package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Alias("pdsVO")
public class PdsVO implements Serializable{
	public PdsVO(FileItem fileItem) {
		super();
		this.fileItem = fileItem;
		this.pds_size = fileItem.getSize();
		this.pds_fancysize = FileUtils.byteCountToDisplaySize(fileItem.getSize());
		this.pds_filename =fileItem.getName();
		this.pds_mime =fileItem.getContentType();
		this.pds_savename = UUID.randomUUID().toString();
	}
	private Long pds_no;
	private Long bo_no;
	private String pds_filename;
	private String pds_savename;
	private String pds_mime;
	private Long pds_size;
	private String pds_fancysize;
	private FileItem fileItem;
}