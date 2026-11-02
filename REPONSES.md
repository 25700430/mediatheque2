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
