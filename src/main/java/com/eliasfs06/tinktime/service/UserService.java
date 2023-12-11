package com.eliasfs06.tinktime.service;

import com.eliasfs06.tinktime.model.Funcionario;
import com.eliasfs06.tinktime.model.Person;
import com.eliasfs06.tinktime.model.User;
import com.eliasfs06.tinktime.model.UserRole;
import com.eliasfs06.tinktime.model.dto.RegisterDTO;
import com.eliasfs06.tinktime.repository.GenericRepository;
import com.eliasfs06.tinktime.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService extends GenericService<User>{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PersonService personService;
    @Autowired
    private FuncionarioService funcionarioService;
    @Autowired
    private ClientService clientService;

    public UserService(GenericRepository<User> userRepository) {
        super(userRepository);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User findByID(Long id){
        return userRepository.findById(id).get();
    }

    public void registerUser(RegisterDTO registerData){
        Person person = registerData.toPerson();
        User user = registerData.toUser();

        encodePassword(user);

        Person personSaved = personService.save(person);
        user.setPerson(personSaved);
        userRepository.save(user);
        createEntityBaseOnUserRole(user);
    }

    private void createEntityBaseOnUserRole(User user) {
        if(user.getUserRole() == UserRole.EMPLOYEE){
            funcionarioService.createFuncionario(user);
        }
        if(user.getUserRole() == UserRole.CLIENT){
            clientService.createClient(user);
        }
    }

    public void encodePassword(User user){
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
    }



}
