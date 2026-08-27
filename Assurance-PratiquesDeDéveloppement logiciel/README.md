
# README

## Technologies utilisées

Voici les technologies utilisées pour notre projet :

1. **Langage de programmation** : JAVA
2. **IDE de développement** : IntelliJ
3. **Format des fichiers** : JSON pour les dossiers d'entrée et de sortie

## Compilation

La compilation est réalisée avec la commande suivante :

\`\`\`
mvn compile
\`\`\`

## Exécution

### Depuis un invite de commande

Pour exécuter le projet à la racine du projet depuis un invite de commande, utilisez la commande :

\`\`\`
java -jar Refunds-3.0.jar Assurance.json Output.json
\`\`\`

Pour afficher le fichier \`Statistique.json\` :

\`\`\`
java -jar Refunds-3.0.jar -S
\`\`\`

Pour réinitialiser les statistiques :

\`\`\`
java -jar Refunds-3.0.jar -SR
\`\`\`

### Depuis IntelliJ

Pour exécuter le projet depuis IntelliJ, ajoutez \`Assurance.json Output.json\` en arguments dans la configuration Main.

Pour afficher le fichier \`Statistique.json\`, mettez en argument \`-S\` dans la configuration Main.

Pour réinitialiser les statistiques, ajoutez \`-SR\` en argument dans la configuration Main.
