package com.br.edu.utfpr.trabalho_backend_topicosavancados.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
/* 
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
*/
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_pessoa")
public class Pessoa /* implements UserDetails */ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Gateway> gateways;

    @Column(unique = true, length = 100, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    public String getUsername() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    /*
     * @Override
     * public Collection<? extends GrantedAuthority> getAuthorities() {
     * return List.of();
     * }
     * 
     * @Override
     * public boolean isAccountNonExpired() {
     * return true;
     * }
     * 
     * @Override
     * public boolean isAccountNonLocked() {
     * return true;
     * }
     * 
     * @Override
     * public boolean isCredentialsNonExpired() {
     * return true;
     * }
     * 
     * @Override
     * public boolean isEnabled() {
     * return true;
     * }
     */
}
