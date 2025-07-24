import java.util.Scanner;

public class GestionStock {
    // Tableaux parallèles pour stocker les informations des produits
    private static int[] codesProduits = new int[100];
    private static String[] nomsProduits = new String[100];
    private static int[] quantites = new int[100];
    private static double[] prix = new double[100];
    
    // Compteur pour le nombre de produits actuels
    private static int nombreProduits = 0;
    
    // Scanner pour les entrées utilisateur
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        int choix;
        
        System.out.println("=== Bienvenue dans l'Application de Gestion de Stock ===\n");
        
        do {
            printMenu();
            System.out.print("Votre choix : ");
            
            try {
                choix = scanner.nextInt();
                scanner.nextLine(); // Consommer la ligne restante
            } catch (Exception e) {
                System.out.println("Erreur : Veuillez entrer un nombre valide (0-6).");
                scanner.nextLine(); // Vider le buffer
                choix = -1; // Valeur invalide pour continuer la boucle
                continue;
            }
            
            switch (choix) {
                case 1:
                    ajouterProduitMenu();
                    break;
                case 2:
                    modifierProduitMenu();
                    break;
                case 3:
                    supprimerProduitMenu();
                    break;
                case 4:
                    afficherProduits();
                    break;
                case 5:
                    rechercherProduitMenu();
                    break;
                case 6:
                    calculerValeurStock();
                    break;
                case 0:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
            
            if (choix != 0) {
                System.out.println("\nAppuyez sur Entrée pour continuer...");
                scanner.nextLine();
            }
            
        } while (choix != 0);
        
        scanner.close();
    }
    
    /**
     * Affiche le menu principal
     */
    public static void printMenu() {
        System.out.println("\n=============== MENU PRINCIPAL ===============");
        System.out.println("1. Ajouter un produit");
        System.out.println("2. Modifier un produit");
        System.out.println("3. Supprimer un produit");
        System.out.println("4. Afficher tous les produits");
        System.out.println("5. Rechercher un produit");
        System.out.println("6. Calculer la valeur totale du stock");
        System.out.println("0. Quitter");
        System.out.println("==============================================");
    }
    
    /**
     * Menu pour ajouter un produit
     */
    private static void ajouterProduitMenu() {
        if (nombreProduits >= 100) {
            System.out.println("Erreur : Le stock est plein (maximum 100 produits).");
            return;
        }
        
        System.out.println("\n--- Ajouter un nouveau produit ---");
        
        try {
            System.out.print("Code du produit (nombre entier) : ");
            int code = scanner.nextInt();
            scanner.nextLine();
            
            // Vérifier si le code existe déjà
            if (trouverIndexProduit(code) != -1) {
                System.out.println("Erreur : Un produit avec ce code existe déjà.");
                return;
            }
            
            System.out.print("Nom du produit : ");
            String nom = scanner.nextLine();
            
            System.out.print("Quantité (nombre entier) : ");
            int quantite = scanner.nextInt();
            
            if (quantite < 0) {
                System.out.println("Erreur : La quantité ne peut pas être négative.");
                return;
            }
            
            System.out.print("Prix unitaire (nombre décimal) : ");
            double prixUnitaire = scanner.nextDouble();
            
            if (prixUnitaire < 0) {
                System.out.println("Erreur : Le prix ne peut pas être négatif.");
                return;
            }
            
            ajouterProduit(code, nom, quantite, prixUnitaire);
            
        } catch (Exception e) {
            System.out.println("Erreur de saisie : Veuillez entrer des valeurs valides.");
            System.out.println("- Code : nombre entier (ex: 123)");
            System.out.println("- Quantité : nombre entier (ex: 10)");
            System.out.println("- Prix : nombre décimal (ex: 15.99)");
            scanner.nextLine(); // Vider le buffer
        }
    }
    
    /**
     * Ajoute un produit dans les tableaux
     */
    public static void ajouterProduit(int code, String nom, int quantite, double prixUnitaire) {
        codesProduits[nombreProduits] = code;
        nomsProduits[nombreProduits] = nom;
        quantites[nombreProduits] = quantite;
        prix[nombreProduits] = prixUnitaire;
        nombreProduits++;
        
        System.out.println("Produit ajouté avec succès !");
    }
    
    /**
     * Menu pour modifier un produit
     */
    private static void modifierProduitMenu() {
        if (nombreProduits == 0) {
            System.out.println("Aucun produit en stock.");
            return;
        }
        
        System.out.println("\n--- Modifier un produit ---");
        
        try {
            System.out.print("Code du produit à modifier : ");
            int code = scanner.nextInt();
            scanner.nextLine();
            
            int index = trouverIndexProduit(code);
            if (index == -1) {
                System.out.println("Produit non trouvé.");
                return;
            }
            
            System.out.println("Produit actuel : " + nomsProduits[index] + 
                              " (Quantité: " + quantites[index] + 
                              ", Prix: " + prix[index] + "€)");
            
            System.out.print("Nouveau nom : ");
            String nouveauNom = scanner.nextLine();
            
            System.out.print("Nouvelle quantité : ");
            int nouvelleQuantite = scanner.nextInt();
            
            if (nouvelleQuantite < 0) {
                System.out.println("Erreur : La quantité ne peut pas être négative.");
                return;
            }
            
            System.out.print("Nouveau prix : ");
            double nouveauPrix = scanner.nextDouble();
            
            if (nouveauPrix < 0) {
                System.out.println("Erreur : Le prix ne peut pas être négatif.");
                return;
            }
            
            modifierProduit(code, nouveauNom, nouvelleQuantite, nouveauPrix);
            
        } catch (Exception e) {
            System.out.println("Erreur de saisie : Veuillez entrer des valeurs valides.");
            scanner.nextLine(); // Vider le buffer
        }
    }
    
