package ivans.task.validators.objects;

import ivans.task.exceptions.InvalidDataException;

public interface ObjectValidator<T> {
    void validate(T object) throws InvalidDataException;
}
