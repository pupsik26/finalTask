package fileWriter.classWriter;

import ModelBuilderClass.User;
import dataSource.fileReader.HeadersConst;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class UserWritable implements Writable<User> {
    private User user;

    public UserWritable() {}

    public void setItem(User user) {
        this.user = user;
    }

    @Override
    public String getCsvHeaders(String separator) {
        return HeadersConst.USER_NAME + separator + HeadersConst.USER_PASSWORD + separator + HeadersConst.USER_EMAIL + "\n";
    }

    @Override
    public String getCsvLine(String separator) {
        return user.getName() + separator + user.getPassword() + separator + user.getEmail() + "\n";
    }

    @Override
    public Element getXmlRoot(Document dom) {
        return dom.createElement("users");
    }

    @Override
    public Element getXmlElement(Document dom) {
        Element elem = dom.createElement("user");
        elem.setAttribute(HeadersConst.USER_NAME, user.getName());
        elem.setAttribute(HeadersConst.USER_PASSWORD, user.getPassword());
        elem.setAttribute(HeadersConst.USER_EMAIL, user.getEmail());
        return elem;
    }
}
