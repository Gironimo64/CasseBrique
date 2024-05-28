
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import javax.swing.Timer;

/**
 * La classe EspaceJeu représente la zone de jeu du casse-briques.
 * Elle étend la classe JPanel et implémente les interfaces Runnable,
 * MouseListener et MouseMotionListener.
 */
public class EspaceJeu extends JPanel implements Runnable, MouseListener,
    MouseMotionListener {
  private CB cb;
  // Delai entre 2 déplacements
  private final int DELAI = 16;

  // Constantes rattachées aux phases de jeu
  private final int ATTEND = 1;
  private final int ROULE = 2;
  private final int SORT = 3;
  private final int GAGNE = 4;

  // Nombre de vie base et Affichage
  private int nbvie;

  // Level du Mur
  private int lvl = 0;

  // Timer variable
  private Timer timer;
  private LocalDateTime startTimer;
  private boolean timerStart = false;

  private final int NORME = 1;
  private final int RAPIDE = 2;
  private final int TAILLE_Moitier = 3;
  private final int DOUBLE_BOULE = 4;
  // Champs d'instance
  private Thread action;
  private int phase;
  private int delai;
  private Barre barre;
  private int barreOrigine;
  private int barreRetrecit;
  private Boule boule1;
  private Mur mur;
  private ArrayList<Boule> listeBoules;
  private ArrayList<Bonus> listeBonus;
  private int compteurBonus;
  private int compteurBonusModulo;

  /**
   * Constructeur de la classe EspaceJeu.
   * Initialise les éléments de jeu et ajoute les écouteurs de souris.
   * 
   * @param cb La référence à la classe principale CB
   */
  public EspaceJeu(CB cb) {
    this.cb = cb;
    // Création de la barre
    barre = new Barre();
    // Création des boule
    boule1 = new Boule();

    listeBoules = new ArrayList<Boule>();
    listeBoules.add(boule1);
    listeBonus = new ArrayList<Bonus>();

    barreOrigine = barre.getMiLargeur();
    barreRetrecit = barreOrigine / 2;

    // Délai entre 2 déplacements
    delai = DELAI;

    // phase d'attente
    phase = ATTEND;

    // Gestion des évenement liés à la souris
    addMouseMotionListener(this);
    addMouseListener(this);

  }

  /**
   * Retourne le nombre de vies restantes.
   * 
   * @return Le nombre de vies restantes
   */
  public int getvie() {
    return nbvie;
  }

  /**
   * Retourne le niveau actuel.
   * 
   * @return Le niveau actuel
   */
  public int getlvl() {
    return lvl;
  }

  /**
   * Applique un nouveau niveau.
   * 
   * @param newlvl Le nouveau niveau à appliquer
   */
  public void setlvl(int newlvl) {
    lvl = newlvl;
  }

  /**
   * Méthode principale exécutée dans le thread.
   * Gère le déroulement du jeu, les interactions avec les objets (boules, barre,
   * briques),
   * les collisions, les bonus et les niveaux.
   */
  public void run() {
    nbvie = 3;
    cb.affichagevie();
    boolean fini = false;
    while (!fini) {
      for (int i = 0; i < listeBoules.size(); i++, compteurBonus++) {
        if (listeBonus.size() > 0) {
          compteurBonusModulo = compteurBonus % listeBonus.size();
        }
        // Selon la phase du jeu ...
        switch (phase) {
          // Attente de lancement de la boule
          case ATTEND:
            delai = DELAI;
            barre.setMiLargeur(barreOrigine);
            listeBonus = new ArrayList<Bonus>();
            // Placement de la boule au milieu de la barre
            listeBoules.get(i).place(barre.getX(), barre.getY() - boule1.getRayon());
            break;

          // La boule roule
          case ROULE:
            // Déplacement de la boule
            listeBoules.get(i).deplace();
            if (listeBonus.size() > 0) {
              listeBonus.get(compteurBonusModulo).deplace();
            }

            // Rebond sur le bord gauche ?
            if (listeBoules.get(i).getX() < listeBoules.get(i).getRayon()) {
              listeBoules.get(i).chocH();
              listeBoules.get(i).place(listeBoules.get(i).getRayon(), listeBoules.get(i).getY());
            } else {
              // Rebond sur le bord droit ?
              if (listeBoules.get(i).getX() > getSize().width - listeBoules.get(i).getRayon()) {
                listeBoules.get(i).chocH();
                listeBoules.get(i).place(getSize().width - listeBoules.get(i).getRayon(), listeBoules.get(i).getY());
              }
            }

            // Rebond sur le haut ?
            if (listeBoules.get(i).getY() < listeBoules.get(i).getRayon()) {
              listeBoules.get(i).chocV();
              listeBoules.get(i).place(listeBoules.get(i).getX(), listeBoules.get(i).getRayon());
            } else {
              // Rebond (ou non) sur la barre de la boule ?
              if (listeBoules.get(i).getY() > 310 - listeBoules.get(i).getRayon()) {
                if ((listeBoules.get(i).getX() - listeBoules.get(i).getRayon() < barre.getX() + barre.getMiLargeur())
                    &&
                    (listeBoules.get(i).getX() + listeBoules.get(i).getRayon() > barre.getX() - barre.getMiLargeur())) {
                  // Rebond sur la barre
                  // Le rebond dépend de la zone de la barre touchée
                  rebondSurBarre(listeBoules.get(i).getX() - barre.getX(), i);

                  listeBoules.get(i).place(listeBoules.get(i).getX(), 310 - listeBoules.get(i).getRayon());
                } else {
                  // Si la boule touche le fond ...
                  if (listeBoules.get(i).getY() > 310 + barre.getHauteur() - listeBoules.get(i).getRayon()) {

                    if (listeBoules.size() > 1) {
                      listeBoules.remove(i);
                      continue;
                    } else {
                      nbvie = nbvie - 1;
                      cb.affichagevie();
                      delai = DELAI;
                      if (nbvie <= 0) {
                        phase = SORT;
                      } else {
                        compteurBonus = 0;
                        phase = ATTEND;
                      }
                    }
                    // Loupé !!

                  }
                }

              }
            }
            // Rebond (ou non) sur la barre du bonus ?
            if (listeBonus.size() > 0) {
              if (listeBonus.get(compteurBonusModulo).getY() > 310 - listeBonus.get(compteurBonusModulo).getRayon()) {
                if ((listeBonus.get(compteurBonusModulo).getX()
                    - listeBonus.get(compteurBonusModulo).getRayon() < barre.getX() + barre.getMiLargeur())
                    &&
                    (listeBonus.get(compteurBonusModulo).getX()
                        + listeBonus.get(compteurBonusModulo).getRayon() > barre.getX() - barre.getMiLargeur())) {
                  // touche la barre
                  // Applique l'effet du bonus
                  modifJeu(listeBonus.get(compteurBonusModulo).getAction(), i, compteurBonusModulo);
                  // supprime le bonus de la liste
                  listeBonus.remove(compteurBonusModulo);
                } else {
                  // Si le bonnus touche le fond ...
                  if (listeBonus.get(compteurBonusModulo).getY() > 310 + barre.getHauteur()
                      - listeBonus.get(compteurBonusModulo).getRayon()) {
                    // supprime le bonus de la liste
                    listeBonus.remove(compteurBonusModulo);
                  }
                }
              }
            }

            // Gestion du choc avec une brique
            // Récupération de la hauteur d'une brique
            int hauteur = mur.getHauteurBrique();
            // Récupération de la largeur d'une brique
            int largeur = mur.getLargeurBrique();
            // Si la boule se trouve dans la zone du mur de briques ...
            if (listeBoules.get(i).getY() - listeBoules.get(i).getRayon() < 10 * (hauteur + 1)) {
              // l1, c1 sont les coordonnées du coin supérieur gauche de la boule
              // l2, c2 sont les coordonnées du coin inférieur droit de la boule
              int l1, l2, c1, c2;
              l1 = (int) ((listeBoules.get(i).getY() - listeBoules.get(i).getRayon()) / (hauteur + 1));
              l2 = (int) ((listeBoules.get(i).getY() + listeBoules.get(i).getRayon()) / (hauteur + 1));
              c1 = (int) ((listeBoules.get(i).getX() - listeBoules.get(i).getRayon()) / (largeur + 1));
              c2 = (int) ((listeBoules.get(i).getX() + listeBoules.get(i).getRayon()) / (largeur + 1));

              // Le rebond dépend des coins (1 ou 2) en contact avec une brique
              // Coin supérieur gauche ...
              if (mur.percute(l1, c1)) {
                // et coin supérieur droit
                if (mur.percute(l1, c2)) {
                  // Choc vertical
                  listeBoules.get(i).chocV();
                } else {
                  // et coin inférieur gauche
                  if (mur.percute(l2, c1)) {
                    // Choc horizontal
                    listeBoules.get(i).chocH();
                  } else {
                    // Double choc
                    listeBoules.get(i).chocV();
                    listeBoules.get(i).chocH();
                  }
                }
              } else {
                // Coin supérieur droit ...
                if (mur.percute(l1, c2)) {
                  // et coin inférieur droit
                  if (mur.percute(l2, c2)) {
                    // Choc horizontal
                    listeBoules.get(i).chocH();
                  } else {
                    // Double choc
                    listeBoules.get(i).chocV();
                    listeBoules.get(i).chocH();
                  }
                } else {
                  // Coin inférieur gauche ...
                  if (mur.percute(l2, c1)) {
                    // et coin inférieur droit
                    if (mur.percute(l2, c2)) {
                      // Choc vertical
                      listeBoules.get(i).chocV();
                    } else {
                      // Double choc
                      listeBoules.get(i).chocV();
                      listeBoules.get(i).chocH();
                    }
                  } else {
                    // Coin inférieur droit
                    if (mur.percute(l2, c2)) {
                      // Double choc
                      listeBoules.get(i).chocV();
                      listeBoules.get(i).chocH();
                    }
                  }
                }
              }
              // Casse effective des brique du mur
              // (et mise en place des conséquences)
              modifJeu(mur.casse(l1, c1), i, listeBonus.size());
              modifJeu(mur.casse(l1, c2), i, listeBonus.size());
              modifJeu(mur.casse(l2, c1), i, listeBonus.size());
              modifJeu(mur.casse(l2, c2), i, listeBonus.size());

              // Si toutes les briques sont cassées ...
              if (mur.getNbBriques() == 0) {
                // Le joueur à gagné
                if (lvl < 2) {
                  // action.interrupt();
                  lvl = lvl + 1;
                  initialiseNiveau();
                } else {
                  lvl = 0;
                  phase = GAGNE;
                }

              }
            }
            break;

          case SORT:
            JOptionPane.showMessageDialog(this, "C'est perdu !", "Casse briques",
                JOptionPane.INFORMATION_MESSAGE);

            fini = true;
            phase = ATTEND;
            break;

          case GAGNE:
            JOptionPane.showMessageDialog(this, "Bravo, vous avez gagné !",
                "Casse briques", JOptionPane.INFORMATION_MESSAGE);
            fini = true;
            phase = ATTEND;
            break;
        }
      }

      // on redessine l'espace de jeu
      repaint();

      try {
        action.sleep(delai);
      } catch (InterruptedException e) {
      }
    }
    action.interrupt();
    action = null;
  }

  /**
   * Effectue le rebond de la boule sur la barre en fonction de l'impact.
   * 
   * @param impact L'impact de la boule sur la barre.
   * @param indice L'indice de la boule concernée dans la liste des boules.
   */
  public void rebondSurBarre(int impact, int indice) {
    // Rebond sur la barre
    listeBoules.get(indice).chocV();

    // La barre est divisée en 5 parties. Chaque partie provoque un rebond différent
    // Partie extrême gauche : Augmentation de l'angle de 30 degrés
    if (impact < -(barre.getMiLargeur() * 0.6))
      listeBoules.get(indice).modifAngle(30);
    else
    // Partie suivante : Augmentation de l'angle de 15 degrés
    if (impact < -(barre.getMiLargeur() * 0.2))
      listeBoules.get(indice).modifAngle(15);

    // Partie extrême droite : Diminution de l'angle de 30 degrés
    if (impact > (barre.getMiLargeur() * 0.6))
      listeBoules.get(indice).modifAngle(-30);
    else
    // Partie précédente : Diminution de l'angle de 15 degrés
    if (impact > (barre.getMiLargeur() * 0.2))
      listeBoules.get(indice).modifAngle(-15);

    // La partie centrale de la barre provoque un rebond normal
  }

  /**
   * Modifie les paramètres du jeu en fonction de l'action spécifiée.
   * 
   * @param action      L'action à effectuer (NORME, RAPIDE, TAILLE_Moitier,
   *                    DOUBLE_BOULE).
   * @param indiceBoule L'indice de la boule concernée.
   * @param indiceBonus L'indice du bonus concerné.
   */
  public void modifJeu(int action, int indiceBoule, int indiceBonus) {
    switch (action) {
      case NORME:
        // Passe lors du 1er choc dans le if puis une fois récupérer le bonus dans le
        // else
        if (indiceBonus == listeBonus.size()) {
          listeBonus.add(new Bonus(listeBoules.get(indiceBoule).getX(), listeBoules.get(indiceBoule).getY(),
              listeBoules.get(indiceBoule).getRayon(), 3, Color.pink, action));
        } else {
          // Retour aux valeurs de base
          delai = DELAI;
          barre.setMiLargeur(barreOrigine);
        }
        break;

      case RAPIDE:
        // Accélération du traitement
        delai = (int) (DELAI / 2);
        // Start Timer quand ça touche
        checkTimer();
        break;

      case TAILLE_Moitier:
        // Réduit la taille de moitié
        if (barre.getMiLargeur() > barreRetrecit) {
          barre.setMiLargeur(barreRetrecit);
          checkTimer();
        }
        break;

      case DOUBLE_BOULE:
        if (indiceBonus == listeBonus.size()) {
          listeBonus.add(new Bonus(listeBoules.get(indiceBoule).getX(), listeBoules.get(indiceBoule).getY(),
              listeBoules.get(indiceBoule).getRayon(), 3, Color.red, action));
        } else {
          listeBoules.add(listeBoules.get(indiceBoule).clone());
          listeBoules.get(listeBoules.size() - 1).modifAngle(nombreAleatoirePlage(-180, 180));
        }
        break;
    }
  }

  /**
   * Vérifie et gère le fonctionnement du timer.
   * Si le timer est déjà démarré et existe, il est arrêté.
   * Un nouveau timer est ensuite créé pour effectuer un compte à rebours de 4
   * secondes.
   * Pendant ce compte à rebours, la méthode modifie le jeu en fonction du temps
   * écoulé.
   * Une fois le compte à rebours terminé, le timer est arrêté et le temps écoulé
   * est affiché.
   */
  private void checkTimer() {
    // Si le timer est déjà démarré et existe, l'arrêter
    if (timerStart && timer != null) {
      timer.stop();
    }
    // ActionListener pour gérer les actions à effectuer à chaque tick du timer
    ActionListener T = new ActionListener() {
      public void actionPerformed(ActionEvent i) {
        LocalDateTime tmp = LocalDateTime.now();
        Duration c = Duration.ofSeconds(4);
        Long e = c.minus(Duration.between(startTimer, tmp)).toSeconds();

        // Si le temps restant est inférieur ou égal à 0
        if (e <= 0) {
          // Arrêter le timer et mettre à jour le jeu
          timerStart = false;
          modifJeu(NORME, 0, 0);
          timer.stop();
          cb.setTimer("0");
        } else {
          // Afficher le temps restant
          cb.setTimer(e.toString());
        }
      }
    };

    // Créer un nouveau timer avec un délai de 500 millisecondes et démarrer
    timer = new Timer(500, T);
    timer.start();

    // Enregistrer le moment de démarrage du timer et indiquer qu'il est démarré
    startTimer = LocalDateTime.now();
    timerStart = true;
  }

  /**
   * Initialise un nouveau niveau de jeu.
   * Cette méthode est appelée chaque fois qu'un nouveau niveau démarre.
   * Elle crée un nouveau mur de briques, réinitialise la liste des boules et des
   * bonus,
   * ajuste les paramètres du jeu et démarre le thread d'exécution du jeu si
   * nécessaire.
   */
  public void initialiseNiveau() {
    // Réinitialiser le nombre de vies et afficher le nombre de vies
    if (action != null) {
      nbvie = 3;
      cb.affichagevie();
    }

    // Créer le mur de briques s'il n'existe pas déjà
    if (mur == null) {
      mur = new Mur();
    }

    // Créer une nouvelle liste de boules et ajouter une nouvelle boule
    listeBoules = new ArrayList<Boule>();
    listeBoules.add(boule1);

    // Construire le mur de briques en fonction du niveau actuel
    if (lvl == 1) {
      mur.construit2();
    } else if (lvl == 2) {
      mur.construit3();
    } else if (lvl == 4) {
      mur.creerlvl();
      lvl = -1;
    } else {
      mur.construit();
    }

    // Mettre à jour le niveau affiché dans l'interface utilisateur
    cb.setLevel(lvl);

    // Initialiser la barre de jeu et réinitialiser la liste de boules et de bonus
    barre = new Barre();
    listeBoules = new ArrayList<Boule>();
    listeBoules.add(new Boule());
    listeBonus = new ArrayList<Bonus>();

    // Passer à la phase d'attente, réinitialiser le délai et la largeur de la barre
    phase = ATTEND;
    delai = DELAI;
    barre.setMiLargeur(barreOrigine);

    // Réinitialiser le compteur de bonus

    compteurBonus = 0;

    // Démarrer le thread d'exécution du jeu si nécessaire
    if (action == null) {
      action = new Thread(this);
      action.start();
    }
  }

  /**
   * Lance la boule dans le jeu avec l'angle spécifié.
   *
   * @param angle L'angle de départ de la boule en degrés.
   *              Cet angle détermine la direction dans laquelle la boule sera
   *              lancée.
   *              Il doit être compris entre 30 et 150 degrés pour un lancement
   *              efficace.
   */
  public void lanceBoule(int angle) {
    // Vérifie si le jeu est en phase d'attente
    if (phase == ATTEND) {
      // Passe à la phase de déplacement de la boule
      phase = ROULE;
      // Lance la boule avec l'angle spécifié
      listeBoules.get(0).angleDep(angle);
    }
  }

  /**
   * Génère un nombre aléatoire dans une plage spécifiée.
   *
   * @param min La valeur minimale de la plage (incluse).
   * @param max La valeur maximale de la plage (exclue).
   * @return Un entier aléatoire compris dans la plage [min, max).
   */
  public int nombreAleatoirePlage(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }

  /**
   * Redessine l'espace de jeu avec tous ses composants.
   *
   * @param comp Le contexte graphique dans lequel dessiner les composants de
   *             l'espace de jeu.
   *             Ce contexte fournit les fonctionnalités nécessaires pour dessiner
   *             des formes et des images.
   */
  public void paintComponent(Graphics comp) {
    Graphics2D comp2D = (Graphics2D) comp;
    // Effacement de l'espace de jeu
    comp2D.setColor(getBackground());
    comp2D.fillRect(0, 0, getSize().width, getSize().height);
    // Dessin de la barre
    barre.dessine(comp2D);
    // Dessin de la liste de boules
    for (Boule boule : listeBoules) {
      boule.dessine(comp2D);
    }
    // Dessin de la liste de bonus
    for (Bonus bonus : listeBonus) {
      bonus.dessine(comp2D);
    }

    // Dessin du mur de brique
    // Au tout départ le mur n'existe pas
    if (mur != null) {
      mur.dessine(comp2D);
    }

  }

  /**
   * Gère le mouvement de la souris.
   *
   * @param evt L'événement de souris associé au mouvement.
   *            Cet événement contient des informations sur la position de la
   *            souris.
   */
  public void mouseMoved(MouseEvent evt) {
    // Si le pointeur est trop à gauche ...
    if (evt.getX() < barre.getMiLargeur())
      // barre contre le bord gauche
      barre.setX(barre.getMiLargeur());
    else
    // Si de pointeur est trop à droite ...
    if (evt.getX() > getSize().width - barre.getMiLargeur())
      // barre contre le bord droit
      barre.setX(getSize().width - barre.getMiLargeur());
    else
      // barre centrée sur le pointeur
      barre.setX(evt.getX());
  }

  /**
   * Gère le glissement de la souris (méthode non utilisée).
   *
   * @param evt L'événement de souris associé au glissement.
   *            Cette méthode n'implémente pas la gestion du glissement de la
   *            souris.
   */
  public void mouseDragged(MouseEvent evt) {
  }

  /**
   * Gère le clic de la souris.
   *
   * @param evt L'événement de souris associé au clic.
   *            Cet événement contient des informations sur la position et le type
   *            de clic effectué.
   */
  public void mouseClicked(MouseEvent evt) {
    // Lance une nouvelle boule avec un angle aléatoire lorsque la souris est
    // cliquée
    lanceBoule((int) (Math.random() * 120) + 30);
    cb.setcreatelvl(false);
  }

  /**
   * Gère l'entrée de la souris dans la zone de l'espace de jeu (méthode non
   * utilisée).
   *
   * @param evt L'événement de souris associé à l'entrée dans la zone de l'espace
   *            de jeu.
   *            Cette méthode n'implémente pas de fonctionnalités pour l'entrée de
   *            la souris.
   */
  public void mouseEntered(MouseEvent evt) {
  }

  /**
   * Gère la sortie de la souris de la zone de l'espace de jeu (méthode non
   * utilisée).
   *
   * @param evt L'événement de souris associé à la sortie de la zone de l'espace
   *            de jeu.
   *            Cette méthode n'implémente pas de fonctionnalités pour la sortie
   *            de la souris.
   */
  public void mouseExited(MouseEvent evt) {
  }

  /**
   * Gère le clic maintenu de la souris (méthode non utilisée).
   *
   * @param evt L'événement de souris associé au clic maintenu.
   *            Cette méthode n'implémente pas de fonctionnalités pour le clic
   *            maintenu de la souris.
   */
  public void mousePressed(MouseEvent evt) {
  }

  /**
   * Gère le relâchement du clic de la souris (méthode non utilisée).
   *
   * @param evt L'événement de souris associé au relâchement du clic.
   *            Cette méthode n'implémente pas de fonctionnalités pour le
   *            relâchement du clic de la souris.
   */
  public void mouseReleased(MouseEvent evt) {
  }

}
