
import javax.swing.*;
import java.awt.event.*;

/**
 * La classe CB représente le cadre principal du jeu de casse-briques.
 * Elle gère l'affichage de la fenêtre, la barre de menu et la logique du jeu.
 */
public class CB extends JFrame implements ActionListener {
    private EspaceJeu espace; // Espace de jeu
    private JMenuBar barreDeMenus; // Barre de menu
    private JMenu menuJeu; // Menu Jeu
    private JMenuItem jeuNouveau; // Option Nouveau dans le menu Jeu
    private JMenuItem jeuQuitter; // Option Quitter dans le menu Jeu
    private JMenuItem traitSeparation; // Trait de séparation dans le menu Jeu
    private JMenu nbvie; // Menu pour afficher le nombre de vies
    private JMenuItem creerlvl; // Option Créer niveau dans le menu
    private JLabel timerVue; // Affichage du timer
    private JLabel timerText; // Texte "Cooldown :"
    private JLabel levelText; // Texte "Niveau :"

    /**
     * Constructeur par défaut de la classe CB.
     * Initialise le cadre principal du jeu, l'espace de jeu et la barre de menus.
     */
    public CB() {
        super("Casse briques");
        setSize(370, 400);

        ExitWindow exit = new ExitWindow(); // Gestionnaire de fermeture du cadre
        addWindowListener(exit);

        espace = new EspaceJeu(this); // Création de l'espace de jeu
        getContentPane().add(espace);

        barreDeMenus = new JMenuBar(); // Création de la barre de menus
        menuJeu = new JMenu(); // Menu Jeu
        menuJeu.setText("Jeu");
        jeuNouveau = new JMenuItem(); // Option Nouveau dans le menu Jeu
        jeuNouveau.setText("Nouveau");
        jeuNouveau.addActionListener(this);

        nbvie = new JMenu(); // Menu pour afficher le nombre de vies

        creerlvl = new JMenuItem(); // Option Créer niveau dans le menu
        creerlvl.setText("Creer lvl");
        creerlvl.setEnabled(false);
        levelText = new JLabel("Niveau :");
        levelText.setVisible(false);

        timerVue = new JLabel(" 0"); // Affichage du timer
        timerText = new JLabel("Cooldown :"); // Texte "Cooldown :"

        jeuQuitter = new JMenuItem(); // Option Quitter dans le menu Jeu
        jeuQuitter.setText("Quitter");
        jeuQuitter.addActionListener(this);
        traitSeparation = new JMenuItem(); // Trait de séparation dans le menu Jeu
        traitSeparation.setText("--------------");

        menuJeu.add(jeuNouveau);
        menuJeu.add(traitSeparation);
        menuJeu.add(jeuQuitter);
        barreDeMenus.add(menuJeu);
        barreDeMenus.add(creerlvl);
        barreDeMenus.add(levelText);
        barreDeMenus.add(timerText);
        barreDeMenus.add(timerVue);
        barreDeMenus.add(nbvie);
        this.setJMenuBar(barreDeMenus);
    }

    /**
     * Point d'entrée de l'application. Crée une instance de CB et l'affiche.
     * 
     * @param args les arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        CB frame = new CB();
        frame.setVisible(true);
    }

    /**
     * Définit le niveau actuel du jeu.
     * 
     * @param lvl le niveau du jeu à définir
     */
    public void setLevel(int lvl) {
        levelText.setVisible(true);
        levelText.setText("Niveau : " + Integer.toString(lvl + 1) + " ");
    }

    /**
     * Met à jour l'affichage du nombre de vies.
     */
    public void affichagevie() {
        nbvie.setText("Vie: " + espace.getvie());
    }

    /**
     * Active ou désactive l'option "Créer niveau" dans le menu.
     * 
     * @param enabled true pour activer l'option, false pour la désactiver
     */
    public void setcreatelvl(boolean enabled) {
        creerlvl.setEnabled(enabled);
    }

    /**
     * Définit le temps affiché dans l'interface utilisateur.
     * 
     * @param time le temps à afficher
     */
    public void setTimer(String time) {
        timerVue.setText(" " + time);
    }

    /**
     * Gère les actions déclenchées par les événements.
     * 
     * @param e l'événement déclenché
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jeuNouveau) {
            espace.setlvl(0);
            creerlvl.setEnabled(true);
            creerlvl.addActionListener(this);
            espace.initialiseNiveau();
        }
        if (e.getSource() == jeuQuitter) {
            System.exit(0);
        }
        if (e.getSource() == creerlvl) {
            espace.setlvl(4);
            espace.initialiseNiveau();
        }
    }
}
