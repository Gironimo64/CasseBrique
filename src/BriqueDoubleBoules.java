import java.awt.Color;

/**
 * La classe BriqueDoubleBoules représente une brique spéciale dans le jeu de
 * casse-briques.
 * Cette brique, lorsqu'elle est détruite, déclenche une action de double boule.
 */
public class BriqueDoubleBoules extends Brique {
    private final int DOUBLE_BOULE = 4;

    /**
     * Constructeur par défaut qui initialise une brique double boules.
     * La brique a une couleur rouge.
     */
    public BriqueDoubleBoules() {
        super();
        couleur = Color.red;
    }

    /**
     * Traite un choc avec la brique double boules.
     * La brique est marquée comme détruite et retourne un code indiquant
     * qu'une double boule doit être déclenchée.
     * 
     * @return un code indiquant que la brique déclenche une double boule
     *         (DOUBLE_BOULE)
     */
    @Override
    public int choc() {
        super.choc();
        return DOUBLE_BOULE;
    }
}
