/**
 * Racine de la hierarchie d'exceptions metier de la mediatheque.
 *
 * CHOIX : checked (extends Exception).
 * Justification : l'indisponibilite d'un document, un titre introuvable ou un catalogue
 * illisible sont des situations METIER attendues et recuperables (proposer une reservation,
 * afficher un message a l'usager, signaler le fichier fautif). L'appelant peut reagir
 * utilement : le compilateur doit donc l'obliger a traiter le cas.
 * Le motif "je verifie estDisponible() avant" est fragile : entre la verification et
 * l'emprunt, un autre usager peut avoir emprunte le document (acces concurrents).
 * Regle appliquee uniformement a toute la hierarchie MediathequeException.
 *
 * Abstraite : on ne leve jamais une "exception mediatheque" generique, toujours un cas precis.
 * Elle sert de racine pour un seul catch (MediathequeException e) quand le detail n'importe pas.
 */
public abstract class MediathequeException extends Exception {
    protected MediathequeException(String message) { super(message); }
    protected MediathequeException(String message, Throwable cause) { super(message, cause); }
}
