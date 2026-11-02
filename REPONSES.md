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

## Question 1.3

### a.

Avec `new Rectangle()`, `tester` affiche `20` : la largeur vaut 5 et la hauteur 4. Avec `new Carre()`, il affiche `16` : après les deux appels, les deux dimensions valent 4. Le client attend `20`, puisque `tester` traite `r` comme un rectangle dont largeur et hauteur peuvent être définies indépendamment.

### b.

Cette conception viole le **principe de substitution de Liskov (LSP)** : `Carre` hérite de `Rectangle` mais ses setters ne respectent pas le comportement attendu d'un rectangle, dont les dimensions sont indépendantes.

Une conception corrigée expose un contrat commun `Forme` avec `aire()`, puis fait implémenter ce contrat par un `Rectangle` et un `Carre` indépendants. Leurs dimensions sont définies au constructeur et immuables, évitant ainsi qu'une sous-classe ne change le comportement des setters du rectangle.

## Question 2.1

1. Le tableau interne est exposé directement : un appelant peut remplacer ou supprimer des éléments, voire modifier des cases au-delà de `taille`, ce qui contourne `ajouter` et peut corrompre l'état du panier.
2. Les clients dépendent de la représentation interne (un tableau de taille fixe) et doivent gérer eux-mêmes les cases `null` après le dernier article ; remplacer le tableau par une autre structure imposerait de modifier ces clients.

## Question 2.2

- **Aggregate** : l'interface `Iterable<Produit>`, qui permet d'obtenir un itérateur.
- **ConcreteAggregate** : `Panier`, qui stocke les produits et fournit `iterator()`.
- **Iterator** : l'interface `Iterator<Produit>`, qui définit `hasNext()` et `next()`.
- **ConcreteIterator** : l'itérateur anonyme créé par `Panier.iterator()` ; `FiltreDisponibles` est également un itérateur concret qui enveloppe un autre `Iterator<Produit>`.

Le patron favorise le **SRP** en séparant le parcours du stockage du panier ; l'**ISP**, car les clients utilisent les petits contrats `Iterable` et `Iterator` plutôt qu'une API exposant le tableau ; et le **DIP**, car `FiltreDisponibles` dépend de l'abstraction `Iterator`, pas d'une classe de collection concrète. Il favorise aussi l'**OCP** : on peut ajouter d'autres itérateurs ou filtres sans changer le code client qui parcourt un `Iterable`. Les implémentations restent substituables si elles respectent le contrat de `Iterator`.

## Question 3.1

Le nombre d'instances distinctes observées n'est pas fixe : lors de dix exécutions du test, j'en ai observé de 16 à 20. Il varie selon l'ordonnancement des threads et peut être supérieur à 1 (au plus 20 threads peuvent chacune ajouter une référence distincte). Le délai de 50 ms rend plus probable que plusieurs threads passent le test `instance == null` avant qu'une instance soit publiée, sans garantir un résultat précis à chaque exécution.

Exemple d'entrelacement qui produit deux références distinctes :

| Instant | Thread T1 | Thread T2 | `instance` |
|---|---|---|---|
| t1 | Vérifie `instance == null` : vrai, commence la construction de A | — | `null` |
| t2 | Dort dans le constructeur de A | Vérifie `instance == null` : vrai, commence la construction de B | `null` |
| t3 | Termine et affecte A, puis lit A pour la retourner | Dort dans le constructeur de B | A |
| t4 | — | Termine, affecte B, puis lit B pour la retourner | B |

Le client reçoit A de T1 et B de T2 ; le `Set` contient alors deux objets. Le champ statique ne conserve finalement que B, mais cela ne change pas la référence A déjà retournée à T1.

## Question 3.2

`JournalApplication` implémente `Journal` et utilise l'idiome du holder : la classe interne `Holder` initialise l'instance unique à la première invocation de `getInstance()`, avec une initialisation sûre vis-à-vis des threads. Les messages sont conservés dans une `CopyOnWriteArrayList`; `getMessages()` en renvoie une copie non modifiable.

Les tests JUnit vérifient que deux appels à `getInstance()` renvoient le même objet (`assertSame`) et que 100 threads ajoutent chacun un message, faisant augmenter la taille de 100.

## Question 3.3

`JournalEnum` est l'implémentation enum du contrat `Journal`; son unique constante `INSTANCE` porte les messages.