    /**
     * Modifie les informations d'un produit existant
     */
    public static void modifierProduit(int code, String nouveauNom, int nouvelleQuantite, double nouveauPrix) {
        int index = trouverIndexProduit(code);
        if (index != -1) {
            nomsProduits[index] = nouveauNom;
            quantites[index] = nouvelleQuantite;
            prix[index] = nouveauPrix;
            System.out.println("Produit modifié avec succès !");
        } else {
            System.out.println("Erreur : Produit non trouvé.");
        }
    }
    
    /**
     * Menu pour supprimer un produit
     */
    private static void supprimerProduitMenu() {
        if (nombreProduits == 0) {
            System.out.println("Aucun produit en stock.");
            return;
        }
        
        System.out.println("\n--- Supprimer un produit ---");
        
        try {
            System.out.print("Code du produit à supprimer : ");
            int code = scanner.nextInt();
            
            supprimerProduit(code);
            
        } catch (Exception e) {
            System.out.println("Erreur de saisie : Veuillez entrer un code valide (nombre entier).");
            scanner.nextLine(); // Vider le buffer
        }
    }
    
    /**
     * Supprime un produit des tableaux
     */
    public static void supprimerProduit(int code) {
        int index = trouverIndexProduit(code);
        if (index == -1) {
            System.out.println("Erreur : Produit non trouvé.");
            return;
        }
        
        // Décaler tous les éléments après l'index vers la gauche
        for (int i = index; i < nombreProduits - 1; i++) {
            codesProduits[i] = codesProduits[i + 1];
            nomsProduits[i] = nomsProduits[i + 1];
            quantites[i] = quantites[i + 1];
            prix[i] = prix[i + 1];
        }
        
        nombreProduits--;
        System.out.println("Produit supprimé avec succès !");
    }
    
    /**
     * Affiche la liste complète des produits
     */
    public static void afficherProduits() {
        System.out.println("\n--- Liste des produits en stock ---");
        
        if (nombreProduits == 0) {
            System.out.println("Aucun produit en stock.");
            return;
        }
        
        System.out.println("+---------+---------------------+----------+----------+----------+");
        System.out.println("| Code    | Nom                 | Quantité | Prix     | Valeur   |");
        System.out.println("+---------+---------------------+----------+----------+----------+");
        
        for (int i = 0; i < nombreProduits; i++) {
            double valeurProduit = quantites[i] * prix[i];
            System.out.printf("| %-7d | %-19s | %-8d | %-8.2f | %-8.2f |\n",
                            codesProduits[i], nomsProduits[i], quantites[i], prix[i], valeurProduit);
        }
        
        System.out.println("+---------+---------------------+----------+----------+----------+");
        System.out.println("Nombre total de produits : " + nombreProduits);
    }
    
    /**
     * Menu pour rechercher un produit
     */
    private static void rechercherProduitMenu() {
        System.out.println("\n--- Rechercher un produit ---");
        System.out.print("Nom du produit à rechercher : ");
        String nom = scanner.nextLine();
        
        rechercherProduit(nom);
    }
    
    /**
     * Recherche un produit par son nom
     */
    public static void rechercherProduit(String nom) {
        boolean trouve = false;
        
        System.out.println("\n--- Résultats de la recherche ---");
        
        for (int i = 0; i < nombreProduits; i++) {
            if (nomsProduits[i].toLowerCase().contains(nom.toLowerCase())) {
                if (!trouve) {
                    System.out.println("+---------+---------------------+----------+----------+----------+");
                    System.out.println("| Code    | Nom                 | Quantité | Prix     | Valeur   |");
                    System.out.println("+---------+---------------------+----------+----------+----------+");
                    trouve = true;
                }
                
                double valeurProduit = quantites[i] * prix[i];
                System.out.printf("| %-7d | %-19s | %-8d | %-8.2f | %-8.2f |\n",
                                codesProduits[i], nomsProduits[i], quantites[i], prix[i], valeurProduit);
            }
        }
        
        if (trouve) {
            System.out.println("+---------+---------------------+----------+----------+----------+");
        } else {
            System.out.println("Aucun produit trouvé avec ce nom.");
        }
    }
    
    /**
     * Calcule et affiche la valeur totale du stock
     */
    public static void calculerValeurStock() {
        System.out.println("\n--- Calcul de la valeur totale du stock ---");
        
        if (nombreProduits == 0) {
            System.out.println("Aucun produit en stock. Valeur totale : 0.00€");
            return;
        }
        
        double valeurTotal = 0.0;
        
        for (int i = 0; i < nombreProduits; i++) {
            double valeurProduit = quantites[i] * prix[i];
            valeurTotal += valeurProduit;
        }
        
        System.out.println("Nombre de produits différents : " + nombreProduits);
        System.out.printf("Valeur totale du stock : %.2f€\n", valeurTotal);
    }
    
    /**
     * Trouve l'index d'un produit par son code
     * @param code Le code du produit à rechercher
     * @return L'index du produit ou -1 si non trouvé
     */
    private static int trouverIndexProduit(int code) {
        for (int i = 0; i < nombreProduits; i++) {
            if (codesProduits[i] == code) {
                return i;
            }
        }
        return -1;
    }
}