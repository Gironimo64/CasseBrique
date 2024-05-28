import java.awt.Color;

/**
 * La classe BriqueRetrecitBarre représente une brique spéciale dans le jeu de
 * casse-briques.
 * Cette brique, lorsqu'elle est détruite, déclenche une action de
 * rétrécissement de la barre.
 */
public class BriqueRetrecitBarre extends Brique {
    private final int TAILLE_MOITIER = 3;

    /**
     * Constructeur par défaut qui initialise une brique rétrécit la barre.
     * La brique a une couleur bleue.
     */
    public BriqueRetrecitBarre() {
        super();
        couleur = Color.blue;
    }

    /**
     * Traite un choc avec la brique rétrécit la barre.
     * La brique est marquée comme détruite et retourne un code indiquant
     * qu'une réduction de la taille de la barre doit être déclenchée.
     * 
     * @return un code indiquant que la brique déclenche une réduction de la taille
     *         de la barre (TAILLE_MOITIER)
     */
    @Override
    public int choc() {
        super.choc();
        return TAILLE_MOITIER;
    }
}
