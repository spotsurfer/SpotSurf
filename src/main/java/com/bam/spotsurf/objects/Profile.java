package com.bam.spotsurf.objects;

/**
 * Created by bmerm on 7/27/2016.
 */
public class Profile {

    String name, email, password;

    public Profile(String name, String email){

        this.name= name;
        this.email=email;

    }

    public String getName(){
        return name;
    }
    public String getEmail(){
        return name;
    }
    public void setName(String name){
        this.name= name;
    }
    public void setEmail(String email){
        this.email= email;
    }
    public void setPassword(String password){
        this.password= password;
    }
    public String getPassword(){
        return password;
    }


}
