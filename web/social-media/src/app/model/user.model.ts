export default interface User {
  id: string;
  email: string;
  firstName: string;
  lastName: string;
  username: string;
  friends: Friendship[];
}

interface Friendship {
  id: string;
  friend: User;
  requester: User;
  status: FriendshipStatus;
}

enum FriendshipStatus {
  ACCEPTED = "ACCEPTED",
  PENDING = "PENDING",
  REJECTED = "REJECTED"
}
