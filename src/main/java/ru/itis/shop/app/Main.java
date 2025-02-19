package ru.itis.shop.app;

import ru.itis.shop.users.controllers.UsersUIConsole;
import ru.itis.shop.users.models.User;
import ru.itis.shop.users.repositories.UsersRepository;
import ru.itis.shop.users.repositories.impl.UsersRepositoryFileImpl;
import ru.itis.shop.users.services.UsersService;
import ru.itis.shop.users.validators.EmailValidator;
import ru.itis.shop.users.validators.SimpleEmailValidator;

public class Main {
    // сборка компонентов системы и клиентский код
    public static void main(String[] args) {
        UsersRepository usersRepository = new UsersRepositoryFileImpl("users.txt");
        EmailValidator emailValidator = new SimpleEmailValidator();
        UsersService usersService = new UsersService(usersRepository, emailValidator);
        UsersUIConsole ui = new UsersUIConsole(usersService);
        //ui.printRegistrationMenu();
        usersRepository.delete("179ad7ef-c8d9-4083-beb6-a8cf795b012c");
        usersRepository.findById("9049db7f-9692-4267-88ae-315de524421d").get();
        usersRepository.update(new User("245dg5ry-f136-0409-lel2-u7vb234f654i", "Elinaa","elina@gmail.com","4782"));
        for (User user: usersRepository.findAll()) {
            System.out.println(user.toString());
        }
    }
}