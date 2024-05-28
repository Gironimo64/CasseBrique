import java.awt.Color;

/**
 * La classe BriqueBouleRapide représente une brique spéciale dans le jeu de
 * casse-briques.
 * Cette brique, lorsqu'elle est détruite, augmente la vitesse de la boule.
 */
public class BriqueBouleRapide extends Brique {
    private final int RAPIDE = 2;

    /**
     * Constructeur par défaut qui initialise une brique rapide.
     * La brique a une couleur jaune.
     */
    public BriqueBouleRapide() {
        super();
        couleur = Color.yellow;
    }

    /**
     * Traite un choc avec la brique rapide.
     * La brique est marquée comme détruite et retourne un code indiquant
     * que la boule doit augmenter sa vitesse.
     * 
     * @return un code indiquant que la brique est rapide (RAPIDE)
     */
    @Override
    public int choc() {
        super.choc();
        return RAPIDE;
    }
}
