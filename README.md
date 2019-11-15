# IHM TP2
## Ergi SALA, Tom SOLVERY
### INFO5 Polytech

### Marking Menu

### Fonctionnement 

Notre marking menu permet à l'utilisateur de séléctionner des éléments du menu circulaire en mode normal ou expert.
L'utilisateur peut donc :
 
 - Ouvrir le menu en appuyant sur le clic droit, le maintien du clic droit enfoncé permet de laisser le menu actif et visible(mode normal)
 - Naviguer dans les menus et sous menu (Entrer dans un menu en sortant la souris du cercle au niveau du menu, retourner dans le menu principal en sortant la souris du cercle au niveau d'un menu "Retour")
 - Séléctionner une feuille du menu en sortant la souris au niveau de la feuille.
 - Fermer le menu en relachant le clic droit.
 
 ### Initialiser un nouveau menu
 
 Pour créer notre marking menu, il faut premièrement instancier un MenuWidget(JPanel).
 Afin d'afficher le menu on appelle drawMenu(Graphics).
 Afin d'ouvrir le menu on appelle OpenMenu(MouseEvent), conseil : appeler cette fonction dans un MousePressedEvent
 Afin de fermer le menu on appelle CloseMenu(MouseEvent)n conseil : appeler cette fonction dans un MouseReleasedEvent
 Afin de faire réagir le menu il faut appeler draggedMenu(MouseEvent) dans un MouseDraggedEvent
 
 On doit ensuite créer des feuilles et des noeuds pour notre menu :
 une feuille est représenté par la classe Leaf(String name) et un noeud par la classe Stage(String name).
 Lorsque l'on crée une feuille on doit implémenter la fonction 
 public void actionned() qui est appelé lorsqu'on sélectionne la feuille dans le menu.
 
 Pour ajouter des noeuds et des feuilles dans ton menu, on dispose des fonctions suivantes : 
 
 addLeaf(Leaf l) qui ajoute une feuille au menu actuel
 addStage(Stage s) qui ajoute un noeud au menu actuel
 addLeafToStage(int parentStageId,Leaf l) qui ajoute une feuille au menu indiqué par parentStageId
 addStageToStage(int parentStageId, Stage s) qui ajoute un noeud au menu indiqué par parentStageId
 
 Si on ajoute un noeud nommé "Retour", ce noeud permettra de rediriger automatiquement vers le menu principal, j'ai fait ce choix pour
permettre à l'utilisateur d'utiliser plus facilement le mode expert en revenant à chaque fois au menu principal pour sélectionner un autre élément du menu. Exemple Forme + Couleur

L'ajout de feuille et de noeud dans un même menu est limité à 8, l'utilisateur doit donc faire en sorte d'organiser son menu pour ne pas avoir plus de 8 items dans un noeud

### Exemple Forme Couleur Effacer

Dans cette exemple on retrouve un menu cqui nous permet de choisir une couleur une forme ou de choisir d'éffacer les dessins.
On a donc dans le menu principal deux noeuds (Forme et Couleur) ainsi qu'une feuille (Effacer).
Dans le sous-menu Forme on a le choix entre différentes formes ainsi que la possibilité de faire un retour au menu principal.
Dans le sous-menu Couleur, on a le choix entre différentes couleurs ainsi que la possibilité de faire un retour au menu principal.
 

  
