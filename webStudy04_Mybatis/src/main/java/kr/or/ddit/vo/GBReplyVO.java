package kr.or.ddit.vo;

import java.io.Serializable;

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
@EqualsAndHashCode(of= {"gbr_no", "gb_no"})
@Alias("gbreplyVO")
public class GBReplyVO implements Serializable{
	public GBReplyVO(Long gbr_no, String gbr_pass) {
		super();
		this.gbr_no = gbr_no;
		this.gbr_pass = gbr_pass;
	}
	
	@NotNull(groups= {UpdateGroup.class})
	private Long gbr_no;
	@NotNull(groups= {InsertGroup.class})
	private Long gb_no;
	@NotBlank(groups= {InsertGroup.class})
	private String gbr_writer;
	@NotBlank(groups= {InsertGroup.class})
	private String gbr_pass;
	@NotBlank(groups= {InsertGroup.class})
	private String gbr_ip;
	@NotBlank(groups= {InsertGroup.class})
	private String gbr_content;
	private String gbr_date;
}
