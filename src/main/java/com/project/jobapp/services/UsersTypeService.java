package com.project.jobapp.services;

import com.project.jobapp.entity.UsersType;
import com.project.jobapp.repository.UsersTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersTypeService {

    @Autowired
    private UsersTypeRepository userTypeRepo;

    public List<UsersType> getAll(){
        return userTypeRepo.findAll();
    }
}
