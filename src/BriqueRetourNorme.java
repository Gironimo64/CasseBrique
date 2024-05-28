import java.awt.Color;

/**
 * La classe BriqueRetourNorme représente une brique spéciale dans le jeu de
 * casse-briques.
 * Cette brique, lorsqu'elle est détruite, déclenche une action de retour à la
 * normale.
 */
public class BriqueRetourNorme extends Brique {
    private final int NORME = 1;

    /**
     * Constructeur par défaut qui initialise une brique retour à la normale.
     * La brique a une couleur rose.
     */
    public BriqueRetourNorme() {
        super();
        couleur = Color.pink;
    }

    /**
     * Traite un choc avec la brique retour à la normale.
     * La brique est marquée comme détruite et retourne un code indiquant
     * qu'un retour à la normale doit être déclenché.
     * 
     * @return un code indiquant que la brique déclenche un retour à la normale
     *         (NORME)
     */
    @Override
    public int choc() {
        super.choc();
        return NORME;
    }
}
