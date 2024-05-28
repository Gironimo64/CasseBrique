import java.awt.Color;
import java.awt.Graphics2D;

/**
 * La classe Barre représente une barre de jeu dans le casse-briques.
 * Elle est caractérisée par ses coordonnées, ses dimensions et sa couleur.
 */
public class Barre {

  // x est la coordonnée horizontale du centre de la barre
  // et y est la coordonnée verticale du haut de la barre
  private int x, y, miLargeur, hauteur;
  private Color couleur;

  /**
   * Constructeur par défaut qui initialise la barre avec des valeurs par défaut.
   * La barre est centrée horizontalement à 175, positionnée verticalement à 310,
   * avec une demi-largeur de 25 et une hauteur de 9. Sa couleur est bleue.
   */
  public Barre() {
    x = 175;
    y = 310;
    miLargeur = 25;
    hauteur = 9;
    couleur = Color.blue;
  }

  /**
   * Retourne la coordonnée horizontale du centre de la barre.
   * 
   * @return la coordonnée x de la barre
   */
  public int getX() {
    return x;
  }

  /**
   * Modifie la coordonnée horizontale du centre de la barre.
   * 
   * @param newVal la nouvelle coordonnée x de la barre
   */
  public void setX(int newVal) {
    x = newVal;
  }

  /**
   * Retourne la coordonnée verticale du haut de la barre.
   * 
   * @return la coordonnée y de la barre
   */
  public int getY() {
    return y;
  }

  /**
   * Retourne la demi-largeur de la barre.
   * 
   * @return la demi-largeur de la barre
   */
  public int getMiLargeur() {
    return miLargeur;
  }

  /**
   * Modifie la demi-largeur de la barre.
   * 
   * @param newVal la nouvelle demi-largeur de la barre
   */
  public void setMiLargeur(int newVal) {
    miLargeur = newVal;
  }

  /**
   * Retourne la hauteur de la barre.
   * 
   * @return la hauteur de la barre
   */
  public int getHauteur() {
    return hauteur;
  }

  /**
   * Retourne la couleur de la barre.
   * 
   * @return la couleur de la barre
   */
  public Color getCouleur() {
    return couleur;
  }

  /**
   * Dessine la barre sur le composant graphique spécifié.
   * 
   * @param motif le contexte graphique dans lequel la barre doit être dessinée
   */
  public void dessine(Graphics2D motif) {
    motif.setColor(couleur);
    motif.fillRect(x - miLargeur, y, miLargeur * 2, hauteur);
  }
}
