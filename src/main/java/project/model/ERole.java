package project.model;

public enum ERole {
    ADMIN("Administrateur"),
    JOUEUR("JOUEUR");

    String nomComplet;

    ERole(String value){
        nomComplet = value;
    }

    public String getNomComplet() {
        return nomComplet;
    }
}
