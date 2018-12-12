package kr.or.ddit.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

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
	@NotBlank
	private Long gbr_no;
	@NotBlank
	private Long gb_no;
	@NotNull
	private String gbr_writer;
	@NotNull
	private String gbr_pass;
	@NotNull
	private String gbr_ip;
	@NotNull
	private String gbr_content;
	@NotNull
	private String gbr_date;
}
