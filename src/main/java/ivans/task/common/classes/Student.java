package ivans.task.common.classes;

public class Student {
    private String groupNumber;
    private double gpa;
    private String recordBookNumber;

    public Student(String groupNumber, double gpa, String recordBookNumber) {
        this.groupNumber = groupNumber;
        this.gpa = gpa;
        this.recordBookNumber = recordBookNumber;
    }

    public String getGroupNumber() { return groupNumber; }
    public double getGpa() { return gpa; }
    public String getRecordBookNumber() { return recordBookNumber; }

    public void setGroupNumber(String groupNumber) { this.groupNumber = groupNumber; }
    public void setGpa(double gpa) { this.gpa = gpa; }
    public void setRecordBookNumber(String recordBookNumber) { this.recordBookNumber = recordBookNumber; }

    @Override
    public String toString() {
        return "Student{groupNumber='" + groupNumber + "', gpa=" + gpa + ", recordBookNumber='" + recordBookNumber + "'}";
    }
}
