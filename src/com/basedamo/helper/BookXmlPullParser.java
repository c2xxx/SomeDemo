package com.basedamo.helper;

import android.util.Xml;

import com.basedamo.data.Book;
import com.basedamo.utils.LogController;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hui on 2016/2/24.
 */
public class BookXmlPullParser {
    public List<Book> parse(InputStream is) throws Exception {
        List<Book> books = null;
        Book book = null;

        XmlPullParser parser = Xml.newPullParser();    //由android.util.Xml创建一个XmlPullParser实例
        parser.setInput(is, "UTF-8");                //设置输入流 并指明编码方式

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    LogController.d("START_DOCUMENT");
                    books = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG:
                    LogController.d("START_TAG");
                    if (parser.getName().equals("book")) {
                        book = new Book();
                    } else if (parser.getName().equals("id")) {
                        parser.next();
                        book.setId(Integer.parseInt(parser.getText()));
                    } else if (parser.getName().equals("name")) {
                        parser.next();
                        book.setName(parser.getText());
                    } else if (parser.getName().equals("price")) {
                        parser.next();
                        book.setPrice(Float.parseFloat(parser.getText()));
                    }
                    break;
                case XmlPullParser.END_TAG:
                    LogController.d("END_TAG");
                    if (parser.getName().equals("book")) {
                        books.add(book);
                        book = null;
                    }
                    break;
            }
            eventType = parser.next();
        }
        return books;
    }

    public String serialize(List<Book> books) throws Exception {
//		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//		XmlSerializer serializer = factory.newSerializer();

        XmlSerializer serializer = Xml.newSerializer();    //由android.util.Xml创建一个XmlSerializer实例
        StringWriter writer = new StringWriter();
        serializer.setOutput(writer);    //设置输出方向为writer
        serializer.startDocument("UTF-8", true);
        serializer.startTag("", "books");
        for (Book book : books) {
            serializer.startTag("", "book");
            serializer.attribute("", "id", book.getId() + "");

            serializer.startTag("", "name");
            serializer.text(book.getName());
            serializer.endTag("", "name");

            serializer.startTag("", "price");
            serializer.text(book.getPrice() + "");
            serializer.endTag("", "price");

            serializer.endTag("", "book");
        }
        serializer.endTag("", "books");
        serializer.endDocument();

        return writer.toString();
    }


}
