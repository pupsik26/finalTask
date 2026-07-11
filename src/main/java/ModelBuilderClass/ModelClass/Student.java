package ModelBuilderClass.ModelClass;

import java.util.Objects;

public class Student {

    protected String groupNumber;
    protected double averageGrade;
    protected String recordBookNumber;

    protected Student() {
    }

    protected Student(String groupNumber, double averageGrade, String recordBookNumber) {
        this.groupNumber = groupNumber;
        this.averageGrade = averageGrade;
        this.recordBookNumber = recordBookNumber;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public String getRecordBookNumber() {
        return recordBookNumber;
    }

    @Override
    public String toString() {
        return "Student{group='" + groupNumber + "', gpa=" + averageGrade +
                ", book='" + recordBookNumber + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        return Double.compare(student.averageGrade, averageGrade) == 0 &&
                Objects.equals(groupNumber, student.groupNumber) &&
                Objects.equals(recordBookNumber, student.recordBookNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupNumber, averageGrade, recordBookNumber);
    }
}