package com.introapp.model;

import com.introapp.library.util.Constants;

import java.util.Map;


public class CandidateFeedModel implements Constants.APPKEYS{

    private String name;
    private String image;
    private String experience;
    private String skills;
    private String email;
    private String phonenumber;


    private void setProperties(Map pMap) {

        name            =   ""+pMap.get(NAME);
        image           =   ""+pMap.get(IMAGE);
        experience      =   ""+pMap.get(EXPERIENCE);
        skills          =   ""+pMap.get(SKILLS);
        email           =   ""+pMap.get(EMAIL);
        phonenumber     =   ""+pMap.get(PHONENUMBER);

    }


    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
