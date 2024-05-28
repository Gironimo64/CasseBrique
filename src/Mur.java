import java.awt.Graphics2D;
import javax.swing.JOptionPane;

/**
 * La classe Mur représente le mur de briques dans le jeu de casse-briques.
 * Elle gère la construction du mur, les différentes configurations de niveaux
 * et la logique de collision avec les balles.
 */
public class Mur {
  private Brique[][] mur = new Brique[10][20]; // Le mur de briques
  private int nbBriques; // Le nombre total de briques dans le mur

  /**
   * Construit aléatoirement le mur de briques avec des configurations basiques.
   */
  public void construit() {
    // Affectation aléatoire de briques au mur
    for (int l = 0; l < 10; l++) {
      for (int c = 0; c < 20; c++) {
        switch ((int) (Math.random() * 10)) {
          case 1:
            mur[l][c] = new BriqueRetourNorme();
            break;
          case 2:
            mur[l][c] = new BriqueBouleRapide();
            break;
          case 3:
          case 6:
          case 7:
            mur[l][c] = new BriqueRetrecitBarre();
            break;
          case 4:
            mur[l][c] = new BriqueRisistante();
            break;
          case 5:
            mur[l][c] = new BriqueDoubleBoules();
            break;
          default:
            mur[l][c] = new Brique();
        }
        mur[l][c].positionne(c * (mur[l][c].getLargeur() + 1), l * (mur[l][c].getHauteur() + 1));
      }
    }
    nbBriques = 200;
  }

  /**
   * Construit le mur de briques avec une configuration spécifique.
   */
  public void construit2() {
    // Affectaion aléatoire de briques au mur
    for (int l = 0; l < 10; l++) {
      for (int c = 0; c < 20; c++) {
        switch ((int) (Math.random() * 10)) {
          case 1:
            mur[l][c] = new BriqueRetourNorme();
            break;
          case 2:
          case 6:
            mur[l][c] = new BriqueBouleRapide();
            break;
          case 3:
          case 7:
          case 9:
            mur[l][c] = new BriqueRetrecitBarre();
            break;
          case 4:
          case 8:
            mur[l][c] = new BriqueRisistante();
            break;
          case 5:
            mur[l][c] = new BriqueDoubleBoules();
            break;
          default:
            mur[l][c] = new Brique();
        }
        mur[l][c].positionne(c * (mur[l][c].getLargeur() + 1),
            l * (mur[l][c].getHauteur() + 1));
      }
    }
    nbBriques = 200;
  }

  /**
   * Construit le mur de briques avec une configuration spécifique.
   */
  public void construit3() {
    // Affectaion aléatoire de briques au mur
    for (int l = 0; l < 10; l++) {
      for (int c = 0; c < 20; c++) {
        switch ((int) (Math.random() * 10)) {
          case 1:
            mur[l][c] = new BriqueRetourNorme();
            break;
          case 2:
          case 6:
            mur[l][c] = new BriqueBouleRapide();
            break;
          case 3:
          case 7:
            mur[l][c] = new BriqueRetrecitBarre();
            break;
          case 4:
          case 8:
          case 9:
          case 10:
            mur[l][c] = new BriqueRisistante();
            break;
          case 5:
            mur[l][c] = new BriqueDoubleBoules();
            break;
          default:
            mur[l][c] = new Brique();
        }
        mur[l][c].positionne(c * (mur[l][c].getLargeur() + 1),
            l * (mur[l][c].getHauteur() + 1));
      }
    }
    nbBriques = 200;
  }

  /**
   * Crée un niveau personnalisé en permettant à l'utilisateur de choisir les
   * types de briques pour chaque case du mur.
   */
  public void creerlvl() {
    boolean stop = false;
    for (int l = 0; l < 10 && stop == false; l++) {
      for (int c = 0; c < 20 && stop == false; c++) {

        Object[] options = { "Basique", "NoEffect", "Rapide", "Barre Retrcit", "Ristante", "Clone Boule" };
        int option = JOptionPane.showOptionDialog(
            null,
            "Sélectionnez une option pour la brique [" + l + "][" + c + "]",
            "Choix d'option",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[0]);

        // Appliquer l'effet en fonction de l'option choisie
        switch (option) {
          case 1:
            mur[l][c] = new BriqueRetourNorme();
            break;
          case 2:
            mur[l][c] = new BriqueBouleRapide();
            break;
          case 3:
          case 7:
            mur[l][c] = new BriqueRetrecitBarre();
            break;
          case 4:
          case 8:
          case 9:
          case 10:
            mur[l][c] = new BriqueRisistante();
            break;
          case 5:
            mur[l][c] = new BriqueDoubleBoules();
            break;
          case javax.swing.JOptionPane.CLOSED_OPTION:
            stop = true;
            continue;
          default:
            mur[l][c] = new Brique();
        }

        // Positionner la brique
        mur[l][c].positionne(c * (mur[l][c].getLargeur() + 1), l * (mur[l][c].getHauteur() + 1));
      }
    }
    nbBriques = 200;

  }

  /**
   * Vérifie si une brique est touchée aux coordonnées spécifiées.
   * 
   * @param l la ligne de la brique
   * @param c la colonne de la brique
   * @return true si une brique est touchée, sinon false
   */
  public boolean percute(int l, int c) {
    // Coordonnées hors du mur ?
    if (l < 0 || l > 9 || c < 0 || c > 19) {
      // Le mur n'a pas été percuté
      return false;
    } else
    // Si la brique à ces cordonnées était déjà détruite ...
    if (mur[l][c].isDetruite()) {
      // Le mur n'a pas été percuté
      return false;
    } else {
      // Le mur est percuté
      return true;
    }
  }

  /**
   * Applique un choc à une brique spécifique aux coordonnées spécifiées.
   * 
   * @param l la ligne de la brique
   * @param c la colonne de la brique
   * @return le type de choc appliqué à la brique
   */
  public int casse(int l, int c) {
    int consequence = 0;
    // Si les coordonnées sont dans le mur (pas de coordonnées hors tableau)
    if (l >= 0 && l < 10 && c >= 0 && c < 20) {
      // Si la brique à ces coordonnées n'était pas détruite ...
      if (!mur[l][c].isDetruite()) {
        // La brique reçoit un choc (qui peut avoir une conséquence)
        consequence = mur[l][c].choc();
        // Si le choc a détruit la brique ...
        if (mur[l][c].isDetruite()) {
          // Une brique en moins, une !
          nbBriques--;
        }
      }
    }
    return consequence;
  }

  /**
   * Récupère le nombre total de briques dans le mur.
   * 
   * @return le nombre total de briques
   */
  public int getNbBriques() {
    return nbBriques;
  }

  /**
   * Récupère la largeur d'une brique dans le mur.
   * 
   * @return la largeur d'une brique
   */
  public int getLargeurBrique() {
    return mur[0][0].getLargeur();
  }

  /**
   * Récupère la hauteur d'une brique dans le mur.
   * 
   * @return la hauteur d'une brique
   */
  public int getHauteurBrique() {
    return mur[0][0].getHauteur();
  }

  /**
   * Dessine le mur de briques.
   * 
   * @param support l'objet Graphics2D pour dessiner le mur
   */
  public void dessine(Graphics2D support) {
    // Dessin du mur de brique
    for (int l = 0; l < 10; l++) {
      for (int c = 0; c < 20; c++) {
        // Si la brique n'est pas détruite ...
        if (mur[l][c] == null) {
          continue;
        }
        if (!mur[l][c].isDetruite()) {
          // On la dessine
          mur[l][c].dessine(support);
        }
      }
    }
  }
}
