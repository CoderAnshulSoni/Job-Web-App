package com.project.jobapp.controller;

import ch.qos.logback.core.util.StringUtil;
import com.project.jobapp.entity.RecruiterProfile;
import com.project.jobapp.entity.Users;
import com.project.jobapp.repository.UsersRepository;
import com.project.jobapp.services.RecruiterProfileService;
import com.project.jobapp.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RecruiterProfileService recruiterProfileService;

    @GetMapping("/")
    public String recruiterProfile(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUsername = authentication.getName();

            Users user = usersRepository.findByEmail(currentUsername).orElseThrow(()-> new UsernameNotFoundException("Could not found the user."));

            Optional<RecruiterProfile> recruiterProfile = recruiterProfileService.getOne(user.getUserId());

            if(recruiterProfile.isPresent()){
                model.addAttribute("profile", recruiterProfile.get());
            }
        }
        return "recruiter_profile";
    }

    @PostMapping("/addNew")
    public String addNew(RecruiterProfile recruiterProfile, @RequestParam("image")MultipartFile multipartFile, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUsername = authentication.getName();

            Users users = usersRepository.findByEmail(currentUsername).orElseThrow(()->new UsernameNotFoundException("Could not found the user.."));

            recruiterProfile.setUserId(users);
            recruiterProfile.setUserAccountId(users.getUserId());
        }

        model.addAttribute("profile", recruiterProfile);

        String fileName = "";

        if(!multipartFile.getOriginalFilename().equals("")){
            fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            recruiterProfile.setProfilePhoto(fileName);
        }

        RecruiterProfile savedUser = recruiterProfileService.addNew(recruiterProfile);

        String uploadDir = "/photos/recruiter/" + savedUser.getUserAccountId();

        try{
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } catch (Exception e){
            e.printStackTrace();
        }

        return "redirect:/dashboard/";
    }

}
