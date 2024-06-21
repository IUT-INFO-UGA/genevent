package fr.uga.iut2.genevent.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModeleUtilitaire {

    // Constantes

    public static final Pattern PATERNE_NOM = Pattern.compile("^[a-z-A-Z]+(-[a-z-A-Z]+)*( [a-z-A-Z]+(-[a-z-A-Z]+)*)+$");
    public static final Pattern PATERNE_TELEPHONE = Pattern.compile("^[0-9]{2}(([0-9]{2}){4})|((\\.[0-9]{2}){4})|(( [0-9]{2}){4})$");

    // Méthodes

    /**
     * Vérifie si la chaîne peut être formatée, et si c'est le cas,
     * formate le numéro de téléphone pour séparer chaque groupe de deux chiffres par un espace.
     * @param telephone Le numéro de téléphone à formater.
     * @return Le nouveau numéro de téléphone formaté si c'est possible, <code>null</code> sinon.
     */
    public static String formaterTelephone(String telephone) {
        Matcher matcher = PATERNE_TELEPHONE.matcher(telephone);
        if (!matcher.find()) {
            return null;
        }

        if (telephone.length() == 10) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 5; i++) {
                sb.append(telephone, i * 2, (i * 2) + 2);
                if (i + 1 < 5) {
                    sb.append(' ');
                }
            }
            return sb.toString();
        } else {
            return telephone.replaceAll("\\.", " ");
        }
    }
}
