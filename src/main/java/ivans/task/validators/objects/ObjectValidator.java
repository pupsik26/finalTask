package ivans.task.validators.objects;

import ivans.task.exceptions.InvalidDataException;

public interface ObjectValidator {
    void validate(Object object) throws InvalidDataException;
}
