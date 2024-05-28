import java.awt.*;

/**
 * La classe Boule représente une boule dans le jeu de casse-briques.
 * Elle est caractérisée par ses coordonnées, son rayon, sa vitesse, et sa
 * couleur.
 */
public class Boule {
    // x et y désignent les coordonnées du centre de la boule
    private int x, y, depX, depY, rayon, vitesse;
    private Color couleur;

    /**
     * Constructeur par défaut qui initialise une boule avec des valeurs par défaut.
     * La boule a un rayon de 4, est centrée horizontalement à 175 et positionnée
     * verticalement à 306 (310 - rayon). Sa couleur est rouge et sa vitesse est de
     * 5.
     */
    public Boule() {
        rayon = 4;
        x = 175;
        y = 310 - rayon;
        couleur = Color.red;
        vitesse = 5;
    }

    /**
     * Crée une copie de la boule actuelle.
     * 
     * @return une nouvelle instance de Boule avec les mêmes propriétés
     */
    public Boule clone() {
        Boule nouvelleBoule = new Boule();
        nouvelleBoule.rayon = this.rayon;
        nouvelleBoule.x = this.x;
        nouvelleBoule.y = this.y;
        nouvelleBoule.depX = this.depX;
        nouvelleBoule.depY = this.depY;
        nouvelleBoule.vitesse = this.vitesse;
        nouvelleBoule.couleur = this.couleur;
        return nouvelleBoule;
    }

    /**
     * Définit l'angle de déplacement de la boule.
     * L'angle doit être compris entre 20 et 160 degrés.
     * 
     * @param angle l'angle de déplacement en degrés
     */
    public void angleDep(int angle) {
        // L'angle doit être compris entre 20 et 160 degrés (c'est mieux !)
        if (angle < 20) {
            angle = 20;
        } else if (angle > 160) {
            angle = 160;
        }
        // Calcul des déplacements en x et en y selon l'angle
        depX = (int) (Math.cos(Math.toRadians(angle)) * vitesse);
        depY = (int) (-Math.sin(Math.toRadians(angle)) * vitesse);
    }

    /**
     * Modifie l'angle de déplacement de la boule.
     * 
     * @param arc l'angle en degrés à ajouter au déplacement actuel
     */
    public void modifAngle(int arc) {
        // Il faut faire l'arc-cosinus du déplacement en x pour retrouver l'angle
        // du déplacement et pouvoir lui ajouter arc degrés. Les fonctions
        // de la classe Math travaillent en radians ( d'où conversion).
        angleDep((int) Math.toDegrees(Math.acos(depX / vitesse)) + arc);
    }

    /**
     * Inverse le déplacement horizontal de la boule (choc horizontal).
     */
    public void chocH() {
        depX = -depX;
    }

    /**
     * Inverse le déplacement vertical de la boule (choc vertical).
     */
    public void chocV() {
        depY = -depY;
    }

    /**
     * Place la boule aux coordonnées spécifiées.
     * 
     * @param newX la nouvelle coordonnée x de la boule
     * @param newY la nouvelle coordonnée y de la boule
     */
    public void place(int newX, int newY) {
        x = newX;
        y = newY;
    }

    /**
     * Déplace la boule selon ses déplacements en x et y.
     */
    public void deplace() {
        x = x + depX;
        y = y + depY;
    }

    /**
     * Retourne le rayon de la boule.
     * 
     * @return le rayon de la boule
     */
    public int getRayon() {
        return rayon;
    }

    /**
     * Retourne la coordonnée x du centre de la boule.
     * 
     * @return la coordonnée x de la boule
     */
    public int getX() {
        return x;
    }

    /**
     * Retourne la coordonnée y du centre de la boule.
     * 
     * @return la coordonnée y de la boule
     */
    public int getY() {
        return y;
    }

    /**
     * Retourne la couleur de la boule.
     * 
     * @return la couleur de la boule
     */
    public Color getCouleur() {
        return couleur;
    }

    /**
     * Dessine la boule sur le composant graphique spécifié.
     * 
     * @param motif le contexte graphique dans lequel la boule doit être dessinée
     */
    public void dessine(Graphics2D motif) {
        motif.setColor(couleur);
        motif.fillOval(x - rayon, y - rayon, rayon * 2, rayon * 2);
    }
}
