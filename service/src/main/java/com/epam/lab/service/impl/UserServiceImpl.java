package com.epam.lab.service.impl;

import com.epam.lab.dto.entity.UserDto;
import com.epam.lab.mapper.UserMapper;
import com.epam.lab.exception.ServiceException;
import com.epam.lab.model.User;
import com.epam.lab.repository.EntityRepository;
import com.epam.lab.service.UserService;
import com.epam.lab.specification.user.UserByNameSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService {

    private EntityRepository<User> userRepository;
    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(EntityRepository<User> userRepository, UserMapper userMapper) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDto add(UserDto userDto) {
        User user = userRepository.addEntity(userMapper.toEntity(userDto));
        return userMapper.toDto(user);
    }

    @Transactional
    public UserDto update(UserDto userDto) {
        User user = userRepository.updateEntity(userMapper.toEntity(userDto));
        return userMapper.toDto(user);
    }


    public void delete(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        userRepository.removeEntity(user);
    }

    public List<UserDto> findByName(UserDto userDto) {
        UserByNameSpecification userByNameSpecification = new UserByNameSpecification(userDto.getName());
        List<User> userList = userRepository.query(userByNameSpecification);
        return userList.stream().map(entity -> userMapper.toDto(entity)).collect(Collectors.toList());
    }

    @Override
    public UserDto findById(UserDto userDto) {
        UserByNameSpecification userByNameSpecification = new UserByNameSpecification(userDto.getName());
        List<User> userList = userRepository.query(userByNameSpecification);
        if(userList.size() == 1){
            User user = userList.get(0);
            return userMapper.toDto(user);
        }else{
            throw new ServiceException("wrong user id");
        }
    }

    public long findCount(){
        return userRepository.findCount();
    }

}