| Critère | Holder (`JournalApplication`) | Enum (`JournalEnum`) |
|---|---|---|
| Création paresseuse | Oui : le holder n'est initialisé qu'au premier appel à `getInstance()`. | Pas via un holder distinct : `INSTANCE` est créée lors de l'initialisation de la classe enum, elle-même différée jusqu'à son premier usage. |
| Sérialisation | Pas automatiquement protégée : une implémentation sérialisable doit gérer la désérialisation pour préserver l'unicité. | Garantie par Java : la sérialisation des enums préserve l'instance unique. |
| Possibilité d'hériter d'une classe | Oui : une classe singleton peut étendre une classe de base si son constructeur est accessible. | Impossible : une enum étend déjà `java.lang.Enum` et ne peut pas étendre une autre classe. |

## Question 3.4

1. **Dépendance impossible à remplacer facilement** : le test ne peut pas injecter un faux journal pour vérifier les messages sans utiliser le singleton global.
2. **État partagé entre tests** : les messages persistent dans la même instance, ce qui peut rendre les tests dépendants de leur ordre et provoquer des résultats non isolés.
3. **Effets de bord difficiles à contrôler** : chaque test déclenche l'écriture réelle dans le journal ; il devient plus difficile de simuler une erreur, de vérifier précisément l'interaction ou d'exécuter le test indépendamment.

Injecter une dépendance de type `Journal` dans `CommandeService` permettrait de fournir un faux journal propre à chaque test.

## Partie 4

### 4.1

`CommandeService` reçoit `DepotCommandes`, `Notificateur` et `Journal` par constructeur. Il parcourt les produits de l'`Iterable` avec une boucle for-each, applique la remise, enregistre et notifie la commande, puis écrit le message demandé dans le journal. `Main` récupère une seule fois `JournalApplication.getInstance()` et passe cette instance au service ; avec `panier.disponibles()` et `RemisePremium`, le total est `112,5`.

### 4.2

`CommandeServiceTest` utilise `DepotFactice`, `NotificateurFactice` et `JournalFactice` pour vérifier le total `112,5`, l'appel unique au dépôt et au notificateur, ainsi que le message `Commande traitee : client@example.com= 112.5`.

### 4.3

L'ajout de `RemiseBlackFriday` (-30 %) ne nécessite de modifier aucune classe métier existante : il suffit d'ajouter cette nouvelle stratégie et son test. Cela illustre le **principe ouvert/fermé (OCP)** : le système est ouvert à l'ajout d'une règle de remise et fermé à la modification du service existant. Le total attendu est `87,5`.

## Partie 5 — Questions de synthèse

### 5.1

| Principe | Où dans le TP ? |
|---|---|
| **S — Responsabilité unique** | `CommandeService` orchestre la commande ; les remises, le dépôt (`DepotFichier`) et la notification (`NotificateurConsole`) ont des classes séparées. |
| **O — Ouvert/fermé** | `RegleRemise` permet d'ajouter `RemiseBlackFriday` sans modifier `CommandeService` ni les autres remises. |
| **L — Substitution de Liskov** | `Carre` et `Rectangle` implémentent tous deux `Forme` sans relation d'héritage qui violerait le contrat ; les stratégies de remise sont aussi interchangeables via `RegleRemise`. |
| **I — Ségrégation des interfaces** | `Imprimante`, `Numeriseur` et `Telecopieur` séparent les capacités ; une imprimante basique n'implémente que celle qu'elle fournit. |
| **D — Inversion des dépendances** | `CommandeService` dépend des abstractions `DepotCommandes`, `Notificateur` et `Journal`, injectées par son constructeur ; les tests fournissent des doublures. |

### 5.2

`Main` est le point de composition de l'application : il choisit les implémentations concrètes et récupère le singleton une fois pour l'injecter. `CommandeService`, qui porte la logique métier, dépend uniquement de l'interface `Journal`; il peut ainsi recevoir un `JournalFactice` dans les tests sans accéder à l'état global du singleton.

### 5.3

`Panier.iterateurInverse()` retourne un itérateur concret qui parcourt les indices de `taille - 1` à `0`, sans exposer le tableau. Ce nouveau mode de parcours respecte l'**OCP** en ajoutant une variante sans modifier l'itérateur principal ni les clients qui choisissent de l'utiliser ; il sépare aussi le parcours de la représentation interne.

### 5.4

Non : le singleton garantit l'unicité de l'instance, pas à lui seul la sûreté de son état mutable. `JournalApplication` utilise une `CopyOnWriteArrayList`, qui rend sûres les écritures concurrentes simples et les lectures via `getMessages()` ; c'est pourquoi les 100 threads du test peuvent ajouter chacun un message sans perte. Avec une liste non synchronisée, ces accès concurrents pourraient perdre des mises à jour ou provoquer des erreurs. Pour des opérations composées (par exemple « vérifier puis ajouter »), une synchronisation supplémentaire pourrait rester nécessaire.
