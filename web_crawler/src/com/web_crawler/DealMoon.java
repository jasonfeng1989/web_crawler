package com.web_crawler;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;


public class DealMoon {
	public static void main(String[] args) throws IOException {
		/*String html1 = "<html><head><title>First parse</title></head>"
				  + "<body><p>Parsed HTML into a doc.</p></body></html>";*/
		List<Item> itemList = new ArrayList<Item>();
		Document DealMoonDoc = Jsoup.connect("http://www.dealmoon.com").get();
		Element tagDivCon_Lecon = DealMoonDoc.select("div.con_lecon").first();
		Elements tagUL = tagDivCon_Lecon.getElementsByTag("ul");
		Elements tagA = tagUL.select("h2>a");
		for (Element eachTagA : tagA)
		{
			Item item = new Item(eachTagA.ownText(),"www.dealmoon.com"+eachTagA.attr("href"));
			itemList.add(item);
		}
		
		for (Item item : itemList)
		{
			System.out.println(item.keyword);
			System.out.println(item.link);
		}
	}
}
