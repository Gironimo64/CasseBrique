import java.awt.*;

/**
 * La classe Brique représente une brique dans le jeu de casse-briques.
 * Chaque brique est caractérisée par ses coordonnées, ses dimensions, sa
 * couleur,
 * et son état (détruite ou non).
 */
public class Brique {
    private final int SIMPLE = 0;
    private int x, y, largeur, hauteur;
    protected Color couleur;
    protected boolean detruite;

    /**
     * Constructeur par défaut qui initialise une brique avec des valeurs par
     * défaut.
     * La brique a une largeur de 17, une hauteur de 14, une couleur verte,
     * et n'est pas détruite.
     */
    public Brique() {
        largeur = 17;
        hauteur = 14;
        couleur = Color.green;
        detruite = false;
    }

    /**
     * Traite un choc avec la brique.
     * Si la brique n'est pas déjà détruite, elle est marquée comme détruite.
     * 
     * @return un code indiquant le type de brique (SIMPLE)
     */
    public int choc() {
        if (!detruite) {
            detruite = true;
        }
        return SIMPLE;
    }

    /**
     * Vérifie si la brique est détruite.
     * 
     * @return true si la brique est détruite, sinon false
     */
    public boolean isDetruite() {
        return detruite;
    }

    /**
     * Positionne la brique aux coordonnées spécifiées.
     * 
     * @param newX la nouvelle coordonnée x de la brique
     * @param newY la nouvelle coordonnée y de la brique
     */
    public void positionne(int newX, int newY) {
        x = newX;
        y = newY;
    }

    /**
     * Retourne la hauteur de la brique.
     * 
     * @return la hauteur de la brique
     */
    public int getHauteur() {
        return hauteur;
    }

    /**
     * Retourne la largeur de la brique.
     * 
     * @return la largeur de la brique
     */
    public int getLargeur() {
        return largeur;
    }

    /**
     * Dessine la brique sur le composant graphique spécifié.
     * 
     * @param motif le contexte graphique dans lequel la brique doit être dessinée
     */
    public void dessine(Graphics2D motif) {
        motif.setColor(couleur);
        motif.fillRect(x, y, largeur, hauteur);
    }
}
