package ivans.task.validators.objects;

import ModelBuilderClass.ModelClass.User;
import ivans.task.exceptions.InvalidDataException;

class UserValidator implements TypeValidator<User> {

    static final int MIN_PASSWORD_LENGTH = 6;

    @Override
    public void validate(User user) throws InvalidDataException {
        ValidationUtils.requireNonNull(user, "User");
        validateName(user.getName());
        validatePassword(user.getPassword());
        validateEmail(user.getEmail());
    }

    private void validateName(String name) throws InvalidDataException {
        ValidationUtils.requireNonEmptyText(name, "Имя пользователя", ValidationUtils.TEXT_LETTERS_ONLY);
    }

    private void validatePassword(String password) throws InvalidDataException {
        if (password == null || password.trim().isEmpty()) {
            throw new InvalidDataException("Пароль не может быть пустым");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidDataException("Пароль должен содержать минимум " + MIN_PASSWORD_LENGTH + " символов");
        }
    }

    private void validateEmail(String email) throws InvalidDataException {
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidDataException("Email не может быть пустым");
        }
        if (!ValidationUtils.EMAIL.matcher(email).matches()) {
            throw new InvalidDataException("Некорректный email: " + email);
        }
    }
}
