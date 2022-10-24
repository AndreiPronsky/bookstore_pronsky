import java.util.List;

public class Main {
    public static void main(String[] args) {
        User user = new User("Andrei", "Pronsky", "andrey.pronsky@mail.com", "1234", 1);
        UserDataAccess.create(user);
        UserDataAccess.update(user);
        System.out.println(UserDataAccess.find(5L));
        List<User> users = UserDataAccess.findAll();
        for (User u : users) {
            System.out.println(u);
        }
        System.out.println(UserDataAccess.deleteById(6L));
        UserDataAccess.printTableInfo();
    }
}