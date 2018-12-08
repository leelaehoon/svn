package kr.or.ddit.crawling;

import java.io.IOException;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FirstCrawler {
	public static void main(String args[]) {
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet httpget = new HttpGet("https://sports.news.naver.com/kbaseball/record/index.nhn?category=kbo");
		try {
			httpClient.execute(httpget, new BasicResponseHandler() {
				@Override
				public String handleResponse(HttpResponse response) throws HttpResponseException, IOException {
					// 웹페이지를 그냥 갖어오면 한글이 깨져요. 인코딩 처리를 해야해요.
					String res = new String(super.handleResponse(response).getBytes("UTF-8"), "8859_1");
					Document doc = Jsoup.parse(res);
					Elements rows = doc.select("tbody#regularTeamRecordList_table tr");
					String[] items = new String[] { "팀", "경기수", "승", "패", "무", "승률", "게임차", "연속", "출루율", "장타율",
							"최근 10경기" };
					for (Element row : rows) {
						Iterator<Element> iterElem = row.getElementsByTag("td").iterator();
						StringBuilder builder = new StringBuilder();
						for (String item : items) {
							builder.append(item + ": " + iterElem.next().text() + "   \t");
						}
						System.out.println(builder.toString());
					}
					return res;
				}
			});
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
