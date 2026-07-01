package ivans.task.validators.objects;

import ivans.task.common.classes.Student;
import ivans.task.exceptions.InvalidDataException;

class StudentValidator implements ObjectValidator<Student> {

    static final double MAX_GPA = 10;
    static final double MIN_GPA = 0;

    @Override
    public void validate(Student student) throws InvalidDataException {
        ValidationUtils.requireNonNull(student, "Student");
        validateGroupNumber(student.getGroupNumber());
        validateGpa(student.getGpa());
        validateRecordBookNumber(student.getRecordBookNumber());
    }

    private void validateGroupNumber(String groupNumber) throws InvalidDataException {
        ValidationUtils.requireNonEmptyText(groupNumber, "Номер группы", ValidationUtils.TEXT_WITH_DIGITS);
    }

    private void validateGpa(double gpa) throws InvalidDataException {
        ValidationUtils.requireInRange(gpa, MIN_GPA, MAX_GPA, "Средний балл");
    }

    private void validateRecordBookNumber(String recordBookNumber) throws InvalidDataException {
        ValidationUtils.requireNonEmptyText(recordBookNumber, "Номер зачётной книжки", ValidationUtils.TEXT_WITH_DIGITS);
    }
}
