package socialnetwork.domain.validators;

import socialnetwork.domain.User;

public class UserValidator implements Validator<User> {
    @Override
    public void validate(User entity) throws ValidationException {

        if(entity == null)
            throw new ValidationException("Entity shouldn't be null!\n'");

        if(entity.getFirstName().equals("") || entity.getFirstName() == null)
            throw new ValidationException("Invalid first name!\n");

        if(entity.getLastName().equals("") || entity.getLastName() == null)
            throw new ValidationException("Invalid last name!\n");

        if(entity.getEmail().equals("") || entity.getEmail() == null)
            throw new ValidationException("Invalid email!\n");
    }
}
