import java.awt.*;

/**
 * La classe Bonus représente un bonus dans le jeu de casse-briques.
 * Chaque bonus est caractérisé par ses coordonnées, son rayon, sa vitesse,
 * sa couleur et son action spécifique.
 */
public class Bonus {
    private int x, y, rayon, vitesse, action;
    private Color couleur;

    /**
     * Constructeur qui initialise un bonus avec les valeurs spécifiées.
     *
     * @param newX       la coordonnée x du centre du bonus
     * @param newY       la coordonnée y du centre du bonus
     * @param newRayon   le rayon du bonus
     * @param newVitesse la vitesse de déplacement du bonus
     * @param newCouleur la couleur du bonus
     * @param newAction  l'action associée au bonus
     */
    public Bonus(int newX, int newY, int newRayon, int newVitesse, Color newCouleur, int newAction) {
        x = newX;
        y = newY;
        rayon = newRayon;
        vitesse = newVitesse;
        couleur = newCouleur;
        action = newAction;
    }

    /**
     * Déplace le bonus en fonction de sa vitesse.
     * Le bonus se déplace verticalement.
     */
    public void deplace() {
        y = y + vitesse;
    }

    /**
     * Retourne le rayon du bonus.
     *
     * @return le rayon du bonus
     */
    public int getRayon() {
        return rayon;
    }

    /**
     * Retourne la coordonnée x du centre du bonus.
     *
     * @return la coordonnée x du bonus
     */
    public int getX() {
        return x;
    }

    /**
     * Retourne la coordonnée y du centre du bonus.
     *
     * @return la coordonnée y du bonus
     */
    public int getY() {
        return y;
    }

    /**
     * Retourne la couleur du bonus.
     *
     * @return la couleur du bonus
     */
    public Color getCouleur() {
        return couleur;
    }

    /**
     * Retourne l'action associée au bonus.
     *
     * @return l'action du bonus
     */
    public int getAction() {
        return action;
    }

    /**
     * Dessine le bonus sur le composant graphique spécifié.
     *
     * @param motif le contexte graphique dans lequel le bonus doit être dessiné
     */
    public void dessine(Graphics2D motif) {
        motif.setColor(couleur);
        motif.fillRect(x - rayon, y - rayon, rayon * 3, rayon * 3);
    }
}
