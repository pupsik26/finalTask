package ModelBuilderClass;

import java.util.Objects;

public class Student {

    private final String groupNumber;
    private final double averageGrade;
    private final String recordBookNumber;

    private Student(Builder builder) {
        this.groupNumber = builder.groupNumber;
        this.averageGrade = builder.averageGrade;
        this.recordBookNumber = builder.recordBookNumber;
    }

    public static Builder builder() {
        return new Builder();
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Double.compare(student.averageGrade, averageGrade) == 0 &&
                Objects.equals(groupNumber, student.groupNumber) &&
                Objects.equals(recordBookNumber, student.recordBookNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupNumber, averageGrade, recordBookNumber);
    }

    public static class Builder {
        private String groupNumber;
        private double averageGrade;
        private String recordBookNumber;

        public Builder() {
        }

        public Builder setGroupNumber(String groupNumber) {
            this.groupNumber = groupNumber;
            return this;
        }

        public Builder setAverageGrade(double averageGrade) {
            this.averageGrade = averageGrade;
            return this;
        }

        public Builder setRecordBookNumber(String recordBookNumber) {
            this.recordBookNumber = recordBookNumber;
            return this;
        }
        public Student build() {
            return new Student(this);
        }
    }
}