# Réponses

## Question 1.1

### a.

La méthode `traiterCommande` assume plusieurs responsabilités distinctes :

1. Calculer le montant total des produits de la commande.
2. Appliquer une remise en fonction du type de client.
3. Enregistrer la commande dans le fichier `commandes.txt`.
4. Gérer les erreurs d'écriture du fichier.
5. Afficher un message de confirmation destiné au client.

### b.

- **SRP (responsabilité unique)** : `CommandeService` cumule calcul, remise, persistance et confirmation, donc l'ajout d'un envoi de courriel réel le ferait changer pour une nouvelle responsabilité.
- **OCP (ouverture/fermeture)** : les règles de remise sont codées dans une chaîne de conditions, donc l'ajout d'un client `VIP` obligerait à modifier `traiterCommande`.
- **DIP (inversion des dépendances)** : le service dépend directement de `FileWriter` et de `System.out`, donc remplacer le fichier par une base de données ou la sortie console par un service de notification obligerait à modifier son code métier.

Les violations de **LSP** et d'**ISP** ne sont pas établies par cet exemple, qui ne présente ni hiérarchie de types ni interfaces à découper.

## Question 1.2

L'interface `Appareil` viole le **principe de ségrégation des interfaces (ISP)**, car elle impose à une imprimante basique des opérations de numérisation et de fax qu'elle ne sait pas effectuer ; un client qui appelle `numeriser` sur cette imprimante reçoit une `UnsupportedOperationException`. Cela enfreint aussi le **principe de substitution de Liskov (LSP)** : `ImprimanteBasique` ne peut pas remplacer correctement un `Appareil` pour un client qui utilise toutes les opérations promises par l'interface.

La conception corrigée sépare ces capacités dans les interfaces `Imprimante`, `Numeriseur` et `Telecopieur`. `ImprimanteBasique` implémente uniquement `Imprimante`, tandis que `MultifonctionBureau` implémente les trois interfaces et fournit les trois opérations.
