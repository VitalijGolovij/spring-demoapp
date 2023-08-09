package ru.example.demoapp.sevice;
import ru.example.demoapp.model.User;
import ru.example.demoapp.repository.UserRepository;


public interface RegisterService {
    public void register(User user);
}
