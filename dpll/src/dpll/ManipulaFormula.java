/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dpll;

import java.util.ArrayList;

/**
 *
 * @author Rodrigo
 */
public class ManipulaFormula {

    final static char E = 'Λ', OU = '+';

    public static void main(String args[]) {
        System.out.println(separaFormula("(B↔D)"));
    }

    public static ArrayList<String> separaFormula(String formula) {
        ArrayList<String> partes = new ArrayList<String>();
        int parentese = 0;
        String parte = "", parteAux = "";
        boolean modificou = false;
        char[] aux = formula.toCharArray();
        for (char c : aux) {
            if (c == '(') {
                parentese++;
            }
            if (c == ')') {
                parentese--;
            }
            if ((c == E || c == OU) && parentese == 0) {
                parteAux = removeImplicacao(parte);
                if (!parte.equals(parteAux)) {
                    modificou = true;
                }
                partes.add(parteAux);
                partes.add(c + "");
                parte = "";
            } else {
                parte += c;
            }
        }
        parteAux = removeImplicacao(parte);
        if (!parte.equals(parteAux)) {
            modificou = true;
        }
        partes.add(parteAux);
        if (modificou) {
            return separaFormula(juntarFormula(partes));
        }

        return partes;
    }

    public static String removeImplicacao(String formula) {
        String aux[], aux2 = "";
        int indice, inicio = 0;

        if (formula.contains("→")) {
            formula = formula.replace("(", "");
            formula = formula.replace(")", "");
            aux = formula.split("→");
            if (aux[0].charAt(0) == '(' && aux[0].charAt(aux[0].length() - 1) == ')') {
                aux2 = "¬" + aux[0];
            } else if (aux[0].length() == 2 && aux[0].charAt(0) == '(') {
                aux2 = "(¬" + aux[0].charAt(1);
            } else if (aux[0].length() > 1) {
                aux2 = "¬(" + aux[0] + ")";
            } else if (aux[0].length() == 1) {
                aux2 = "¬" + aux[0];
            }
            aux2 += OU + aux[1];
            return "(" + aux2 + ")";

        } else if (formula.contains("↔")) {
            formula = formula.replace("(", "");
            formula = formula.replace(")", "");
            aux = formula.split("↔");
            aux2 = removeImplicacao(aux[0] + "→" + aux[1]);
            aux2 += E;
            aux2 += removeImplicacao(aux[1] + "→" + aux[0]);
            return aux2;
        }
        return formula;

    }

    public static String corrigeParenteses(String formula) {
        char aux, aux2, aux3;
        int nivelParenteseAberto = 0, nivelParenteseFechado = 0;
        aux = formula.charAt(0);
        if (aux == '(') {
            for (int i = 0; i < formula.length(); i++) {
                aux = formula.charAt(i);
                if (aux == '(') {
                    nivelParenteseAberto++;
                    nivelParenteseFechado = nivelParenteseAberto;
                } else if (aux == ')') {
                    nivelParenteseFechado--;
                    nivelParenteseAberto--;
                    if (nivelParenteseAberto == 0 && i == formula.length() - 1) {
                        formula = formula.substring(1, i);
                        break;
                    } else if (nivelParenteseAberto == 0) {
                        break;
                    }
                }
            }
        }
        return formula;
    }

    public static String juntarFormula(ArrayList<String> formula) {
        String formula2 = "";
        for (String f : formula) {
            if(!Dpll.operacoes.contains(f))
            {
                formula2 += "("+f+")";
            }else{
                formula2 += f;
            }
        }
        return formula2;
    }
}