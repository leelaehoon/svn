package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of={"mem_id","mem_regno1","mem_regno2"})
public class MemberVO implements Serializable{
	public MemberVO(String mem_id, String mem_pass) {
		this.mem_id = mem_id;
		this.mem_pass = mem_pass;
	}
	
	private String mem_id;
	private String mem_pass;
	private String mem_name;
	private String mem_regno1;
	private String mem_regno2;
	private String mem_bir;
	private String mem_zip;
	private String mem_add1;
	private String mem_add2;
	private String mem_hometel;
	private String mem_comtel;
	private String mem_hp;
	private String mem_mail;
	private String mem_job;
	private String mem_like;
	private String mem_memorial;
	private String mem_memorialday;
	private Long mem_mileage;
	private String mem_delete;
	private String mem_auth;
	// 구매 상품 목록
	private List<ProdVO> prodList;
	
	public String getAddress() {
		return Objects.toString(mem_add1, "") + " " + Objects.toString(mem_add2,"");
	}
}
