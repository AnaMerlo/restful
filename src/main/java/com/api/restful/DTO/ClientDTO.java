package com.api.restful.DTO;

import com.api.restful.entities.Order;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

    private String id;
    private String name;
    private String email;
    private Integer phone;

    public ClientDTO(String id){
        this.id = id;
    }

}
