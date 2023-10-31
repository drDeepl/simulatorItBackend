package ru.simbirgo.services;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.simbirgo.exceptions.AccountExistsException;
import ru.simbirgo.exceptions.AccountNotExistsException;
import ru.simbirgo.models.User;
import ru.simbirgo.repositories.AccountRepository;

import java.util.List;

@Service
public class UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ModelMapper modelMapper;



    public User updateAccount(String currentUsername, String updateUsername, String password) throws AccountExistsException{
        if(accountRepository.existsByUsername(updateUsername)){
            new AccountExistsException("аккаунт с таким именем пользователя уже существует");
        }
        User user = accountRepository.findByUsername(currentUsername).get();
        user.setUsername(updateUsername);
        user.setPassword(password);
        return accountRepository.save(user);
    }

    public User getAccountById(Long id){
        LOGGER.info("GET ACCOUNT BY ID");
        return accountRepository.findById(id).orElseThrow(() -> new AccountNotExistsException(String.format("аккаунта с id %s не существует", id)));
    }

    public List<User> getAccounts(){
        LOGGER.info("GET ACCOUNTS");
        List<User> users = accountRepository.findAccounts();
        LOGGER.info("GETTING ACCOUNTS");
        return users;
    }

    public void updateAccountById(long accountId, String username, String password, String gender, boolean isAdmin){
        User user = accountRepository.findById(accountId).get();
        user.setUsername(username);
        user.setPassword(password);
        user.setIsAdmin(isAdmin);
        user.setGender(gender);
        accountRepository.save(user);
    }
}
