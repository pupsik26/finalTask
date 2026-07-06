package ivans.task.validators.objects;

import ivans.task.exceptions.InvalidDataException;

interface TypeValidator<T> {
    void validate(T object) throws InvalidDataException;
}
