package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.ibatis.type.Alias;

import kr.or.ddit.validator.InsertGroup;
import kr.or.ddit.validator.UpdateGroup;
import kr.or.ddit.validator.rules.constraints.NotBlank;
import kr.or.ddit.validator.rules.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of= {"gb_no", "gb_writer"})
@Alias("gbookVO")
public class GBookVO implements Serializable{
	public GBookVO(Long gb_no, String gb_pass) {
		super();
		this.gb_no = gb_no;
		this.gb_pass = gb_pass;
	}

	@NotNull(groups= {UpdateGroup.class})
	private Long gb_no;
	@NotBlank(groups= {InsertGroup.class})
	private String gb_writer;
	@NotBlank(groups= {InsertGroup.class})
	private String gb_pass;
	@NotBlank(groups= {InsertGroup.class})
	private String gb_ip;
	@NotBlank(groups= {InsertGroup.class})
	private String gb_content;
	private String gb_date;
	
	private byte[] gb_image;
	
	private List<GBReplyVO> gbreplyList;
	
	public String getGb_imgToBase64() {
		if (gb_image == null) {
			return null;
		} else {
			return Base64.encodeBase64String(gb_image);
		}
	}
}
