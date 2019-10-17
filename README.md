# TP1 IHM
## Ergi Sala, Tom Solvery
### INFO5 Polytech,

### Spécifications

 - Lorsqu'on clique sur le RangeSlider le curseur le plus près se positionne et est séléecionné.
 - Lorsqu'on clique sur le RangeSlider à équidistance de chaque curseur, le curseur le plus bas s'y positionne et est séléctionné.
 - On peut superposer les deux curseurs afin de choisir  une seule valeur.
 - Les curseurs ne peuvent pas échanger de place (curseur 1 >= curseur2)
 - Cliquer sur un curseur le place dans un état "sélectionné" qui permet aux touches directionnelles du clavier de le déplacer.
 - Lorsqu'on reste appuyé sur un curseur on peut le faire glisser sur le RangeSlider afin de lui donner la valeur que l'on souhaite
 - Lorsque les deux curseurs se trouvent au même endroit, il est possivle de faire glisser seulement le curseurs inférieurs, pour replacer le curseurs supérieur cliquer dans une zone supérieur du rangeslider afin de détacher les deux curseurs.
 
 Les libellés au dessus du rangeSlider indiquent la valeur des curseurs inférieur(A) et supérieur(B)
 
 ### Démo
 Cette démo simule une carte de logement. Chaque maison est représentée par un numéro qui désigne leur nombre de chambre.
 Le rangeSlider permet de choisir l'intervalle que l'on souhaite prendre afin de faire une sélection des maisons selon leur nombre de chambre
 Cliquer sur le boutton valider une première fois pour faire apparaître toutes les maisons.
 Vous pouvez ensuite utiliser le rangeSlider et appuyer sur valider pour faire apparaitre les maisons filtrées. 
 Si vous voulez utiliser les touches du clavier pensez à reappuyer dans la zone du rangeslider pour réactiver l'utilisation du rangeslider avec le clavier.
