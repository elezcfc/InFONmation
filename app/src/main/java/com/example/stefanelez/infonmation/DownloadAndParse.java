package com.example.stefanelez.infonmation;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.stefanelez.infonmation.model.Item;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stefan Elez on 22-Sep-16.
 */

public class DownloadAndParse {

    static final String KEY_ITEM = "item";
    static final String KEY_TITLE = "title";
    static final String KEY_LINK = "link";
    static final String KEY_DESCRIPTION = "description";

    public ArrayList<Item> readRSS (String urlAdresa){

        ArrayList<Item> list = new ArrayList<Item>();
        Item i = null;

        try {
            URL urlRss = new URL(urlAdresa);

            BufferedReader in = new BufferedReader(new InputStreamReader(urlRss.openStream(), "UTF8"));

            String line, line2, line3, title, link, desc1;
            title="";
            link="";
            desc1="";

			/*while((line = in.readLine()) != null){
				if(line.contains("<item>")){
					//if (line.contains("[RMT]")) {
						int firstPosition = line.indexOf("<title>");
						String temp = line.substring(firstPosition);
						temp = temp.replace("<title", "");
						int lastPosition = temp.indexOf("</title>");
						temp = temp.substring(0, lastPosition);
						sourceCode += temp + "\n";
					//}
				}
			}	*/
            int pFrom, pTo;
            while((line = in.readLine()) != null){
                if(line.contains("<item>")){
                    while(!(line2=in.readLine()).contains("</item>")){
                        //dok ne dodje do kraja item vrti ovu petlju
                        if(line2.contains("<title>")){
                            pFrom = line2.indexOf("<title>") + "<title>".length();
                            pTo = line2.indexOf("</title>");

                            title = line2.substring(pFrom, pTo);
                        }else{
                            if(line2.contains("<link>")){
                                link="";
                                if(line2.contains("</link>")){
                                    pFrom = line2.indexOf("<link>") + "<link>".length();
                                    pTo = line2.indexOf("</link>");

                                    link = line2.substring(pFrom, pTo);
                                }
                                else {
                                    pFrom = line2.indexOf("<link>") + "<link>".length();
                                    link+=line2.substring(pFrom, line2.length());

                                    while(!(line3=in.readLine()).contains("</link>")){
                                        link+=line3;
                                    }
                                    pTo = line3.lastIndexOf("</link>");
                                    link+=line3.substring(0, pTo);
                                }
                            }
                            if(line2.contains("<description>")){
                                desc1="";
                                if(line2.contains("</description>")){
                                    pFrom = line2.indexOf("<description>") + "<description>".length();
                                    pTo = line2.indexOf("</description>");

                                    desc1 = line2.substring(pFrom, pTo);
                                    desc1 = desc1.substring(9, desc1.length()-3);
                                }
                                else {
                                    pFrom = line2.indexOf("<description>") + "<description>".length();
                                    desc1+=line2.substring(pFrom, line2.length());
                                    while(!(line3=in.readLine()).contains("</link>")){
                                        desc1+=line3;
                                    }
                                    pTo = line3.lastIndexOf("</description>");
                                    desc1+=line3.substring(0, pTo);
                                    desc1 = desc1.substring(9, desc1.length()-3);
                                }
                            }
                        }

                    }
					/*if (line.contains("[RMT]")) {
						int firstPosition = line.indexOf("<title>");
						String temp = line.substring(firstPosition);
						temp = temp.replace("<title", "");
						int lastPosition = temp.indexOf("</title>");
						temp = temp.substring(0, lastPosition);
						sourceCode += temp + "\n";
					}*/

                }
                if(title!=""&&link!=""&&desc1!=""){
                    i = new Item();
                    i.setTitle(title);
                    i.setDescription(desc1);
                    i.setLink(link);
                    list.add(i);
                }


            }

            in.close();
            return list;


        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            System.out.println("Bad URL");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Something went wrong with reading");
        }
        return null;
    }

}
