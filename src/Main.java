import online.javaclass.bookstore.Entities.User;
import online.javaclass.bookstore.UserDao.UserDataAccess;
import online.javaclass.bookstore.UserInfo.UserTableInfo;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        User user = new User("Pavel", "Asinov", "andrey.pronsky@mail.com", "1234", 1);
        UserDataAccess.create(user);
        UserDataAccess.update(user);
        System.out.println(UserDataAccess.find(18L));
        List<User> users = UserDataAccess.findAll();
        for (User u : users) {
            System.out.println(u);
        }
        System.out.println(UserDataAccess.deleteById(6L));
        UserTableInfo.printTableInfo(UserDataAccess.URL, UserDataAccess.USER, UserDataAccess.PASSWORD);
    }
}