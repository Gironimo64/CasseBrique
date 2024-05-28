import java.awt.Color;

/**
 * La classe BriqueRisistante représente une brique spéciale dans le jeu de
 * casse-briques.
 * Cette brique, lorsqu'elle est détruite, déclenche une action de résistance.
 * Elle change de couleur lorsqu'elle est touchée avant d'être finalement
 * détruite.
 */
public class BriqueRisistante extends Brique {
    private final int RESISTANTE = 100;

    /**
     * Constructeur par défaut qui initialise une brique résistante.
     * La brique a une couleur gris foncé.
     */
    public BriqueRisistante() {
        super();
        couleur = Color.darkGray;
    }

    /**
     * Traite un choc avec la brique résistante.
     * La brique change de couleur à chaque choc jusqu'à ce qu'elle soit finalement
     * détruite.
     * 
     * @return un code indiquant que la brique est résistante (RESISTANTE)
     */
    @Override
    public int choc() {
        if (couleur == Color.darkGray) {
            couleur = Color.gray;
        } else if (couleur == Color.gray) {
            couleur = Color.lightGray;
        } else if (couleur == Color.lightGray) {
            super.choc();
        }
        return RESISTANTE;
    }
}
