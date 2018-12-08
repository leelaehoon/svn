package kr.or.ddit.crawling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.core.util.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SecondCrawler {
	public static void main(String[] args) throws ClientProtocolException, IOException {
		// 1. 가져올 HTTP 주소 셋팅
		HttpPost http = new HttpPost("https://sports.news.naver.com/kbaseball/record/index.nhn?category=kbo");
		// 2. 가져오기를 실행할 클라이언트 객체 생성
		HttpClient httpClient = HttpClientBuilder.create().build();
		// 3. 실행 및 실행데이터를 Response 객체에 담음
		HttpResponse response = httpClient.execute(http);
		// 4. Response 받은 데이터 중, DOM 데이터를 가져와 Entity에 담음 
		HttpEntity entity = response.getEntity();
		// 5. Charset을 알아내기 위해 DOM의 컨텐트 타입을 가져와 담고 Charset을 가져옴
		ContentType contentType = ContentType.getOrDefault(entity);
		Charset charSet = contentType.getCharset();
		// 6. DOM데이터를 한 줄씩 읽기 위해 Reader에 담음
		BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), charSet));
		// 7. 가져온 DOM데이터를 담기위한 그릇
		StringBuffer sb = new StringBuffer();
		String line = "";
		// 8. DOM 데이터 가져오기
		while ((line = br.readLine()) != null) {
			sb.append(line + "\n");
		}
		// 9. 가져온 DOM 데이터 출력
		// 10. Jsoup으로 파싱
		Document doc = Jsoup.parse(sb.toString());
		String pattern = "%s : %5s\t";
		String[] items = new String[] { "팀", "경기수", "승", "패", "무", "승률", "게임차", "연속", "출루율", "장타율", "최근 10경기" };
		Elements rows = doc.select("tbody#regularTeamRecordList_table tr");
		for (Element row : rows) {
			Iterator<Element> it = row.getElementsByTag("td").iterator();
			for (String item : items) {
				System.out.print(String.format(pattern, item, it.next().text()));
			}
			System.out.println();
		}
	}
}
