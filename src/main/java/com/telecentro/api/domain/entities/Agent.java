package com.telecentro.api.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "t_tc_agent")
@Table(name = "t_tc_agent")
@EqualsAndHashCode(of = "id")
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "TEXT", unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

}
