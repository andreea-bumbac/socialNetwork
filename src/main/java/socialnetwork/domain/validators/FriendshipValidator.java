package socialnetwork.domain.validators;

import socialnetwork.domain.Friendship;

public class FriendshipValidator implements Validator<Friendship>{


    @Override
    public void validate(Friendship friend){
        if (friend.getId1() == null || friend.getId2() == null)
            throw new ValidationException("Friendship ids invalid!\n");

        if (friend == null)
            throw new ValidationException("Friendship shouldn't be null!\n");

        if (friend.getId1().equals(friend.getId2()))
            throw new ValidationException("Ids should be different!\n");
    }
}
