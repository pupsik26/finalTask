package Builder;

import ModelClass.Student;

public class StudentBuilder extends Student {

    public StudentBuilder() {
        super();
    }

    public StudentBuilder setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
        return this;
    }

    public StudentBuilder setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
        return this;
    }

    public StudentBuilder setRecordBookNumber(String recordBookNumber) {
        this.recordBookNumber = recordBookNumber;
        return this;
    }
}