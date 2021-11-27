package project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class Roles {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long refRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private User user;

    /* Role défini par un entier tel que :
        0 défini le role ADMIN
        1 défini le role JOUEUR
    * */
    private ERole role;

    public User getUser() {
        return user;
    }

    public void setUser(User utilisateur) {
        this.user = utilisateur;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

    public Roles() {
    }

    public Roles(User utilisateur, ERole role) {
        this.user = utilisateur;
        this.role = role;
    }

    public Roles(ERole role, Long refRole, User utilisateur ) {
        this.refRole = refRole;
        this.user = utilisateur;
        this.role = role;
    }
}
