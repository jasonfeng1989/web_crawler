package web_crawler;


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


public class DealMoon 
{
	static List<Item> resultList = new ArrayList<Item>();
	
	//input: url
	//output: the list of items on this url page
	public static List<Item> DealmoonPageCrawler (String URL) throws IOException 
	{
		/*String html1 = "<html><head><title>First parse</title></head>"
				  + "<body><p>Parsed HTML into a doc.</p></body></html>";*/
		//System.out.println("helloworld");
		List<Item> itemList = new ArrayList<Item>();
		Document DealMoonDoc = Jsoup.connect(URL).get();
		Element tagDivCon_Lecon = DealMoonDoc.select("div.con_lecon").first();
		Elements tagUL = tagDivCon_Lecon.getElementsByTag("ul");
		Elements tagA = tagUL.select("h2>a");
		for (Element eachTagA : tagA)
		{
			Item item = new Item(eachTagA.ownText(),"www.dealmoon.com"+eachTagA.attr("href"));
			itemList.add(item);
		}
		/*
		for (Item item : itemList)
		{
			System.out.println(item.keyword);
			System.out.println(item.link);
		}
		*/
		return itemList;		
	}
	
	//append the item lists of certain pages
	//output: the list of items of the certain pages
	public static void DealmoonWebsiteCrawler () throws IOException
	{
		int pageNumber=1;
		for (pageNumber=1; pageNumber<=2; pageNumber++)
			resultList.addAll(DealmoonPageCrawler("http://www.dealmoon.com/"+Integer.toString(pageNumber)));

	}
	
	//input: user keyword list, eg {"Canon 6D", "O'Real Cream"}
	//output: matched items
	public static List<Item> Match (String[] userKeywordList) 
	{
		List<Item> matchedItem = new ArrayList<Item>();
		for (Item eachItem : resultList)
		{
			String itemKeyLow=eachItem.keyword.toLowerCase();
			for (String eachUserKeyword : userKeywordList)
			{
				String eachUserKeywordLow=eachUserKeyword.toLowerCase();
				String[] eachUserKeySplit=eachUserKeywordLow.split(" ");
				int found=1;
				for (String eachUserKeywordSubstring : eachUserKeySplit)
					if (! itemKeyLow.contains(eachUserKeywordSubstring))
					{
						found=0;
						break;
					}
				if (found==1)
				{
					System.out.println(eachUserKeyword+" Found in "+eachItem.keyword);
					matchedItem.add(eachItem);		
					break;
				}
			}
		}
		return matchedItem;
	}
	
	
	public static void main (String[] args) throws IOException
	{
		DealmoonWebsiteCrawler();
		String[] keywordList = {"canon g12", "asus infinity", "canon 6d"};
		List<Item> matchedList = Match (keywordList);
		//System.out.println("helloworld");
		for (Item item : matchedList)
		{
			System.out.println(item.keyword);
			System.out.println(item.link);
		}
	}
	
	
}
