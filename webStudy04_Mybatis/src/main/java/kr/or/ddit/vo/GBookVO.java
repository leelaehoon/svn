package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.type.Alias;

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
	@NotBlank
	private Long gb_no;
	@NotNull
	private String gb_writer;
	@NotNull
	private String gb_pass;
	@NotNull
	private String gb_ip;
	@NotNull
	private String gb_content;
	@NotNull
	private String gb_date;
	
	private byte[] gb_image;
	
	private List<GBReplyVO> gbReplyList;
}
