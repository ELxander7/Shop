package ru.itis.shop.users.repositories.impl;

import ru.itis.shop.users.models.User;
import ru.itis.shop.users.repositories.UsersRepository;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryFileImpl implements UsersRepository {
    private final String fileName;

    public UsersRepositoryFileImpl(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void save(User user) {
        // try-with-resources
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))){
            writer.write(user.getId() + "|" + user.getName() + "|" + user.getEmail() + "|" + user.getPassword());
            writer.newLine();
        } catch (IOException e) { // перехватываю проверяемое исключение
            throw new IllegalStateException(e); // пробрасываем непроверяемое поверх, чтобы остановить цикл работы программы
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new LinkedList<User>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String readingUser;
            while ((readingUser = reader.readLine()) != null){
                String[] userData = readingUser.split("\\|");
                users.add(new User(userData[0], userData[1], userData[2], userData[3]));
            }
        } catch (IOException e){
            throw new IllegalStateException(e);
        }

        return null;
    }

    @Override
    public void update(User user) {
        List<User> savedUsers = findAll();
        savedUsers.removeIf(user1 -> user1.getId().equals(user.getId()));
        savedUsers.add(user);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
            for (User savedUser: savedUsers){
                writer.write(savedUser.getId() + "|" + savedUser.getName() + "|" + savedUser.getEmail() + "|" + savedUser.getPassword());
                writer.newLine();
            }
        } catch (IOException e){
            throw new IllegalStateException(e);
        }

    }

    @Override
    public void delete(String id) {
        List<User> users = findAll();
        users.removeIf(user -> user.getId().equals(id));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
            for (User savedUser: users){
                writer.write(savedUser.getId() + "|" + savedUser.getName() + "|" + savedUser.getEmail() + "|" + savedUser.getPassword());
                writer.newLine();
            }
        } catch (IOException e){
            throw new IllegalStateException(e);
        }

    }

    @Override
    public Optional<User> findById(String id) {
        for (User user: findAll()){
            if (user.getId().equals(id)){
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }
}
