package com.example.demo.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private String name;
    private String email;
    private String phone;
    private AllDetailsDTO allDetailsDTO;

    public MessageDTO(String name){
        this.name = name;
    }

    public String mailBody(){
        return "\nEvent Booked by "+name
                +"\nUser Email - "+email
                +"\n"+ allDetailsDTO.mailBody();
    }
}
