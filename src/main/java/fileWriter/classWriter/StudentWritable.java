package fileWriter.classWriter;

import daryaClassStream.ModelBuilderClass.Student;
import dataSource.fileReader.HeadersConst;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class StudentWritable implements Writable<Student> {
    private Student student;

    public StudentWritable() {}

    public void setItem(Student student) {
        this.student = student;
    }

    @Override
    public String getCsvHeaders(String separator) {
        return HeadersConst.STUDENT_GROUP + separator + HeadersConst.STUDENT_GPA + separator + HeadersConst.STUDENT_NUMBER + "\n";
    }

    @Override
    public String getCsvLine(String separator) {
        return student.getGroupNumber() + separator + student.getAverageGrade() + separator + student.getRecordBookNumber() + "\n";
    }

    @Override
    public Element getXmlRoot(Document dom) {
        return dom.createElement("students");
    }

    @Override
    public Element getXmlElement(Document dom) {
        Element elem = dom.createElement("student");
        elem.setAttribute(HeadersConst.STUDENT_GROUP, student.getGroupNumber());
        elem.setAttribute(HeadersConst.STUDENT_GPA, String.valueOf(student.getAverageGrade()));
        elem.setAttribute(HeadersConst.STUDENT_NUMBER, student.getRecordBookNumber());
        return elem;
    }
}
